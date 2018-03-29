package DAL;

import Helpers.EventHandler;

import java.io.*;
import java.net.Socket;

public class TCPConnection extends Thread {
    private static TCPConnection ourInstance = null;

    private Socket clientSocket;

    public static TCPConnection getInstance(String host, int port) {
        if(ourInstance == null)
            ourInstance = new TCPConnection(host, port);
        return ourInstance;
    }

    private TCPConnection(String host, int port) {
        try {
            this.clientSocket = new Socket(host,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sentCommand(String command)
    {
        try {
            PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            outToServer.println(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Listen() throws Exception
    {
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        EventHandler handler = new EventHandler();

        while (true) {
            String response = inFromServer.readLine();

            // For testing purposes.
            System.out.println("server: " + response);

            handler.HandleCommand(response);
        }
    }

    @Override
    public void run()
    {
        try {
            this.Listen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
