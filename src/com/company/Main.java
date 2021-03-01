package com.company;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
	    try {
            ServerSocket serverSocket = new ServerSocket(8001);
            boolean truth = false;
            while(!truth) {
                System.out.println("Waiting for clients...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                ClientThread thread = new ClientThread(socket);
                thread.start();
            }
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }
}
