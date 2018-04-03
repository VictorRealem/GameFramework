package Controllers;

import DAL.TCPConnection;
import Models.GameType;
import Views.GameBoardView;
import javafx.application.Platform;

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

    /**
     * Initialize the gameboard by using the datacontroller to set the scene
     */
    public void initializeGame() {

        dataController.setDatasetType(GameType.Tictactoe);

        dataController.setScene(new GameBoardView(this, dataController.getData().length, dataController.getYourTurn()).createBoardScene());
    }


    @Override
    public void turn() {
        boolean AI = dataController.getAI();

        if(AI){
            //run AI code.
        }else{
            dataController.setScene(new GameBoardView(this, dataController.getData().length, dataController.getYourTurn()).createBoardScene());
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
                if(i == move){
                    dataController.setYourTurn(false);
                    this.connection.sentCommand("MOVE " + move);
                    dataController.setScene(new GameBoardView(this, dataController.getData().length, dataController.getYourTurn()).createBoardScene());
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

        int[] possibleMoves = new int[dataSet.length];

        for(int i = 0; i < dataSet.length; i++){
            possibleMoves[i] = 1;
            if(dataSet[i] != 0){
                possibleMoves[i] = 0 ;
            }
        }

        dataController.setPossibleMoves(possibleMoves);

    }

    @Override
    public void drawPlayer1(javafx.scene.text.Text text) {
        Platform.runLater(()-> {
            text.setText("x");
        });

    }
}

