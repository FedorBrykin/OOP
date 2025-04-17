package ru.nsu.brykin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * master.
 */
public class PrimeCheckerMaster {
    private static final int MAX_RETRIES = 10;
    private final AtomicBoolean hasComposite = new AtomicBoolean(false);
    private static final int MULTICAST_PORT = 8888;
    private static final String MULTICAST_GROUP = "230.0.0.0";
    private static final int WORKER_TIMEOUT = 5000;
    private static final int BATCH_SIZE = 100;
    private static final int DISCOVERY_INTERVAL_MS = 1000;

    private final ConcurrentLinkedQueue<InetSocketAddress> workers = new ConcurrentLinkedQueue<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Map<List<Integer>, Boolean> results = new ConcurrentHashMap<>();
    private final AtomicInteger activeTasks = new AtomicInteger(0);

    /**
     * Точка входа мастера.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java PrimeCheckerMaster <numbers_file>");
            return;
        }

        PrimeCheckerMaster master = new PrimeCheckerMaster();
        master.startDiscoveryListener();
        master.processNumbers(args[0]);
        master.awaitCompletion();

        System.out.println("Final result: " +
                (master.hasCompositeNumbers() ? "COMPOSITE NUMBERS FOUND"
                        : "ALL NUMBERS ARE PRIME"));
    }

    public boolean hasCompositeNumbers() {
        return hasComposite.get();
    }

    public void awaitCompletion() {
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Запускает поток для прослушивания сообщений воркеров.
     */
    void startDiscoveryListener() {
        new Thread(() -> {
            try (MulticastSocket socket = new MulticastSocket(MULTICAST_PORT)) {
                InetAddress group = InetAddress.getByName(MULTICAST_GROUP);
                socket.joinGroup(group);

                byte[] buf = new byte[256];
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    String msg = new String(packet.getData(), 0, packet.getLength());
                    if (msg.startsWith("PRIME_WORKER:")) {
                        registerWorker(packet, msg);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error in discovery listener: " + e.getMessage());
            }
        }).start();
    }

    /**
     * Регистрирует нового воркера.
     */
    private void registerWorker(DatagramPacket packet, String msg) {
        InetSocketAddress workerAddr = new InetSocketAddress(
                packet.getAddress(),
                Integer.parseInt(msg.split(":")[1])
        );
        if (!workers.contains(workerAddr)) {
            workers.add(workerAddr);
            System.out.println("Registered worker: " + workerAddr);
        }
    }

    /**
     * Обрабатывает файл с числами.
     */
    void processNumbers(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            List<Integer> batch = new ArrayList<>(BATCH_SIZE);
            while (scanner.hasNextInt()) {
                batch.add(scanner.nextInt());
                if (batch.size() >= BATCH_SIZE) {
                    dispatchBatch(new ArrayList<>(batch));
                    batch.clear();
                }
            }
            if (!batch.isEmpty()) {
                dispatchBatch(batch);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        }
    }

    /**
     * Отправляет пачку чисел доступному воркеру.
     */
    private void dispatchBatch(List<Integer> batch) {
        executor.submit(() -> {
            int retries = 0;
            while (retries < MAX_RETRIES && !hasComposite.get()) {
                InetSocketAddress worker = null;
                try {
                    worker = workers.poll();
                    if (worker == null) {
                        waitForWorkers();
                        continue;
                    }

                    try (Socket socket = new Socket();
                         ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                         ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                        socket.connect(worker, WORKER_TIMEOUT);
                        out.writeObject(batch);
                        out.flush();

                        Boolean batchResult = (Boolean) in.readObject();
                        if (batchResult) {
                            hasComposite.set(true);
                        }
                        workers.add(worker);
                        break;
                    }
                } catch (Exception e) {
                    System.err.println("Error processing batch with " + worker + ": "
                            + e.getMessage());
                    if (++retries == MAX_RETRIES) {
                        hasComposite.set(true);
                    }
                }
            }
        });
    }

    /**
     * Ожидает появления воркеров.
     */
    private void waitForWorkers() {
        try {
            System.err.println("No workers available, waiting...");
            Thread.sleep(DISCOVERY_INTERVAL_MS);
            sendWorkerDiscoveryRequest();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Обрабатывает пачку чисел.
     */
    private void processBatchWithWorker(List<Integer> batch) {
        InetSocketAddress worker = workers.poll();
        activeTasks.incrementAndGet();

        try (Socket socket = new Socket();
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            socket.connect(worker, WORKER_TIMEOUT);
            out.writeObject(batch);
            out.flush();

            Boolean hasComposite = (Boolean) in.readObject();
            results.put(batch, hasComposite);
            logResult(batch, worker, hasComposite);

        } catch (Exception e) {
            System.err.println("Error processing batch with " + worker + ": " + e.getMessage());
            results.put(batch, true);
        } finally {
            workers.add(worker);
            activeTasks.decrementAndGet();
        }
    }

    /**
     * Логирует результат обработки.
     */
    private void logResult(List<Integer> batch, InetSocketAddress worker, Boolean hasComposite) {
        System.out.printf("Batch %s processed by %s: %s (Active tasks: %d)%n",
                batch.subList(0, Math.min(5, batch.size())) + (batch.size() > 5 ? "..." : ""),
                worker,
                hasComposite ? "HAS COMPOSITE" : "ALL PRIME",
                activeTasks.get());
    }

    /**
     * Отправляет запрос на обнаружение воркеров.
     */
    private void sendWorkerDiscoveryRequest() {
        try (DatagramSocket socket = new DatagramSocket()) {
            String msg = "PRIME_MASTER_DISCOVERY";
            byte[] buf = msg.getBytes();
            InetAddress group = InetAddress.getByName(MULTICAST_GROUP);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, MULTICAST_PORT);
            socket.send(packet);
        } catch (IOException e) {
            System.err.println("Error sending discovery request: " + e.getMessage());
        }
    }

    /**
     * Возвращает результаты обработки.
     */
    public Map<List<Integer>, Boolean> getResults() {
        return Collections.unmodifiableMap(results);
    }
}