package com.shilaeva;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.*;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    static final Logger logger = LogManager.getLogger(Client.class.getName());
    static int PORT = 5060;
    static final int THREADS_NUMBER = 10;
    static final int CLIENTS_NUMBER = 20;

    public static void main(String[] args) {
        try {
            String portString = args[0];
            PORT = Integer.parseInt(portString);
        } catch (Exception ignored) {
        }

        InetAddress address;
        try {
            address = InetAddress.getByName(null);
        } catch (IOException e) {
            logger.log(Level.ERROR, "IO exception: "+ e.getMessage());
            return;
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(THREADS_NUMBER);
        for (int i = 0; i < CLIENTS_NUMBER; ++i) {
            threadPool.submit(new ClientSession(address, PORT));
        }

        threadPool.shutdown();
    }
}
