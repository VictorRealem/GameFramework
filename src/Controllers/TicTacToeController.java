package Controllers;

import DAL.TCPConnection;
import Models.GameType;
import Views.GameBoardView;
import javafx.scene.Scene;


public class TicTacToeController extends GameController {

    private DataController dataController;
    private TCPConnection connection;
    private boolean playable = true;
    private boolean turnX = true;


    public TicTacToeController() {
        this.dataController = DataController.getInstance();
        this.connection = TCPConnection.getInstance();
    }

    public Scene initializeGame() {

        //connection.sentCommand("subscribe tictactoe");
        dataController.setDatasetType(GameType.Tictactoe);

        return new GameBoardView(this, dataController.getData().length, dataController.getYourTurn()).createBoardScene();
    }

    @Override
    public void turn() {

    }

    @Override
    public void sentMove(int move) {

    }

    @Override
    public void update(int move, int player) {
        int[] dataSet = dataController.getData();
        dataSet[move] = player;

        dataController.setData(dataSet);

        // UpdateView
    }
}

