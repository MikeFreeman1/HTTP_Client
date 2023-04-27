/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.http_client.tests;

import com.http_client.HTTP_Client;
import com.http_client.influxDBUtills.InfluxDBWriter;
import com.http_client.interfaces.RunTest;
import com.http_client.properties.Props;
import com.http_client.threads.ExecThreads;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kape404
 */
public class Test implements RunTest {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    
    private ExecThreads execThreads;
    private InfluxDBWriter influx;
    private Props properties;
    
    @Override
    public void init() {
//        logger.error("{ERROR}: Hey Boy!");
//        logger.trace("{TRACE}: Boy!");
        
        properties = new Props("utils.properties");
        properties.setDefaultProperties();
        influx = new InfluxDBWriter();
        influx.connectToInfluxDB(Props.influxPath, Props.influxUserName, Props.influxUserPass);
        influx.createDB(Props.influxDBName);
        influx.createRetentionPolicy("autogen", Props.influxDBName, "7d");
        influx.enableBatch(100, 200);
        
        execThreads = new ExecThreads(2);
    }

    @Override
    public void action() {
        try {
            execThreads.runTest(influx);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void end() {
        execThreads.endTest();
        influx.disableBatch();
    }
    
}
