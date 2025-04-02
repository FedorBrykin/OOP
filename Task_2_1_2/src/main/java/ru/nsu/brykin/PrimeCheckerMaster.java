package ru.nsu.brykin;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeCheckerMaster implements AutoCloseable {
    private final List<Socket> workers = Collections.synchronizedList(new ArrayList<>());
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final int timeoutMs;
    private final int retries;
    private final List<String> workerAddresses;
    private final int basePort;
    private final AtomicInteger currentWorkerIndex = new AtomicInteger(0);

    public PrimeCheckerMaster(List<String> workerAddresses, int basePort, int timeoutMs, int retries) {
        this.workerAddresses = workerAddresses;
        this.basePort = basePort;
        this.timeoutMs = timeoutMs;
        this.retries = retries;
        connectToWorkers();
    }

    public boolean checkForComposite(List<Integer> numbers) throws Exception {
        if (workers.isEmpty()) {
            throw new Exception("No workers available");
        }

        List<Future<Boolean>> futures = new ArrayList<>();
        for (int number : numbers) {
            futures.add(executor.submit(() -> checkNumber(number)));
        }

        boolean hasComposite = false;
        for (Future<Boolean> future : futures) {
            try {
                if (future.get(timeoutMs * 2, TimeUnit.MILLISECONDS)) {
                    hasComposite = true;
                }
            } catch (Exception e) {
                System.err.println("Error getting result: " + e.getMessage());
                hasComposite = true;
            }
        }
        return hasComposite;
    }

    private Boolean checkNumber(int number) {
        Socket worker = getNextWorker();
        if (worker == null) {
            return true;
        }

        try {
            DataOutputStream out = new DataOutputStream(worker.getOutputStream());
            DataInputStream in = new DataInputStream(worker.getInputStream());

            out.writeInt(number);
            out.flush();

            return in.readBoolean();
        } catch (Exception e) {
            System.err.println("Error communicating with worker: " + e.getMessage());
            workers.remove(worker);
            try {
                worker.close();
            } catch (IOException ex) {
                System.err.println("Error closing failed worker connection: " + ex.getMessage());
            }
            return true;
        }
    }

    private Socket getNextWorker() {
        if (workers.isEmpty()) {
            connectToWorkers();
            if (workers.isEmpty()) {
                return null;
            }
        }

        int index = currentWorkerIndex.getAndIncrement() % workers.size();
        return workers.get(index);
    }

    private void connectToWorkers() {
        for (String address : workerAddresses) {
            String[] parts = address.split(":");
            String ip = parts[0];
            int port = parts.length > 1 ? Integer.parseInt(parts[1]) : basePort;

            if (workers.stream().anyMatch(s -> {
                try {
                    return s.getInetAddress().getHostAddress().equals(ip) && s.getPort() == port;
                } catch (Exception e) {
                    return false;
                }
            })) {
                continue;
            }

            for (int attempt = 0; attempt < retries; attempt++) {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), timeoutMs);
                    workers.add(socket);
                    System.out.println("Connected to worker at " + address);
                    break;
                } catch (IOException e) {
                    System.err.println("Attempt " + (attempt + 1) + " to connect to " + address + " failed");
                    if (attempt == retries - 1) {
                        System.err.println("Failed to connect to worker at " + address);
                    }
                }
            }
        }
    }

    @Override
    public void close() {
        executor.shutdownNow();
        for (Socket worker : workers) {
            try {
                DataOutputStream out = new DataOutputStream(worker.getOutputStream());
                out.writeInt(-1);
                out.flush();
                worker.close();
            } catch (IOException e) {
                System.err.println("Error closing worker connection: " + e.getMessage());
            }
        }
    }
}