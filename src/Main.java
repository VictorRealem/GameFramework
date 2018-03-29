import Controllers.DataController;
import Controllers.TicTacToeController;
import DAL.TCPConnection;
import Models.GameType;

public class Main {
    public static void main(String[] args)
    {

        // TODO: getting host and port from user input;
        TCPConnection connection = TCPConnection.getInstance();
        connection.initializeConnection("Localhost", 7789);

        // Start listening from server
        connection.start();


        // Voorbeeld van wat de controllers moeten gaan doen nadat ze input hebben ontvangen. en voor testen :)
        connection.sentCommand("login Arjen");

        TicTacToeController cont = new TicTacToeController();
        cont.initializeGame();





        while(true)
        {

        }
    }
}
