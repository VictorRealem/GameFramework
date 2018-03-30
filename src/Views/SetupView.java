package Views;

/*
public class SetupView extends View {

    public SetupView(String args[])
    {
        super.LaunchView(args);
    }
}*/

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class SetupView {
    public SetupView() {

    }

    public Scene getSetupScene() {
        VBox box = new VBox();
        ArrayList<String> gameList = new ArrayList<>();

        gameList.add("Reversie");
        gameList.add("Tic Tac Toe");
        for (String game: gameList) {
            box.getChildren().add(new Label(game));
        }
        return new Scene(box,600,300);
    }

}
