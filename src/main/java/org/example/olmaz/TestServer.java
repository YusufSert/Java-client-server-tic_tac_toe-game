package org.example.olmaz;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TestServer {
    public static void main(String[] args) throws IOException {
        List<Socket> sockets = new ArrayList<>();
        List<OutputStream> outs = new ArrayList<>();
        List<InputStream> ins = new ArrayList<>();

        char[][] board = new char[3][3];
        int x;
        int y;


        try {
            ServerSocket server = new ServerSocket(8009);
            sockets.add(server.accept());
            sockets.add(server.accept());
            for (Socket s : sockets) {
                outs.add(s.getOutputStream());
                ins.add(s.getInputStream());
            }
            while (true){
                for (int i = 0; i < ins.size(); i++) {
                    int msg = ins.get(i).read();
                    y = msg / 3;
                    x = msg % 3;
                    int sym = i == 0 ? 'o' : 'x';
                    board[y][x] = (char) sym;
                    outs.get(1 - i).write(msg);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());;
        }
    }
}


