package com.gubenkoDM.dz.dz4.multiThread1;

import java.util.ArrayList;

/**
 * Created by Nestor on 07.02.2017.
 */
public class PrinterL implements Runnable {
    private ArrayList<String> listL;
    //private String s;
    private  volatile int j=0;
    private  volatile int reout;
    private volatile String currentStr;
    private final Object mon=new Object();
    private boolean finishFlag;

    public PrinterL(ArrayList<String> listL,int reout,boolean finishFlag) {
        this.finishFlag = finishFlag;
        this.listL = listL;
        currentStr=this.listL.get(j);
        this.reout=reout;
    }

    public boolean isFinishFlag() {
        return finishFlag;
    }

    public void print(String str){
        synchronized (mon){
            try {
                for (int i = 0; i < reout ; i++) {
                    while(!currentStr.equals(str)){
                        mon.wait();
                    }
                    System.out.print(str);
                    //System.out.print("{номер цикла "+i+"}");
                    if (++j>2) {
                        j=0;
                    };
                    currentStr=listL.get(j);
                    //System.out.print("{текущий вывод "+currentStr+"}");
                    mon.notifyAll();
                }
                //формируем признак завершения задачи
                if (currentStr.equals(listL.get(listL.size()-1))){
                    finishFlag=true;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void run() {}
}
