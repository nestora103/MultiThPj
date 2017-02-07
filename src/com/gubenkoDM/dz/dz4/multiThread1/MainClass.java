package com.gubenkoDM.dz.dz4.multiThread1;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Nestor on 07.02.2017.
 */
public class MainClass {


    public static void main(String[] args) {
        System.out.println("Task 1");
        new PrinterL(new ArrayList<>(Arrays.asList("A", "B", "C")));

        Thread t1=new Thread(new PrinterL("A"));
        Thread t2=new Thread(new PrinterL("B"));
        Thread t3=new Thread(new PrinterL("C"));
        t1.start();
        t2.start();
        t3.start();
    }
}
