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
    public boolean sentMove(int move) {

        DataController dataController = DataController.getInstance();
        int[] pm = dataController.getPossibleMoves();
        boolean validMove = false;
        if(dataController.getYourTurn()){
            for(int i : pm){
                if(i == move){
                    validMove = true;
                    this.connection.sentCommand("MOVE " + move);
                    break;
                }
            }
        }

        return validMove;
    }

    @Override
    public void update(int move, int player) {
        int[] dataSet = dataController.getData();
        dataSet[move] = player;

        dataController.setData(dataSet);

        int[] possibleMoves = new int[dataSet.length];

        for(int i = 0; i < dataSet.length; i++){
            possibleMoves[i] = 1;
            if(dataSet[i] != 0){
                possibleMoves[i] = 0 ;
            }
        }

        dataController.setPossibleMoves(possibleMoves);

    }
}

