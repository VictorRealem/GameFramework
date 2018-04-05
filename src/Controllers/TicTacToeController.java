package Controllers;

import DAL.TCPConnection;
import Views.GameBoardView;


public class TicTacToeController extends GameController {

    private DataController dataController;
    private TCPConnection connection;


    public TicTacToeController() {
        this.dataController = DataController.getInstance();
        this.connection = TCPConnection.getInstance();
    }

    /**
     * Initialize the gameboard by using the datacontroller to set the scene
     */
    public void initializeGame() {

        //dataController.setDatasetType(GameType.Tictactoe);

        dataController.setPossibleMoves(new int[]{1,1,1,1,1,1,1,1,1});
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
        return dataController.getYourTurn();
    }


    @Override
    public boolean sentMove(int move) {
        int[] pm = dataController.getPossibleMoves();
        if(dataController.getYourTurn()){
            if(pm[move] == 1){
                dataController.setYourTurn(false);
                this.connection.sentCommand("MOVE " + move);
            }
        }

        return !dataController.getYourTurn();
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

        dataController.setScene(new GameBoardView(this, dataController.getData().length, dataController.getYourTurn()).createBoardScene(dataSet));
    }
}

