package Controllers;

import DAL.TCPConnection;
import Models.GameType;
import Views.GameBoardView;


public class TicTacToeController extends GameController {

    private DataController dataController;
    private TCPConnection connection;
    private boolean playable = true;
    private boolean turnX = true;


    public TicTacToeController() {
        this.dataController = DataController.getInstance();
        this.connection = TCPConnection.getInstance();
    }

    /**
     * Initialize the gameboard by using the datacontroller to set the scene
     */
    public void initializeGame() {

        dataController.setDatasetType(GameType.Tictactoe);

        dataController.setScene(new GameBoardView(this, dataController.getData().length, dataController.getYourTurn()).createBoardScene());
    }


    @Override
    public void turn() {

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
}

