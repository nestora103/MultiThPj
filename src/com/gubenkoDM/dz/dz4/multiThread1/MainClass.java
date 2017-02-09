package com.gubenkoDM.dz.dz4.multiThread1;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Nestor on 07.02.2017.
 */
public class MainClass implements Runnable{
    private final Object monG=new Object();
    private boolean finishFlag=false;

    public static void main(String[] args) throws InterruptedException {
        MainClass main=new MainClass();

        new Thread(()->main.runTask1()).start();
        new Thread(()->main.runTask2()).start();
    }

    private synchronized void runTask1(){
        synchronized (monG){
            System.out.println("Task 1");

            //new PrinterL(new ArrayList<>(Arrays.asList("A", "B", "C")));

            PrinterL pr=new PrinterL(new ArrayList<>(Arrays.asList("A", "B", "C")),5,finishFlag);

            new Thread(()->{pr.print("A");}).start();
            new Thread(()->{pr.print("B");}).start();
            new Thread(()->{pr.print("C");}).start();

            try {
                //подождем пока порожденные этим потоком дочерние потоки закончат свою работу
                while (!pr.isFinishFlag()){
                    Thread.sleep(1);
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void runTask2(){
        synchronized (monG){
            System.out.println("");
            System.out.println("Task 2");
        }
    }


    @Override
    public void run() {

    }
}
