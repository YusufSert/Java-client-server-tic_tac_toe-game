package org.example.test;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        char[][] board;

        try {
            Socket socket = new Socket("localhost", 8009);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            // Get current state of the board
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            board = (char[][]) ois.readUnshared();

            // Choose player 1 | 2
            Scanner scanner = new Scanner(System.in);
            System.out.println("Player (1 | 2) ?");
            char[] sym = new char[]{'o', 'x'};
            int i = scanner.nextInt() - 1;


            //Listen the server and draw the board
            Thread listener = new Thread(() -> {
                System.out.println("Listener on !");
                try {
                    while (true) {
                        if (in.available() > 0) {
                            var msg = in.read();
                            var y = msg / 3;
                            var x = msg % 3;
                            board[y][x] = sym[i == 0 ? 1 : 0];
                            drawBoard(board);
                            if(winner(board)) {
                                System.out.println("Winner is " + sym[i == 0 ? 1 : 0]);
                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });

            //Send data to server and draw the board
            Thread writer = new Thread(() -> {
                System.out.println("Writer on !");
                while (true) {
                    int msg = scanner.nextInt();
                    var y = msg / 3;
                    var x = msg % 3;
                    board[y][x] = sym[i];
                    try {
                        out.write(msg);
                        out.flush();
                        drawBoard(board);
                        if(winner(board)) {
                            System.out.println("Winner is " + sym[i]);
                            break;
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
            writer.start();
            listener.start();
            writer.join();
            listener.join();

            in.close();
            out.close();
            ois.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void drawBoard(char[][] board) {
        System.out.printf(" %c | %c | %c ", board[0][0], board[0][1], board[0][2]);
        System.out.print("\n---|---|---\n");
        System.out.printf(" %c | %c | %c ", board[1][0], board[1][1], board[1][2]);
        System.out.print("\n---|---|---\n");
        System.out.printf(" %c | %c | %c ", board[2][0], board[2][1], board[2][2]);
        System.out.println();
    }


    private static boolean winner(char[][] board) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (board[0][i] != 0 && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return true;
            }
        }
        return false;
    }
}


//reset
        /*
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
        //Draw
            //drawBoard(board);

            //Send new position to server
            Scanner scanner = new Scanner(System.in);
            System.out.print("position x: ");
            int x = Byte.parseByte(scanner.next());
            System.out.print("position y: ");
            int y = Byte.parseByte(scanner.next());
            byte move = (byte) (1 + ( x * 3 + y));
            streamWriter.write(move);
            streamWriter.flush();

            //Refresh the board
            board[x][y] = 'X';
            drawBoard(board);
            ObjectInputStream objectInputStream2 = new ObjectInputStream(socket.getInputStream());
            board = (char[][]) objectInputStream2.readObject();
            drawBoard(board);

             char[][] board = new char[3][3];
            int output;
            int i = 0;
            drawBoard(board);
            while (i < 2) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Position: ");
                output = scanner.nextInt();
                out.write(output);
                out.flush();
                board = (char[][]) in.readUnshared();
                drawBoard(board);
                i++;
            }
         */