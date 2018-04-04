package Controllers;

import DAL.TCPConnection;
import Models.GameType;
import Views.GameBoardView;

public class ReversiController extends GameController {

    private DataController dataController;
    private TCPConnection connection;

    public ReversiController() {
        this.dataController = DataController.getInstance();
        this.connection = TCPConnection.getInstance();
    }

    /**
     * Initialize the gameboard by using the datacontroller to set the scene
     */
    public void initializeGame(){

        dataController.setDatasetType(GameType.Reversi);
        dataController.setPossibleMoves(new int[64]);
        dataController.setScene(new GameBoardView(this, dataController.getData().length, dataController.getYourTurn()).createBoardScene(dataController.getData()));
    }

    @Override
    public void update(int move, int player) {

        // get the current boardstate
        int[] dataSet = dataController.getData();

        // set the selected square tot the players color
        dataSet[move] = player;

        // change stones between new and old stones..

        // the number of squares next to the move.
        int up = move / 8;
        int left = move % 8;

        int down = 7 - up;
        int right = 7 - left;


        // check upward
        for (int counter = 1; counter <= up; counter++) {
            boolean allowTurning = false;
            if (dataSet[move + (8 * counter)] == player) {
                allowTurning = true;
            }
            if (allowTurning) {
                for (counter = 1; counter <= up; counter++) {
                    if (dataSet[move + (8 * counter)] != player) {
                        dataSet[move + (8 * counter)] = player;
                    }
                }
            }

        }

        // check downward
        for (int counter = 1; counter <= down; counter++) {
            boolean allowTurning = false;
            if (dataSet[move - (8 * counter)] == player) {
                allowTurning = true;
            }
            if (allowTurning) {
                for (counter = 1; counter <= down; counter++) {
                    if (dataSet[move - (8 * counter)] != player) {
                        dataSet[move - (8 * counter)] = player;
                    }
                }
            }

        }

        // check left
        for (int counter = 1; counter <= left; counter++) {
            boolean allowTurning = false;
            if (dataSet[move - counter] == player) {
                allowTurning = true;
            }
            if (allowTurning) {
                for (counter = 1; counter <= left; counter++) {
                    if (dataSet[move - counter] != player) {
                        dataSet[move - counter] = player;
                    }
                }
            }

        }

        // check right
        for (int counter = 1; counter <= right; counter++) {
            boolean allowTurning = false;
            if (dataSet[move + counter] == player) {
                allowTurning = true;
            }
            if (allowTurning) {
                for (counter = 1; counter <= right; counter++) {
                    if (dataSet[move + counter] != player) {
                        dataSet[move + counter] = player;
                    }
                }
            }

        }

        // check up left
        for (int counter = 1; counter <= left && counter <= up; counter++) {
            boolean allowTurning = false;
            if (dataSet[move - (9 * counter)] == player) {
                allowTurning = true;
            }
            if (allowTurning) {
                for (counter = 1; counter <= left && counter <= up; counter++) {
                    if (dataSet[move - (9 * counter)] != player) {
                        dataSet[move - (9 * counter)] = player;
                    }
                }
            }

        }

        // check up right
        for (int counter = 1; counter <= left && counter <= up; counter++) {
            boolean allowTurning = false;
            if (dataSet[move - (7 * counter)] == player) {
                allowTurning = true;
            }
            if (allowTurning) {
                for (counter = 1; counter <= left && counter <= up; counter++) {
                    if (dataSet[move - (7 * counter)] != player) {
                        dataSet[move - (7 * counter)] = player;
                    }
                }
            }

        }

        // check down left
        for (int counter = 1; counter <= left && counter <= up; counter++) {
            boolean allowTurning = false;
            if (dataSet[move + (9 * counter)] == player) {
                allowTurning = true;
            }
            if (allowTurning) {
                for (counter = 1; counter <= left && counter <= up; counter++) {
                    if (dataSet[move + (9 * counter)] != player) {
                        dataSet[move + (9 * counter)] = player;
                    }
                }
            }

        }

        // check down right
        for (int counter = 1; counter <= left && counter <= up; counter++) {
            boolean allowTurning = false;
            if (dataSet[move + (7 * counter)] == player) {
                allowTurning = true;
            }
            if (allowTurning) {
                for (counter = 1; counter <= left && counter <= up; counter++) {
                    if (dataSet[move + (7 * counter)] != player) {
                        dataSet[move + (9 * counter)] = player;
                    }
                }
            }

        }

        // update the dataset.
        dataController.setData(dataSet);

        // update the possible moves dataset
        updatePossibleMoves();

        // call to update view UpdateView
    }

    private void updatePossibleMoves(){ // todo: this needs to be the player from the active client

        int[] dataSet = dataController.getData();

        int[] possibleMoves = new int[dataSet.length];

        int player;
        if(dataController.getPlayerOne()){
            player = 1;
        }
        else{
            player = 2;
        }

        for(int counter = 0; counter < 64; counter ++){
            if(checkPossibleMoves(counter, player)){
                possibleMoves[counter] = 1; // move is possible
            }
            else{
                possibleMoves[counter] = 0; // move is not possible
            }
        }
        dataController.setPossibleMoves(possibleMoves);

    }

    private boolean checkPossibleMoves(int move, int player){
        int[] dataSet = dataController.getData();
        int up = move / 8;
        int left = move % 8;

        int down = 7 - up;
        int right = 7 - left;

        // check upward
        for (int counter = 1; counter <= up; counter++) {
            if (dataSet[move + (8 * counter)] == player) {
                return true;
            }
        }

        // check downward
        for (int counter = 1; counter <= down; counter++) {
            if (dataSet[move - (8 * counter)] == player) {
                return true;

            }
        }

        // check left
        for (int counter = 1; counter <= left; counter++) {
            if (dataSet[move - counter] == player) {
                return true;
            }
        }

        // check right
        for (int counter = 1; counter <= right; counter++) {
            if (dataSet[move + counter] == player) {
                return true;
            }
        }

        // check up left
        for (int counter = 1; counter <= left && counter <= up; counter++) {
            if (dataSet[move - (9 * counter)] == player) {
                return true;
            }
        }

        // check up right
        for (int counter = 1; counter <= left && counter <= up; counter++) {
            if (dataSet[move - (7 * counter)] == player) {
                return true;
            }
        }

        // check down left
        for (int counter = 1; counter <= left && counter <= up; counter++) {
            if (dataSet[move + (9 * counter)] == player) {
                return true;
            }
        }

        // check down right
        for (int counter = 1; counter <= left && counter <= up; counter++) {
            if (dataSet[move + (7 * counter)] == player) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void turn() {
        //notify user or start ai.
        boolean AI = dataController.getAI();

        if(AI){
            //run AI code.
        }
        else{
            dataController.setScene(new GameBoardView(this, dataController.getData().length, dataController.getYourTurn()).createBoardScene(dataController.getData()));
        }
    }

    @Override

    public boolean sentMove(int move) {
        int[] possibleMoves = dataController.getPossibleMoves();
        if(dataController.getYourTurn()){
            if(possibleMoves[move] == 1){
                dataController.setYourTurn(false);
                this.connection.sentCommand("MOVE " + move);
            }
        }

        return !dataController.getYourTurn();
    }
}