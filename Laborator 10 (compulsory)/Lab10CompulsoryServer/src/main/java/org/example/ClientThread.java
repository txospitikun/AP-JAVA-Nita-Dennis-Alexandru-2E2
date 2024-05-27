package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private GameServer server;

    public ClientThread(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            String command;
            while ((command = input.readLine()) != null) {
                System.out.println("Message Received: " + command);
                if ("stop".equalsIgnoreCase(command)) {
                    output.println("Server stopped");

                    server.stopServer();

                    break;
                } else {
                    output.println("Server received the request: " + command);
                }
            }
            socket.close();
        } catch (IOException e) {
            System.out.println("Error in client thread: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
