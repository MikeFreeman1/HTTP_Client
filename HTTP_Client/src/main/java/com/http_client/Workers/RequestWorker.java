/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.http_client.workers;

import static com.http_client.properties.Props.*;
import static com.http_client.dataGenerator.ClientDataGenerator.getRandNum;
import com.http_client.influxDBUtills.InfluxDBWriter;
import static com.http_client.workers.FileWorker.readFileFromResources;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author kape404
 */
public class RequestWorker {
    private String soapBody="";
    private String resp;
    private HttpClient httpclient;
    private StringEntity strEntity;
    private HttpPost post;
    private HttpResponse response;
    private HttpEntity respEntity;
    private long sla;
    private InfluxDBWriter point;

    public RequestWorker(InfluxDBWriter point) {
        this.point = point;
    }
   
    public String sendPostSOAPRequest() {
        try {
            soapBody = readFileFromResources(fileName).
                    replaceFirst("__UBINUM__", String.valueOf(getRandNum(minRandValue, maxRandValue)));
            
//            System.out.println(soapBody);
                        
            httpclient = new DefaultHttpClient();
            strEntity = new StringEntity(soapBody, "application/soap+xml", "UTF-8");
            
            post = new HttpPost(soapURL);
            post.setEntity(strEntity);
            
            long startTime = System.nanoTime();

            response = httpclient.execute(post);
            respEntity = response.getEntity();
            
            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;
            
            sla = timeElapsed;   
            point.setPoint("SOAP", sla);
            
            resp = EntityUtils.toString(respEntity);
        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e.toString()); 
        }
        return resp;
    }

    
}
