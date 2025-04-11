package ru.nsu.brykin;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PrimeCheckerWorker {
    private static final int MULTICAST_PORT = 8888;
    private static final String MULTICAST_GROUP = "230.0.0.0";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) throws IOException {
        announcePresence();

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Worker started on " + getLocalAddress() + ":" + SERVER_PORT);
            while (true) {
                try (Socket client = serverSocket.accept();
                     ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                     ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())) {

                    List<Integer> numbers = (List<Integer>) in.readObject();
                    boolean hasComposite = checkBatch(numbers);
                    out.writeObject(hasComposite);
                    out.flush();

                } catch (Exception e) {
                    System.err.println("Error processing request: " + e.getMessage());
                }
            }
        }
    }

    private static void announcePresence() throws IOException {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            public void run() {
                try (DatagramSocket socket = new DatagramSocket()) {
                    String msg = "PRIME_WORKER:" + SERVER_PORT;
                    byte[] buf = msg.getBytes();
                    InetAddress group = InetAddress.getByName(MULTICAST_GROUP);
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, group, MULTICAST_PORT);
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 3000); // Отправляем каждые 3 секунды
    }

    static boolean checkBatch(List<Integer> numbers) {
        return numbers.stream().anyMatch(n -> !isPrime(n));
    }

    static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i * i <= n; i++) {
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

    private ServerSocket serverSocket;
    private volatile boolean running = true;

    // Для тестов
    public void startServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (running) {
            try (Socket client = serverSocket.accept();
                 ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())) {

                List<Integer> numbers = (List<Integer>) in.readObject();
                boolean hasComposite = checkBatch(numbers);
                out.writeObject(hasComposite);
                out.flush();

            } catch (Exception e) {
                if (running) {
                    System.err.println("Error processing request: " + e.getMessage());
                }
            }
        }
    }

    public void stopServer() {
        running = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing server: " + e.getMessage());
        }

    }
}