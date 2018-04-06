package Controllers;

import AI.AI;
import DAL.TCPConnection;
import Views.GameBoardView;
import javafx.scene.image.Image;

import javax.xml.soap.Text;
import java.awt.*;


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
        dataController.setScene(new GameBoardView(this, dataController.getData().length, dataController.getYourTurn()).createBoardScene(dataController.getData()));

        if(AI){
            //run AI code.
            AI ai = new AI();
            System.out.println("AI is made");
            int move = ai.makeMove(dataController.getPossibleMoves());
            System.out.println("AI made move " + move);
            sentMove(move);
            System.out.println("Move was supposed to be send");
            /*try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }*/

        }
    }


    @Override
    public int[] getPossibleMoves() {
        int[] pm = dataController.getPossibleMoves();
        return pm;
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

        updateScore();
        dataController.setScene(new GameBoardView(this, dataController.getData().length, dataController.getYourTurn()).createBoardScene(dataSet));
    }

    public void updateScore(){
        int[] dataSet = dataController.getData();

        int playerOneScore = 0;
        int playerTwoScore = 0;

        for(int counter = 0; counter < 9; counter++){
            if(dataSet[counter] == 1){
                playerOneScore++;
            }
            else if(dataSet[counter] == 2){
                playerTwoScore++;
            } else{
                // do nothing
            }
        }
        dataController.setScore(playerOneScore, playerTwoScore);
    }


    @Override
    public String getImage(int val) {
        if (val == 1){
            return "/Images/redCircle.png";
        }if (val == 2){
            return "/Images/blueX.png";

        }
        return null;
    }

    @Override
    public String getNamePlayer1() {
        return  dataController.getPlayerName();
    }

    @Override
    public String getNameOppenent() {
        return  dataController.getOpponentName();
    }

    @Override
    public boolean getPlayer1() {
        return dataController.getPlayerOne();
    }
}

