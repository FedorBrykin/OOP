package ru.nsu.brykin;

import java.io.*;
import java.net.*;
import java.util.*;

public class PrimeCheckerWorker {
    private static final int MULTICAST_PORT = 8888;
    private static final String MULTICAST_GROUP = "230.0.0.0";
    private static final int DEFAULT_PORT = 9999;

    private volatile boolean running = true;
    private ServerSocket serverSocket;
    private Timer announcementTimer;

    public void start() throws IOException {
        startServer(DEFAULT_PORT);
        startAnnouncement();
    }

    public void startServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Worker started on " + getLocalAddress() + ":" + port);

        while (running) {
            try (Socket client = serverSocket.accept();
                 ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())) {

                List<Integer> numbers = (List<Integer>) in.readObject();
                boolean hasComposite = checkBatch(numbers);
                out.writeObject(hasComposite);
                out.flush();

            } catch (SocketException e) {
                if (running) {
                    System.err.println("Socket error: " + e.getMessage());
                }
            } catch (Exception e) {
                System.err.println("Error processing request: " + e.getMessage());
            }
        }
    }

    private void startAnnouncement() {
        announcementTimer = new Timer(true);
        announcementTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                announcePresence();
            }
        }, 0, 3000);
    }

    private void announcePresence() {
        try (DatagramSocket socket = new DatagramSocket()) {
            String msg = "PRIME_WORKER:" + (serverSocket != null ? serverSocket.getLocalPort() : DEFAULT_PORT);
            byte[] buf = msg.getBytes();
            InetAddress group = InetAddress.getByName(MULTICAST_GROUP);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, MULTICAST_PORT);
            socket.send(packet);
        } catch (IOException e) {
            System.err.println("Error announcing presence: " + e.getMessage());
        }
    }

    public void stopServer() {
        running = false;
        if (announcementTimer != null) {
            announcementTimer.cancel();
        }
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing server: " + e.getMessage());
        }
    }

    private boolean checkBatch(List<Integer> numbers) {
        return numbers.stream().anyMatch(n -> !isPrime(n));
    }

    private boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;

        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private static String getLocalAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

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