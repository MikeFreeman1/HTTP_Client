/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.http_client.dataGenerator;

import java.util.Random;


/**
 *
 * @author kape404
 */
public class ClientDataGenerator {
    
    public static int getRandNum(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }
    
    public static int getRandomByPerc(int[][] inputPercent) throws Exception {

        for (int i = 1; i < inputPercent.length; i++) {
            inputPercent[i][0] += inputPercent[i - 1][0];
        }

        int r = getRandNum(0, inputPercent[inputPercent.length - 1][0] - 1);

        for (int i = 0; i < inputPercent.length; i++) {
            if (r < inputPercent[i][0]) {
                return getRandNum(inputPercent[i][1], inputPercent[i][2]);
            }
        }

        throw new Exception("error_randomPercent");
    }
}
