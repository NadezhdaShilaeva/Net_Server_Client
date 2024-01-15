package com.shilaeva;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class Session extends Thread {
    static final Logger logger = LogManager.getLogger(Session.class.getName());
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public Session(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

        logger.log(Level.INFO, "Server connected to client");
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = in.readLine();
                if (message.equals("Bye.")) {
                    break;
                }

                logger.log(Level.INFO, "Message from client: " + message);
                out.println(message);
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "IO exception: " + e.getMessage());
        } finally {
            try {
                socket.close();
            }
            catch (IOException e) {
                logger.log(Level.ERROR, "IO exception: " + e.getMessage());
                logger.log(Level.WARN, "Socket not closed");
            }
        }
    }
}
