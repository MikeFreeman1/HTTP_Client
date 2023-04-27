package com.http_client;

import com.http_client.influxDBUtills.InfluxDBWriter;
import com.http_client.interfaces.RunTest;
import static com.http_client.properties.Props.*;
import com.http_client.tests.Test;
import com.http_client.threads.ExecThreads;
import java.util.ArrayList;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTP_Client {

//    private static final Logger logger = LoggerFactory.getLogger(HTTP_Client.class);
//    private void task() {
//        logger.error("{ERROR}: Hey Boy!");
//        logger.trace("{TRACE}: Boy!");
//        Props properties = new Props("utils.properties");
//        properties.setDefaultProperties();
//        ReqMethods send = new ReqMethods();
//        System.out.println(send.PostSOAPReq());
//
//    }
//    static int count1 = 0;      //сколько раз выполнится код
//    static int count2 = 0;

    public static void main(String[] args) throws InterruptedException {
            RunTest test = new Test();
            test.init();
            test.action();
            test.end();
        
    }

    

}
