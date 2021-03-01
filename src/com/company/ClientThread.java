package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader input = null;
        BufferedReader bufferedReader = null;

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hello client!");

            while(true) {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.println("Enter username (-1 for closing the connection).");
                String username = input.readLine();
                if(username.equals("-1")) {
                    out.println("User closed the connection");
                    break;
                }

                out.println("Enter password (-1 for closing the connection).");
                String password = input.readLine();
                if(password.equals("-1")) {
                    out.println("User closed the connection");
                    break;
                }

                bufferedReader = new BufferedReader(new FileReader("users.txt"));
                String line = null;
                boolean truth = false;

                while((line = bufferedReader.readLine()) != null) {
                    String[] str = line.split("\\|");
                    if(str[0].equals(username) && str[1].equals(password)) {
                        truth = true;
                    }
                }
                bufferedReader.close();
                if(truth) {
                    out.println("Success! You are now connected.");
                } else {
                    out.println("Invalid username or password, try again.");
                }
            }
            bufferedReader.close();
            input.close();
            out.close();
            socket.close();
        } catch(Exception ex) {
            System.err.println(ex.toString());
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
