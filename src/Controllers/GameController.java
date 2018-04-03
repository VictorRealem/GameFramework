package Controllers;

import javax.xml.soap.Text;

public abstract class GameController {
    public abstract void update(int move, int player);
    public abstract void turn();
    public abstract void sentMove(int move);
    public abstract void drawPlayer1(javafx.scene.text.Text text);
}
