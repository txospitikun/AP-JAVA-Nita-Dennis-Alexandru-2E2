package org.example;
import java.net.Socket;

public class Player {
    private String name;
    private Socket socket;

    public Player(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }
}
