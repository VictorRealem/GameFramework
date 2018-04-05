package AI;

import java.util.Random;

public class AI {
    public AI() {

    }

    public int makeMove(int[] pMoves) {
        System.out.println("AI is making random move");
        int move;
        Random random = new Random();
        move = random.nextInt(pMoves.length);
        return move;
    }
}
