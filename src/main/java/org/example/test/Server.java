package org.example.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<Socket> sockets = new ArrayList<>();
        List<OutputStream> outs = new ArrayList<>();
        List<InputStream> ins = new ArrayList<>();
        List<ObjectOutputStream> oos = new ArrayList<>();
        Logger logger = Logger.getLogger(Server.class.getName());


        File file = new File("test.txt");
        ObjectOutputStream connectToFile = new ObjectOutputStream(new FileOutputStream(file, true));
        ObjectInputStream fromFile = new ObjectInputStream(new FileInputStream("test.txt")); // throws error if outputstream not open

        char[][] board;

        //Read saved board from file if not empty, if empty then used the newly created board;
        if(file.length() > 4) {
            board = (char[][]) fromFile.readUnshared();
        }else {
           board = new char[3][3];
        }

        ServerSocket server = new ServerSocket(8009);
        logger.log(Level.INFO, "Server started !");
        while (true) {
            //Listen for join
            sockets.add(0, server.accept());
            sockets.add(1, server.accept());

            // Run the thread
            Thread thread = new Thread(() -> {
                try {
                    //Set up the IO
                    for (int j = 0; j < 2; j++) {
                        outs.add(j, sockets.get(j).getOutputStream());
                        ins.add(j, sockets.get(j).getInputStream());
                        oos.add(j, new ObjectOutputStream(sockets.get(j).getOutputStream()));
                    }
                    //Send saved game board to clients
                    for (var o : oos) {
                        o.writeUnshared(board);
                        o.flush();
                    }
                    //Start the game
                    while (true){
                        for (int i = 0; i < 2; i++) {

                            // Listen clients for move
                            int msg = ins.get(i).read();


                            //Convert 1-D array_index to 2-d array_index
                            var y = msg / 3;
                            var x = msg % 3;
                            int sym = i == 0 ? 'o' : 'x';
                            board[y][x] = (char) sym;
                            //Send opponent's move client
                            outs.get(1 - i).write(msg);

                            // Deleted to old file
                            if(file.delete())
                                System.out.println("File deleted");
                            // Creat new file
                            File copyFile = new File("test.txt");
                            if(copyFile.createNewFile())
                                System.out.println("File created");
                            // Save current board state to file
                            ObjectOutputStream toFile2 = new ObjectOutputStream(new FileOutputStream(file));
                            toFile2.writeUnshared(board);
                            toFile2.flush();
                            toFile2.close();
                        }
                    }
                }catch (Exception e) {
                    System.out.println(e.toString());
                }
            });
            thread.start();
        }
    }
}

