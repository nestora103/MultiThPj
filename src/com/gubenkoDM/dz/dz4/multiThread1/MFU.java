package com.gubenkoDM.dz.dz4.multiThread1;

/**
 * Created by DmitriX on 09.02.2017.
 */
public class MFU {
    private final int outPeriod=50;
    private final Object monPr=new Object();//монитор синхронизации задач печати
    private final Object monSc=new Object();//монитор синхронизации задач сканирования

    public void print(int printPageNumber,int printOrderNumber){
        synchronized (monPr){
            try {
                System.out.println("[Номер задания печати]:"+printOrderNumber);
                for (int i = 0; i < printPageNumber ; i++) {
                    System.out.println("Отпечатано страниц:" + (i + 1)+" {"+printOrderNumber+"}");
                    Thread.sleep(outPeriod);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void scan(int scanPageNumber,int scanOrderNumber){
        synchronized (monSc){
            try {
                System.out.println("[Номер задания сканирования]:"+scanOrderNumber);
                for (int i = 0; i < scanPageNumber ; i++) {
                    System.out.println("Отсканировано страниц:" + (i + 1)+" {"+scanOrderNumber+"}");
                    Thread.sleep(outPeriod);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
