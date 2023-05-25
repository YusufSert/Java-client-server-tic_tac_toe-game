package org.example.olmaz;

import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        char[][] board = new char[3][3];
        //address of a[i][j] = base_address + w * (i * c + j)
        int position = 1 * (2*3+2);
        System.out.println(position);
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        System.out.println(index);
    }
}
