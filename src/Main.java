import DAL.TCPConnection;

public class Main {
    public static void main(String[] args)
    {

        // TODO: getting host and port from user input;
        TCPConnection connection = TCPConnection.getInstance("Localhost", 7789);

        // Start listening from server
        connection.start();

        connection.sentCommand("login Arjen2");
        //connection.sentCommand("subscribe tictactoe");

        while(true)
        {

        }
    }
}
