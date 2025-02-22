package org.example.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;

public class Generator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] file = new String[3];
        for(int i=0;i<3;i++) {
            String[] file_question = {"File name is... :","Extension child is...(Don't use '.') :","Do you want you random file size?(y/n)"};
            System.out.println(file_question[i]);
            file[i] = sc.nextLine();
        }
        int size;
        while(true) {
            if(file[2].equals("y")) {
                System.out.println("Size range is...(KB, Please delimit ',') :");
                String[] ran = sc.next().split(",");
                int[] range = Stream.of(ran).mapToInt(Integer::parseInt).toArray();
                double random = Math.random() * (range[1]-range[0]) + range[0];
                size = (int)random;
                break;
            }else if(file[2].equals("n")) {
                System.out.println("File size is...(KB) :");
                size = sc.nextInt();
                break;
            }else {
                System.out.println("Please enter y/n");
                System.out.println("Do you want you random file size?(y/n)");
                file[2] = sc.next();
            }
        }
        sc.close();
        int j = 1;
        String filename = file[0] + "." + file[1];
        double real_size = (double)size*1000;
        byte[] data = new byte[(int)real_size];
        for(int i=1;i<=real_size;i++){
            double percent = (i/real_size)*100;
            int per = (int)(percent);
            double asciicode = Math.random()*(176-33)+33;
            int ascii = (int)asciicode;
            data[i-1] = (byte)ascii;
            if(per%2 == 0 && per/j == 2) {
                j++;
            }
        }
        System.out.println(" ");
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(data);
            System.out.println("Binary file generated successfully.\n File size is " + size + "KB");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}