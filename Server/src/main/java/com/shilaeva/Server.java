package com.shilaeva;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final Logger logger = LogManager.getLogger(Server.class.getName());
    public static int PORT = 5060;

    public static void main(String[] args) {
        try {
            String portString = args[0];
            PORT = Integer.parseInt(portString);
        } catch (Exception ignored) {
        }

        try (ServerSocket server = new ServerSocket(PORT)) {
            logger.log(Level.INFO, "Server started on port " + PORT);

            while (true) {
                Socket clientSocket = server.accept();
                new Session(clientSocket).start();
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "Server socket IO exception: " + e.getMessage());
        }
    }
}
