package org.example.olmaz;

import java.io.*;

import java.io.*;

import java.io.*;

public class ObjectOutputStreamDemo {
    public static void main(String[] args) throws ClassNotFoundException {
        Object s = "Hello World!";
        Object i = 1974;

        try {
            // create a new file with an ObjectOutputStream
            File file = new File("test.ser");
            FileOutputStream out = new FileOutputStream(file, true); // false mode deletes old data when opening a stream
            ObjectOutputStream oout = new ObjectOutputStream(out);

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("test.ser"));

            if(file.length() > 4) { // empty file is 4 byte
                System.out.println(file.length());
                System.out.println("" + ois.readObject());
                System.out.println("" + ois.readObject());
            }
            //
            //write something in the file
            oout.writeObject(s);
            oout.writeObject(i);

            // close the stream
            oout.close();

            // create an ObjectInputStream for the file we created before



            // read and print what we wrote before

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}