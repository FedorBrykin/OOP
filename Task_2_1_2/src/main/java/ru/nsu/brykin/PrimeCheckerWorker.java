package ru.nsu.brykin;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * worker.
 */
public class PrimeCheckerWorker {
    private static final int MULTICAST_PORT = 8888;
    private static final String MULTICAST_GROUP = "230.0.0.0";
    private static final int DEFAULT_PORT = 9999;
    private static final int MAX_PARALLEL_TASKS = 10;
    private static final int ANNOUNCEMENT_INTERVAL_MS = 3000;

    private volatile boolean running = true;
    private ServerSocket serverSocket;
    private Timer announcementTimer;
    private final ExecutorService taskExecutor =
            Executors.newFixedThreadPool(MAX_PARALLEL_TASKS);

    /**
     * Запускает воркер на стандартном порту.
     */
    public void start() throws IOException {
        startServer(DEFAULT_PORT);
        startAnnouncement();
    }

    /**
     * Запускает сервер для обработки соединений.
     */
    public void startServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Worker started on " + getLocalAddress() + ":" + port);

        while (running) {
            try {
                Socket client = serverSocket.accept();
                taskExecutor.submit(() -> handleClient(client));
            } catch (SocketException e) {
                if (running) {
                    System.err.println("Socket error: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Обрабатывает подключение.
     */
    private void handleClient(Socket client) {
        try (ObjectInputStream in = new ObjectInputStream(client.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())) {

            List<Integer> numbers = (List<Integer>) in.readObject();
            boolean hasComposite = checkBatch(numbers);
            out.writeObject(hasComposite);
            out.flush();

        } catch (Exception e) {
            System.err.println("Error processing request: " + e.getMessage());
        } finally {
            try { client.close(); } catch (IOException ignored) {}
        }
    }

    /**
     * Проверяет пачку чисел на наличие составных.
     */
    private boolean checkBatch(List<Integer> numbers) {
        return numbers.stream().anyMatch(n -> !PrimeMathUtils.isPrime(n));
    }

    /**
     * Запускает периодическое анонсирование воркера.
     */
    private void startAnnouncement() {
        announcementTimer = new Timer(true);
        announcementTimer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() { announcePresence(); }
                },
                0,
                ANNOUNCEMENT_INTERVAL_MS
        );
    }

    /**
     * Отправляет сообщение о присутствии.
     */
    private void announcePresence() {
        try (DatagramSocket socket = new DatagramSocket()) {
            String msg = "PRIME_WORKER:" +
                    (serverSocket != null ? serverSocket.getLocalPort() : DEFAULT_PORT);
            byte[] buf = msg.getBytes();
            InetAddress group = InetAddress.getByName(MULTICAST_GROUP);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, MULTICAST_PORT);
            socket.send(packet);
        } catch (IOException e) {
            System.err.println("Error announcing presence: " + e.getMessage());
        }
    }

    /**
     * Останавливает воркера.
     */
    public void stopServer() {
        running = false;
        if (announcementTimer != null) announcementTimer.cancel();
        taskExecutor.shutdownNow();
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing server: " + e.getMessage());
        }
    }

    /**
     * Возвращает локальный IP.
     */
    private static String getLocalAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    /**
     * Точка входа для запуска воркера.
     */
    public static void main(String[] args) {
        PrimeCheckerWorker worker = new PrimeCheckerWorker();
        try {
            worker.start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Shutting down worker...");
                worker.stopServer();
            }));
        } catch (IOException e) {
            System.err.println("Failed to start worker: " + e.getMessage());
            System.exit(1);
        }
    }
}