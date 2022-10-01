package com.jessca.rabbitmq.util;

/**
 * @author jessca
 */
public class SleepUtils {
    public static void sleep(int second){
        try {
            Thread.sleep(1000*second);
        }catch (InterruptedException _ignore){
            Thread.currentThread().interrupt();
        }
    }
}
