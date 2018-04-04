package Controllers;

import DAL.TCPConnection;
import Models.GameType;
import Views.GameBoardView;
import javafx.application.Platform;


public class TicTacToeController extends GameController {

    private DataController dataController;
    private TCPConnection connection;
    private GameBoardView gameBoard;
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
        dataController.setPossibleMoves(new int[9]);
        dataController.setScene(new GameBoardView(this, dataController.getData().length, dataController.getYourTurn()).createBoardScene(dataController.getData()));
    }


    @Override
    public void turn() {
        boolean AI = dataController.getAI();

        if(AI){
            //run AI code.
        }else{
            dataController.setScene(new GameBoardView(this, dataController.getData().length, dataController.getYourTurn()).createBoardScene(dataController.getData()));
        }
    }

    public boolean isTurnX(){
        boolean turnX = dataController.getYourTurn();
        return turnX;
    }


    @Override
    public boolean sentMove(int move) {
        int[] pm = dataController.getPossibleMoves();
        if(dataController.getYourTurn()){
            for(int i : pm){
                if(pm[move] == move){
                    dataController.setYourTurn(false);
                    this.connection.sentCommand("MOVE " + move);
                    break;
                }
            }
        }

        return !dataController.getYourTurn();
    }


    @Override
    public void update(int move, int player) {
        int[] dataSet = dataController.getData();
        dataSet[move] = player;

        dataController.setData(dataSet);

        dataController.setScene(new GameBoardView(this, dataController.getData().length, dataController.getYourTurn()).createBoardScene(dataSet));

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

