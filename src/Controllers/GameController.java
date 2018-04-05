package Controllers;

public abstract class GameController {
    public abstract void update(int move, int player);
    public abstract void turn();
    public abstract boolean sentMove(int move);
    public abstract int[] getPossibleMoves();
}
