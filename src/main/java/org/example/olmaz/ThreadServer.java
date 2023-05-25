package org.example.olmaz;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ThreadServer {
    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocket server = new ServerSocket(9000);
        while (true) {
            Socket socket = server.accept();
            Thread client = new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (true) {
                        System.out.println(in.readLine());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            client.start();
        }
    }
}