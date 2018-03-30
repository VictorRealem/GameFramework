import Controllers.DataController;
import Controllers.SetupController;
import Controllers.TicTacToeController;
import DAL.TCPConnection;
import Models.GameType;

public class Main {
    public static void main(String args[])
    {
        SetupController setupController = new SetupController();
        setupController.InitializeLogin(args);

    }
}
