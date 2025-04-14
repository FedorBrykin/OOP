package ru.nsu.brykin;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrimeCheckerMaster {
    private static final int MULTICAST_PORT = 8888;
    private static final String MULTICAST_GROUP = "230.0.0.0";
    private static final int WORKER_TIMEOUT = 5000;
    private static final int BATCH_SIZE = 100;

    private final ConcurrentLinkedQueue<InetSocketAddress> workers = new ConcurrentLinkedQueue<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java PrimeCheckerMaster <numbers_file>");
            return;
        }

        PrimeCheckerMaster master = new PrimeCheckerMaster();
        master.startDiscoveryListener();
        master.processNumbers(args[0]);
    }

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
                        InetSocketAddress workerAddr = new InetSocketAddress(
                                packet.getAddress(),
                                Integer.parseInt(msg.split(":")[1])
                        );
                        workers.add(workerAddr);
                        System.out.println("Registered worker: " + workerAddr);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error in discovery listener: " + e.getMessage());
            }
        }).start();
    }

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

    private void dispatchBatch(List<Integer> batch) {
        while (workers.isEmpty()) {
            System.err.println("No workers available, waiting...");
            try {
                Thread.sleep(1000);
                sendWorkerDiscoveryRequest();
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        InetSocketAddress worker = workers.poll();
        executor.submit(() -> {
            try (Socket socket = new Socket();
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                socket.connect(worker, WORKER_TIMEOUT);
                out.writeObject(batch);
                out.flush();

                Boolean hasComposite = (Boolean) in.readObject();
                System.out.printf("Batch %s processed by %s: %s%n",
                        batch, worker, hasComposite ? "HAS COMPOSITE" : "ALL PRIME");

                workers.add(worker);
            } catch (Exception e) {
                System.err.println("Error processing batch with " + worker + ": " + e.getMessage());
                dispatchBatch(batch);
            }
        });
    }

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
}