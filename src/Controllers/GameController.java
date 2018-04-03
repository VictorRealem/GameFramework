package Controllers;

import javafx.scene.text.Text;

public abstract class GameController {
    public abstract void update(int move, int player);
    public abstract void turn();
    public abstract boolean sentMove(int move);
}
