package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    private static final int PORT = 8000;
    private boolean running = true;

    public static void main(String[] args) {
        new GameServer().startServer();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            while (running) {
                Socket socket = serverSocket.accept();
                new ClientThread(socket, this).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void stopServer() {
        running = false;
        System.out.println("Server has been stopped.");
    }
}
