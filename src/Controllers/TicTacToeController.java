package Controllers;

import DAL.TCPConnection;
import Models.GameType;
import Views.GameBoardView;
import javafx.scene.Scene;

import javax.xml.soap.Text;


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

    public boolean isTurnX(){
        boolean turnX = dataController.getYourTurn();
        return turnX;
    }


    @Override
    public void sentMove(int move) {

        DataController dataController = DataController.getInstance();
        if(dataController.getYourTurn()){
            this.connection.sentCommand("MOVE " + move);
        }

    }

    @Override
    public void update(int move, int player) {
        int[] dataSet = dataController.getData();
        dataSet[move] = player;

        dataController.setData(dataSet);

        // UpdateView
    }

    @Override
    public void drawPlayer1(javafx.scene.text.Text text) {
        text.setText("X");
    }
}

