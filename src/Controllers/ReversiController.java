package Controllers;

import DAL.TCPConnection;

public class ReversiController extends GameController {

    private DataController dataController;
    private TCPConnection connection;

    public ReversiController()
    {
        this.dataController = DataController.getInstance();
        this.connection = TCPConnection.getInstance();


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
        for(int counter = 1; counter <= up; counter++){
            boolean allowTurning = false;
            if(dataSet[move + (8 * counter)] == player){
                allowTurning = true;
            }
            if(allowTurning){
                for(counter = 1; counter <= up; counter++){
                    if(dataSet[move + (8 * counter)] != player){
                        dataSet[move + (8 * counter)] = player;
                    }
                }
            }

        }

        // check downward
        for(int counter = 1; counter <= down; counter++){
            boolean allowTurning = false;
            if(dataSet[move - (8 * counter)] == player){
                allowTurning = true;
            }
            if(allowTurning){
                for(int counter = 1; counter <= down; counter++){
                    if(dataSet[move - (8 * counter)] != player){
                        dataSet[move - (8 * counter)] = player;
                    }
                }
            }

        }

        // check left
        for(int counter = 1; counter <= left; counter++){
            boolean allowTurning = false;
            if(dataSet[move - counter] == player){
                allowTurning = true;
            }
            if(allowTurning){
                for(int counter = 1; counter <= left; counter++){
                    if(dataSet[move - counter] != player){
                        dataSet[move - counter] = player;
                    }
                }
            }

        }

        // check right
        for(int counter = 1; counter <= right; counter++){
            boolean allowTurning = false;
            if(dataSet[move + counter] == player){
                allowTurning = true;
            }
            if(allowTurning){
                for(int counter = 1; counter <= right; counter++){
                    if(dataSet[move + counter] != player){
                        dataSet[move + counter] = player;
                    }
                }
            }

        }

        // check up left
        for(int counter = 1; counter <= left && counter <= up; counter++){
            boolean allowTurning = false;
            if(dataSet[move - (9 * counter)] == player){
                allowTurning = true;
            }
            if(allowTurning){
                for(int counter = 1; counter <= left && counter <= up; counter++){
                    if(dataSet[move - (9 * counter)] != player){
                        dataSet[move - (9 * counter)] = player;
                    }
                }
            }

        }

        // check up right
        for(int counter = 1; counter <= left && counter <= up; counter++){
            boolean allowTurning = false;
            if(dataSet[move - (7 * counter)] == player){
                allowTurning = true;
            }
            if(allowTurning){
                for(int counter = 1; counter <= left && counter <= up; counter++){
                    if(dataSet[move - (7 * counter)] != player){
                        dataSet[move - (7 * counter)] = player;
                    }
                }
            }

        }

        // check down left
        for(int counter = 1; counter <= left && counter <= up; counter++){
            boolean allowTurning = false;
            if(dataSet[move + (9 * counter)] == player){
                allowTurning = true;
            }
            if(allowTurning){
                for(int counter = 1; counter <= left && counter <= up; counter++){
                    if(dataSet[move + (9 * counter)] != player){
                        dataSet[move + (9 * counter)] = player;
                    }
                }
            }

        }

        // check down right
        for(int counter = 1; counter <= left && counter <= up; counter++){
            boolean allowTurning = false;
            if(dataSet[move + (7 * counter)] == player){
                allowTurning = true;
            }
            if(allowTurning){
                for(int counter = 1; counter <= left && counter <= up; counter++){
                    if(dataSet[move + (7 * counter)] != player){
                        dataSet[move + (9 * counter)] = player;
                    }
                }
            }

        }

        // update the dataset.
        dataController.setData(dataSet);

        // call to update view UpdateView
    }





    @Override
    public void turn() {
        //notify user or start ai.
    }

    @Override

    public void sentMove(int move) {
        this.connection.sentCommand("MOVE " + move);
    }
