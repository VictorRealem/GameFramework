package AI;

import Controllers.DataController;
import Controllers.ReversiController;

import java.util.*;

public class AI {
    private DataController dataController;
    private int totalTiles;
    public AI(ReversiController controller) {
        this.dataController = DataController.getInstance();
        totalTiles = 0;
    }

    public int makeMove(int[] dataset, int[] pMoves) {
        int move;
        switch (dataController.getAiDifficulty()) {
            case 0: {
                move = randomMove(pMoves);
                break;
            }
            case 1: {
                move = maxTilesMove(dataset, getPriotitymoves(pMoves));
                break;
            }
            default: {
                move = -1;
                System.out.println("Not a difficulty option");
                break;
            }
        }
        return move;
    }

    public HashMap<Integer, ArrayList<Integer>> getPriotitymoves(int[] pmdataset){
        HashMap<Integer, ArrayList<Integer>> priomoves = new HashMap<>();
        ArrayList<Integer> cornerMoves = new ArrayList<>();
        ArrayList<Integer> sideMoves = new ArrayList<>();
        ArrayList<Integer> softAvMoves = new ArrayList<>();
        ArrayList<Integer> hardAvMoves = new ArrayList<>();
        ArrayList<Integer> restMoves = new ArrayList<>();


        for(int i = 0; i < pmdataset.length; i++){
            if(pmdataset[i] == 0){
                continue;
            }

            if(getCorners().contains(i)){
                cornerMoves.add(i);
            }
            else if(getSides().contains(i)){
                sideMoves.add(i);
            }
            else if(getSoftAvoidance().contains(i)){
                softAvMoves.add(i);
            }
            else if(getHardAvoidance().contains(i)){
                hardAvMoves.add(i);
            }
            else{
                restMoves.add(i);
            }
        }

        priomoves.put(0,cornerMoves);
        priomoves.put(1,sideMoves);
        priomoves.put(2,restMoves);
        priomoves.put(3,softAvMoves);
        priomoves.put(4,hardAvMoves);

        return priomoves;
    }

    /**
     * Chooses a random move from all the posible Moves
     * Checks for each move if it is possible, if it is it will save the index from that move
     * It chooses a random index from the saved indexes
     * It returns the move associated with the random index
     * @param possibleMoves an int[] from the board where 0 = not possible, 1 = possible
     * @return int move The index on the board that is the move made
     */
    private int randomMove(int[] possibleMoves) {
        //System.out.println("AI is making random move");
        Random random = new Random();
        ArrayList<Integer> moves = new ArrayList<>(possibleMoves.length);
        for(int i = 0; i < possibleMoves.length; i++) {
            if(possibleMoves[i] == 1) {
                //System.out.println("Empty cell found: " + i);
                moves.add(i);
            }
        }
        moves.trimToSize();
        //System.out.println(moves.size());
        int index = random.nextInt(moves.size());
        //System.out.println("Random index: " + index);
        int move = moves.get(index);

        //System.out.println("Made up move: " + move);
        return move;
    }

