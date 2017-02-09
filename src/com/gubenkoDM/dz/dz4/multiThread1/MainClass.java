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
    private final Object monF=new Object();//монитор синхронизации записи в файл


    private boolean finishFlag=false;

    private FileOutputStream outS;
    private final int writePeriodSize=20;//период в мс
    private final int numRecords=50;//количество записей за 1 раз 1 потоком
    private volatile int jWrite=0;

    public static void main(String[] args) throws InterruptedException {
        MainClass main=new MainClass();

        new Thread(()->main.runTask1()).start();
        new Thread(()->main.runTask2()).start();
    }

    private void runTask1(){
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

    private  void runTask2(){
        synchronized (monG){
            finishFlag=false;
            System.out.println("\nTask 2");


            try {
                outS=new FileOutputStream("multiFile");
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден!");
                e.printStackTrace();
            }

            new Thread(()->fileMultiWrite("\nПоток 1")).start();
            new Thread(()->fileMultiWrite("\nПоток 2")).start();
            new Thread(()->fileMultiWrite("\nПоток 3")).start();
        }
    }

    private synchronized void fileMultiWrite(String writeStr){
        try {
            for (int i = 0; i < numRecords; i++) {

                outS.write(writeStr.getBytes());
                jWrite++;
                Thread.sleep(writePeriodSize);

            }
            //проверим все ли данные записали
            if (jWrite==numRecords*3){
                //закрыли файл
                outS.close();
                System.out.println("Файл записан!");
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


    @Override
    public void run() {

    }
}
