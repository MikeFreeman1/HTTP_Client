/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.http_client.influxDBUtills;

import java.util.concurrent.TimeUnit;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;

/**
 *
 * @author kape404
 */
public class InfluxDBWriter {
    private InfluxDB influxDB;
//    private Pong response;
    private BatchPoints batchPoints;
    private String dbName;
    private String policyName;
    private Point point;    
    
    public void connectToInfluxDB(String url, String username, String password){
        try{
           influxDB = InfluxDBFactory.connect(url, username, password); 
//           response = influxDB.ping();
//           logger.info(response.getVersion());
        }catch(Exception ex){
            System.out.println("Some problems with connect to InfluxDB");
            ex.printStackTrace();
        }
    }
    
    public void createDB(String dbName){
        this.dbName = dbName;
        influxDB.createDatabase(
                dbName);
        
    }
    
    public void createRetentionPolicy(String policyName, String dbName, String policy){
        this.policyName = policyName;
        influxDB.createRetentionPolicy(
                policyName, dbName, policy, 1, true);
    }
    
    public void enableBatch(int batchSize, int timePeriodInMillis){
        influxDB.enableBatch(batchSize, timePeriodInMillis, TimeUnit.MILLISECONDS);
    }
    
    public void disableBatch(){
        influxDB.disableBatch();
        influxDB.close();
    }
    
    public void setBatchPointsDefault(){
        batchPoints = BatchPoints
                .database(dbName)
                .retentionPolicy(policyName)
                .build();
    }
    
    public void setBatchPoints(String dbName, String policyName){
        batchPoints = BatchPoints
                .database(dbName)
                .retentionPolicy(policyName)
                .build();
    }
    
    public void setPoint(String measurement, long sla){
        point = Point.measurement(measurement)
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("SLA", sla)
                    .build();
            batchPoints.point(point);
    }
    
    public void writeToDB(){
        influxDB.write(batchPoints);
    }


//    public Pong getResponse() {
//        return response;
//    }

    public BatchPoints getBatchPoints() {
        return batchPoints;
    }

    public String getDbName() {
        return dbName;
    }

    public String getPolicyName() {
        return policyName;
    }
    
    
}
