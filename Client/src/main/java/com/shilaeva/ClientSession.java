package com.shilaeva;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSession implements Runnable {
    static final Logger logger = LogManager.getLogger(ClientSession.class.getName());
    private final InetAddress address;
    private final int port;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private static int counter = 0;
    private final int id = counter++;
    private int messagesNumber = 50;
    private int sleepTimeMs = 100;

    public ClientSession(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public void run() {
        createConnection();
        sendMessages();
    }

    private void createConnection() {
        logger.log(Level.INFO, "Making client " + id);

        while (socket == null) {
            try {
                socket = new Socket(address, port);
            } catch (IOException ignored) {
                //System.err.println("Socket failed");
            }
        }

        logger.log(Level.INFO, "Client " + id + " connect to server");

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e2) {
                logger.log(Level.ERROR, "IO exception: "+ e.getMessage());
                logger.log(Level.WARN, "Socket not closed");
            }
        }
    }

    private void sendMessages() {
        try {
            for (int i = 0; i < messagesNumber; i++) {
                out.println("Client " + id + ": " + i);
                String message = in.readLine();
                logger.log(Level.INFO, "Message from server: " + message);
                Thread.sleep(sleepTimeMs);
            }

            out.println("Bye.");
        } catch (IOException e) {
            logger.log(Level.ERROR, "IO exception" + e.getMessage());
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Interrupted exception: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.log(Level.ERROR, "IO exception" + e.getMessage());
                logger.log(Level.WARN, "Socket not closed");
            }
        }
    }

    public int getMessagesNumber() {
        return messagesNumber;
    }

    public int getSleepTimeMs() {
        return sleepTimeMs;
    }

    public void setMessagesNumber(int messagesNumber) {
        this.messagesNumber = messagesNumber;
    }

    public void setSleepTimeMs(int sleepTimeMs) {
        this.sleepTimeMs = sleepTimeMs;
    }
}