    private int maxTilesMove(int[] dataset ,HashMap<Integer, ArrayList<Integer>> priorityMoves) {
        ReversiController controller = new ReversiController();

        /*if(priorityMoves.get(0).size() > 0) {
            moves = priorityMoves.get(0);
        }
        if(priorityMoves.get(1).size() > 0) {
            moves.addAll(priorityMoves.get(1));

        }
        if(priorityMoves.get(2).size() > 0) {
            moves.addAll(priorityMoves.get(2));
        }
        if(priorityMoves.get(3).size() > 0) {
            moves.addAll(priorityMoves.get(3));

        }
        if(priorityMoves.get(4).size() > 0) {
            moves.addAll(priorityMoves.get(4));
        }*/
        ArrayList<Integer> doableMoves = new ArrayList<>();

        int player = 2;
        int opponent = 1;

        if(dataController.getPlayerOne()) {
            player = 1;
            opponent = 2;
        }

        int prevPlayerTiles = 0;
        double prevMovePoint = 64.0;

        for(Map.Entry entrySet : priorityMoves.entrySet()) {
            int prio = (int) entrySet.getKey();

            ArrayList<Integer> moves = (ArrayList) entrySet.getValue();
            for(int move : moves) {
                int playerTiles = 0;
                int opponentTiles = 0;

                int[] newBoard = controller.calculateMove(move, player, dataset);
                for (int cell : newBoard) {
                    if (cell == player) {
                        playerTiles++;
                    } else if (cell == opponent) {
                        opponentTiles++;
                    }
                }
                double movePoint = 0.0;
                double prioWorth = 0.95;
                double tileWorth = 0.05;
                if(prio != 0) {
                    movePoint = (prioWorth * prio) + (tileWorth * playerTiles);
                }

                totalTiles += playerTiles;
                //System.out.println("Total Tiles: " + totalTiles);

                if (movePoint < prevMovePoint) {
                    prevMovePoint = movePoint;
                    doableMoves.clear();
                    doableMoves.add(move);
                } else if (movePoint == prevMovePoint) {
                    doableMoves.add(move);
                }
            }
        }

        Random r = new Random();
        int index = r.nextInt(doableMoves.size());
        return doableMoves.get(index);
    }


    private int predictOpponent(int[] board, int[] possibleMoves, int opponent) {
        int move = -1;

        int numTiles = 0;
        int prevNumTiles = 0;

        ReversiController controller = new ReversiController();
        for(int i = 0; i< possibleMoves.length; i++) {
            if(possibleMoves[i] == 1) {
                int[] newBoard = controller.calculateMove(i,opponent,board);
                for(int a = 0; a< newBoard.length; a++) {
                    if(newBoard[a] == opponent) {
                        numTiles++;
                    }
                }
                if(numTiles > prevNumTiles) {
                    prevNumTiles = numTiles;
                    move = i;
                }
            }
        }

        return move;
    }

    private ArrayList<Integer> getCorners() {
        ArrayList<Integer> corners = new ArrayList<>();
        corners.add(0);
        corners.add(7);
        corners.add(56);
        corners.add(63);
        return corners;
    }

    private ArrayList<Integer> getSides() {
        ArrayList<Integer> sides = new ArrayList<>();
        sides.add(2);
        sides.add(3);
        sides.add(4);
        sides.add(5);
        sides.add(16);
        sides.add(24);
        sides.add(32);
        sides.add(40);
        sides.add(23);
        sides.add(31);
        sides.add(39);
        sides.add(47);
        sides.add(58);
        sides.add(59);
        sides.add(60);
        sides.add(61);
        return sides;
    }

    private ArrayList<Integer> getSoftAvoidance() {
        ArrayList<Integer> softAvoidance = new ArrayList<>();
        softAvoidance.add(10);
        softAvoidance.add(11);
        softAvoidance.add(12);
        softAvoidance.add(13);
        softAvoidance.add(17);
        softAvoidance.add(25);
        softAvoidance.add(33);
        softAvoidance.add(41);
        softAvoidance.add(22);
        softAvoidance.add(30);
        softAvoidance.add(38);
        softAvoidance.add(46);
        softAvoidance.add(50);
        softAvoidance.add(51);
        softAvoidance.add(52);
        softAvoidance.add(53);
        return softAvoidance;
    }

    private ArrayList<Integer> getHardAvoidance() {
        ArrayList<Integer> hardAvoidance = new ArrayList<>();
        hardAvoidance.add(1);
        hardAvoidance.add(8);
        hardAvoidance.add(9);
        hardAvoidance.add(6);
        hardAvoidance.add(14);
        hardAvoidance.add(15);
        hardAvoidance.add(48);
        hardAvoidance.add(49);
        hardAvoidance.add(57);
        hardAvoidance.add(54);
        hardAvoidance.add(55);
        hardAvoidance.add(62);
        return hardAvoidance;
    }
}
