/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.http_client.workers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author kape404
 */
public class FileWorker {

    private static String fileBody = "";
    private static String strCurrentLine = "";
    private static Class cl = FileWorker.class;

    public static String readFile(String pathToFile) {

        try (BufferedReader bufReader = new BufferedReader(new FileReader(pathToFile))) {

            while ((strCurrentLine = bufReader.readLine()) != null) {
                fileBody += strCurrentLine;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileBody;
    }

    public static synchronized String readFileFromResources(String fileName) throws IOException {

        try (InputStream is = cl.getClassLoader().getResourceAsStream(fileName); InputStreamReader isr = new InputStreamReader(is); BufferedReader bufReader = new BufferedReader(isr)) {
            strCurrentLine = "";
            fileBody = "";
            
            while ((strCurrentLine = bufReader.readLine()) != null) {
                fileBody += strCurrentLine;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileBody;
    }
}
