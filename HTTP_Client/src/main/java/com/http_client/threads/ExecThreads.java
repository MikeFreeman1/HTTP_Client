/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.http_client.threads;

import com.http_client.influxDBUtills.InfluxDBWriter;
import com.http_client.properties.Props;
import com.http_client.workers.RequestWorker;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author kape404
 */
public class ExecThreads {
    private ScheduledExecutorService ses;
    private int threadsCount;
    private ArrayList<ScheduledFuture> strategyList;
    private Runnable task;
    
    private int delayNormal = 3600000 / Props.loadNormal;
    private int stepLoad = (Props.loadNormal * Props.stepLoadPercent) / 100;
    private int varStep = stepLoad;
    private int delay = 3600000 / varStep;
    private int timeToDecreaseStress = (Props.initialDelay * 2) / 1000;
    private int timeFromStartSec = Props.timeFromStartSec;
    private int initialDelay = Props.initialDelay;
    private int timeToFinishSec = Props.timeToFinishSec;
    

    public ExecThreads(int threadsCount) {
        this.threadsCount = threadsCount;
        ses = Executors.newScheduledThreadPool(this.threadsCount);
    }

    private Runnable createTasks(RequestWorker requestTask) {
        return task = () -> {
//            System.out.println("------task1 is running-------");
//            count1++;
            requestTask.sendPostSOAPRequest();
        };
    }

    private void setLoadStrategy(int startTimeInMillis, int delayInMillis, InfluxDBWriter statDBInstance) {
        strategyList = new ArrayList<>();
        for (int i = 0; i < this.threadsCount; i++) {
            Runnable task = createTasks(new RequestWorker(statDBInstance));
            ScheduledFuture scheduledFuture = ses.scheduleAtFixedRate(task, startTimeInMillis, delayInMillis, TimeUnit.MILLISECONDS);;
            strategyList.add(scheduledFuture);
        }
    }
    

    public void runTest(InfluxDBWriter statDBInstance) throws InterruptedException {
        setLoadStrategy(1, delayNormal, statDBInstance);
        setLoadStrategy(initialDelay, delay, statDBInstance);
        do {
//            System.out.println("timeFromStartSec :" + timeFromStartSec++);
            Thread.sleep(1000);
            if (timeFromStartSec == timeToDecreaseStress) {
                varStep += stepLoad;
                timeToDecreaseStress += initialDelay / 1000;
                statDBInstance.setPoint("RequestsPerHour", varStep);
            }

        } while (timeFromStartSec != timeToFinishSec);
    }

    public void endTest() {
//        System.out.println("Count is reach finishCount, cancel the scheduledFuture!");
        for (ScheduledFuture scheduledFuture : strategyList) {
            scheduledFuture.cancel(true);
        }
        ses.shutdown();
    }

//    ScheduledFuture<?> scheduledFuture2 = ses.scheduleAtFixedRate(task2, initialDelay, delay, TimeUnit.MILLISECONDS);
}
