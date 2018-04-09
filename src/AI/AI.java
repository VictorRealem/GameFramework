package AI;

import Controllers.DataController;
import Controllers.ReversiController;

import java.util.ArrayList;
import java.util.Random;

public class AI {
    private DataController dataController;
    public AI(ReversiController controller) {
        this.dataController = DataController.getInstance();
    }

    public int makeMove(int[] dataset, int[] pMoves) {
        int move;
        //remove
        dataController.setAiDifficulty(1);
        //
        switch (dataController.getAiDifficulty()) {
            case 0: {
                move = randomMove(pMoves);
                break;
            }
            case 1: {
                move = blockingMove(dataset, pMoves);
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

    /**
     * Chooses a random move from all the posible Moves
     * Checks for each move if it is possible, if it is it will save the index from that move
     * It chooses a random index from the saved indexes
     * It returns the move associated with the random index
     * @param possibleMoves an int[] from the board where 0 = not possible, 1 = possible
     * @return int move The index on the board that is the move made
     */
    private int randomMove(int[] possibleMoves) {
        System.out.println("AI is making random move");
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

    private int blockingMove(int[] dataset ,int[] possibleMoves) {
        ReversiController controller = new ReversiController();
        ArrayList<Integer> corners = getCorners();
        ArrayList<Integer> sides = getSides();
        ArrayList<Integer> softAvoidance = getSoftAvoidance();
        ArrayList<Integer> hardAvoidance = getHardAvoidance();

        int player = 2;
        int opponent = 1;

        int opSumTiles = 0;
        int prevOpSumTiles = 64;
        int move = -1;

        int priority;
        int prevPriority = 5;

        if(dataController.getPlayerOne())
            player = 1;
            opponent = 2;

        for(int i = 0; i < possibleMoves.length; i++) {

            //System.out.println("Index: " + i + " - " + possibleMoves[i]);
            if(possibleMoves[i] == 1) {
                System.out.println("Checking move " + i);
                if(corners.contains(i)) {
                    System.out.println("Corner");
                    move = i;
                    break;
                } else if(sides.contains(i)) {
                    System.out.println("Sides");
                    priority = 1;
                } else if(hardAvoidance.contains(i)) {
                    System.out.println("Hard av");
                    priority = 4;
                } else if(softAvoidance.contains(i)) {
                    System.out.println("Soft av");
                    priority = 3;
                } else {
                    priority = 2;
                }
                int[] newBoard = controller.calculateMove(i, player, dataset);

                for(int x = 0; x < newBoard.length; x++) {
                    if(newBoard[x] == opponent) {
                        opSumTiles++;
                    }
                }
                System.out.println("Prev P: " + prevPriority);
                System.out.println("New p: " + priority);
                if(opSumTiles < prevOpSumTiles && prevPriority > priority) {
                    //new priority is smaller than prev priority
                    prevOpSumTiles = opSumTiles;
                    move = i;
                    prevPriority = priority;
                }
            }
            opSumTiles = 0;

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
