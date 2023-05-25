package org.example.olmaz;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{

    protected Socket incoming;
    public ClientHandler(Socket incoming) {this.incoming = incoming;}
    public void run()
    {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(incoming.getOutputStream()));

            out.println("HEllo");
            out.println("Enter BYE to exit");
            out.flush();

            while (true)
            {
                String str = in.readLine();
                if(str == null)
                    break;

                System.out.println(incoming.getInetAddress() + " says-> " + str);

                out.println("Echo: " + str);
                out.flush();

                if (str.trim().equals("BYE"))
                    break;
            }

            in.close();
            out.close();
            incoming.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
