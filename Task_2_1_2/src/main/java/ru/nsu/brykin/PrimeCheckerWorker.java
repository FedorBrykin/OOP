package ru.nsu.brykin;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class PrimeCheckerWorker implements AutoCloseable {
    private final int port;
    private ServerSocket serverSocket;
    private final ExecutorService executor;
    private volatile boolean running = true;

    public PrimeCheckerWorker(int port) {
        this.port = port;
        this.executor = Executors.newFixedThreadPool(10);
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Worker started on port " + port);

        executor.submit(() -> {
            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    executor.submit(() -> handleClient(clientSocket));
                } catch (SocketException e) {
                    if (running) {
                        System.err.println("Worker socket error: " + e.getMessage());
                    }
                } catch (IOException e) {
                    System.err.println("Worker accept error: " + e.getMessage());
                }
            }
        });
    }

    private void handleClient(Socket clientSocket) {
        try (DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
             DataInputStream in = new DataInputStream(clientSocket.getInputStream())) {

            while (running) {
                try {
                    int number = in.readInt();
                    if (number == -1) break;

                    boolean isPrime = isPrime(number);
                    out.writeBoolean(!isPrime);
                    out.flush();
                } catch (EOFException | SocketException e) {
                    break;
                } catch (Exception e) {
                    System.err.println("Error processing request: " + e.getMessage());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Client handling error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    private boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number == 2) return true;
        if (number % 2 == 0) return false;

        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void close() {
        running = false;
        executor.shutdownNow();
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing server socket: " + e.getMessage());
        }
    }
}