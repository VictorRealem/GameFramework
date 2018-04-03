package Controllers;

public abstract class GameController {
    public abstract void update(int move, int player);
    public abstract void turn();
    public abstract void sentMove(int move);
}
