package DAL;

import Helpers.EventHandler;

import java.io.*;
import java.net.Socket;

public class TCPConnection extends Thread {

    /***
     * Class instance for SingleTon.
     */
    private static TCPConnection ourInstance = null;

    /***
     * socket for server connection
     */
    private Socket clientSocket;

    /***
     * Static method for getting the object instance
     * @return TCPConnection
     */

    public static TCPConnection getInstance() {
        if(ourInstance == null)
            ourInstance = new TCPConnection();
        return ourInstance;
    }

    public boolean checkConnection() {
        return !clientSocket.isClosed();
    }

    public void logout() {
        sentCommand("logout");
        try {
            clientSocket.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /***
     * Initializing class with private constructor for SingleTon.
     */
    private TCPConnection() {

    }


    /***
     * Initializing connection with the server
     * @param host serverurl
     * @param port serverport
     * @throws Exception if it can't start a connection / server not found.
     */

    public void initializeConnection(String host, int port) throws Exception
    {
        if(clientSocket == null) {
            this.clientSocket = new Socket(host, port);
        }
    }


    /***
     * Send command to the server
     * @param command
     */

    public void sentCommand(String command)
    {
        try {
            PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            outToServer.println(command);
            Thread.sleep(100);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }


    /***
     * Recieves messages from the server.
     * sents the messages to the eventHandler
     * @throws Exception
     */

    public void Listen() throws Exception
    {
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        EventHandler handler = new EventHandler();
        while (checkConnection()) {
            String response = inFromServer.readLine();
            // For testing purposes.
            System.out.println("server: " + response);
            handler.HandleCommand(response);
        }
    }


    /***
     * Starts running the listen Thread.
     */

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
