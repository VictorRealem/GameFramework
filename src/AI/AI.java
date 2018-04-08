package AI;

import Controllers.DataController;
import Controllers.ReversiController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AI {
    private DataController dataController;
    public AI(ReversiController controller) {
        this.dataController = DataController.getInstance();
    }

    public int makeMove(int[] dataset, int[] pMoves) {
        return blockingMove(dataset, pMoves);
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
        int player = 2;
        int opponent = 1;

        int opSumTiles = 0;
        int prevOpSumTiles = 64;
        int move = -1;

        if(dataController.getPlayerOne())
            player = 1;
            opponent = 2;

        for(int i = 0; i < possibleMoves.length; i++) {
            //System.out.println("Index: " + i + " - " + possibleMoves[i]);
            if(possibleMoves[i] == 1) {
                System.out.println("Before");
                System.out.println(Arrays.toString(dataset));
                int[] newBoard = controller.calculateMove(i, player, dataset);
                System.out.println("After");
                System.out.println(Arrays.toString(dataset));

                //int[] newBoard = new int[64];
                for(int x = 0; x < newBoard.length; x++) {
                    //System.out.println("New Board Index: " + x + " - " + newBoard[x]);
                    if(newBoard[x] == opponent) {
                        opSumTiles++;
                    }
                }
                System.out.println("Tiles for opponent if make move " + i + ": " + opSumTiles);
                if(opSumTiles < prevOpSumTiles) {
                    System.out.println("Switchen opSumTiles");
                    prevOpSumTiles = opSumTiles;
                    move = i;
                    System.out.println("Current move: " + move);
                }
            }
            opSumTiles = 0;

        }

        return move;
    }
}
