package com.gubenkoDM.dz.dz4.multiThread1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Nestor on 07.02.2017.
 */
public class MainClass implements Runnable{
    private final Object monG=new Object();//монитор синхронизации задач
    private Thread task1;
    private Thread task2;
    private Thread task3;

    //файл вывода
    private FileOutputStream outS;
    private final int writePeriodSize=20;//период в мс
    private final int numRecords=50;//количество записей за 1 раз 1 потоком

    public static void main(String[] args) throws InterruptedException {
        MainClass main=new MainClass();
        //create threads task
        main.task1=new Thread(()->main.runTask1());
        main.task2=new Thread(()->main.runTask2());
        main.task3=new Thread(()->main.runTask3());
        //start task
        main.task1.start();
        main.task2.start();
        main.task3.start();
    }

    private void runTask1(){
        System.out.println("Task 1");
        PrinterL pr=new PrinterL(new ArrayList<>(Arrays.asList("A", "B", "C")),5);
        //create
        Thread t1=new Thread(()->{pr.print("A");});
        Thread t2=new Thread(()->{pr.print("B");});
        Thread t3=new Thread(()->{pr.print("C");});
        //start
        t1.start();
        t2.start();
        t3.start();
        //wait
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private  void runTask2(){
        try {
            task1.join();
            System.out.println("\nTask 2");
            try {
                outS=new FileOutputStream("multiFile");
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден!");
                e.printStackTrace();
            }
            //create
            Thread t1=new Thread(()->fileMultiWrite("\nПоток 1"));
            Thread t2=new Thread(()->fileMultiWrite("\nПоток 2"));
            Thread t3=new Thread(()->fileMultiWrite("\nПоток 3"));
            //start
            t1.start();
            t2.start();
            t3.start();
            //wait
            t1.join();
            t2.join();
            t3.join();
            //закрыли файл
            outS.close();
            System.out.println("Файл записан!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized  void fileMultiWrite(String writeStr){
        try {
            for (int i = 0; i < numRecords; i++) {
                outS.write(writeStr.getBytes());
                Thread.sleep(writePeriodSize);
            }
        }catch (InterruptedException e) {
            System.out.println("Ошибка ожидания потока!");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("Возникла ошибка в процессе записи в файл!");
            e.printStackTrace();
        }
    }

    private  void runTask3()  {
        try {
            task2.join();
            System.out.println("Task 3");
            MFU mfu=new MFU();
            //create
            Thread t1=new Thread(()->mfu.print(100,1)); //100
            Thread t2=new Thread(()->mfu.scan(50,1));   //50
            Thread t3=new Thread(()->mfu.print(50,2)); //50
            Thread t4=new Thread(()->mfu.scan(70,2)); //70
            //start
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            //wait
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}
