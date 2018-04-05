package AI;

import java.util.ArrayList;
import java.util.Random;

public class AI {
    public AI() {

    }

    public int makeMove(int[] pMoves) {
        System.out.println("AI is making random move");
        Random random = new Random();
        ArrayList<Integer> moves = new ArrayList<>(pMoves.length);
        for(int i = 0; i < pMoves.length; i++) {
            if(pMoves[i] == 1) {
                System.out.println("Empty cell found: " + i);
                moves.add(i);
            }
        }
        moves.trimToSize();
        for(int i = 0; i < moves.size(); i++) {
            System.out.println("in moves: " + moves.get(i));
        }
        System.out.println(moves.size());
        int index = random.nextInt(moves.size());
        System.out.println("Random index: " + index);
        int move = moves.get(index);

        System.out.println("Made up move: " + move);
        return move;
    }
}
