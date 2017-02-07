package com.gubenkoDM.dz.dz4.multiThread1;

import java.util.ArrayList;

/**
 * Created by Nestor on 07.02.2017.
 */
public class PrinterL implements Runnable {
    private volatile static ArrayList<String> listL;
    private String s;
    private final Object mon=new Object();

    public PrinterL(ArrayList<String> listL) {
        this.listL = listL;
    }

    public PrinterL(String s) {
        this.s = s;

    }

    private void print(String str){
        synchronized (mon){
            for (int i = 0; i < 3 ; i++) {

            }
        }


    }

    @Override
    public void run() {
        print(s);
    }
}
