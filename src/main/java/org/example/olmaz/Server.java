package org.example.olmaz;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws IOException {
            char[][] board = new char[3][3];
            List<ObjectOutputStream> outs = new ArrayList<>();
            List<InputStreamReader>  ins = new ArrayList<>();
            List<Socket> socks = new ArrayList<>();

            try {
                ServerSocket server = new ServerSocket(8009);
                socks.add(server.accept());
                socks.add(server.accept());
                System.out.println("Ready to go !");
                for (Socket s : socks) {
                    outs.add(new ObjectOutputStream(s.getOutputStream()));
                    ins.add(new InputStreamReader(s.getInputStream()));
                }
                int i = 0;
                int x;
                int y;
                while (i < 1) {
                    for (int r = 0; r < 2; r++) {
                        int p = ins.get(r).read();
                        x = p % 3;
                        y = p / 3;
                        board[y][x] = r == 0 ? 'X' : 'Y';
                        for (ObjectOutputStream o : outs) {
                            o.writeUnshared(board);
                            o.flush();
                            o.reset(); // badboy
                        }
                    }
                    i++;
                }


                ins.get(0).close();
                outs.get(0).close();
            } catch (Exception e) {
                System.out.println(e.getMessage());;
            }
    }
}


