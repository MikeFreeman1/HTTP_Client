/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.http_client.properties;

import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 *
 * @author kape404
 */
public class Props {
    private Properties properties;
    private URL url ;
    private Set<String> keys;
    
    public static String fileName;
    public static String soapURL;
    public static int maxRandValue;
    public static int minRandValue;
    
    public static int loadNormal;
    public static int stepLoadPercent;
    public static int initialDelay;
    public static int timeToFinishSec;
    public static int timeFromStartSec;
    
    public static String influxPath;
    public static String influxUserName;
    public static String influxUserPass;
    public static String influxDBName;
    
    //+ varInitialDelay;    время когда нужно увеличить нагрузку в сек

    public Props(String propsName) {
        properties = new Properties();
        url = ClassLoader.getSystemResource(propsName);
        
        try  {
            properties.load(url.openStream());
        } catch (Exception e) {
            System.out.println("{}: failed to get properties file");
            e.printStackTrace();
        }
        
        getListPropertyKeys();
    }

    public void setProperty(String key, String value){
        properties.put(key, value);
        getListPropertyKeys();
    }
    
    public void setDefaultProperties() {
        fileName = getStringProperty("props.fileName");
        soapURL = getStringProperty("props.soapURL");
        maxRandValue = getIntProperty("props.maxRandValue");
        minRandValue = getIntProperty("props.minRandValue");
        loadNormal = getIntProperty("props.loadNormal");
        stepLoadPercent = getIntProperty("props.stepLoadPercent");
        initialDelay = getIntProperty("props.initialDelay");
        timeToFinishSec = getIntProperty("props.timeToFinishSec");
        timeFromStartSec = getIntProperty("props.timeFromStartSec");
        influxPath = getStringProperty("props.influxPath");
        influxUserName = getStringProperty("props.influxUserName");
        influxUserPass = getStringProperty("props.influxUserPass");
        influxDBName = getStringProperty("props.influxDBName");
    }
    
    public String getStringProperty(String key){
       return properties.getProperty(key);
    }
    
    public int getIntProperty(String key){
       return Integer.valueOf(properties.getProperty(key));
    }
    
    public Set getListPropertyKeys(){   
        return keys = properties.stringPropertyNames();
    }
    
}
