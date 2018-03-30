import Controllers.DataController;
import Controllers.SetupController;
import Controllers.TicTacToeController;
import DAL.TCPConnection;
import Models.GameType;

public class Main {
    public static void main(String args[])
    {
        SetupController setupController = new SetupController();
        setupController.login("arjen2" , "Localhost", "7789");
        TCPConnection connection = TCPConnection.getInstance();
        connection.sentCommand("subscribe Tic-tac-toe");
        connection.sentCommand("MOVE 2");

        while(true)
        {

        }

    }
}
