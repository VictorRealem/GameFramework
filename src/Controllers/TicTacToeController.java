package Controllers;

import DAL.TCPConnection;
import Models.GameType;

public class TicTacToeController extends GameController {

    private DataController dataController;
    private TCPConnection connection;

    public TicTacToeController()
    {
        this.dataController = DataController.getInstance();
        this.connection = TCPConnection.getInstance();
    }

    public void initializeGame()
    {
        connection.sentCommand("subscribe tictactoe");
        dataController.setDatasetType(GameType.Tictactoe);

        //initializeView.
    }


    @Override
    public void update(int move, int player) {
        int[] dataSet = dataController.getData();
        dataSet[move] = player;

        dataController.setData(dataSet);

        // UpdateView
    }
}
