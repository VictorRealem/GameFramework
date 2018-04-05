package AI;

import java.util.Random;

public class AI {
    public AI() {

    }

    public int makeMove(int[] pMoves) {
        System.out.println("AI is making random move");
        System.out.println(pMoves);
        int move;
        Random random = new Random();
        move = random.nextInt(pMoves.length-1);
        System.out.println("Made up move: " + move);
        return move;
    }
}
