package Views;

/*
public class SetupView extends View {

    public SetupView(String args[])
    {
        super.LaunchView(args);
    }
}*/

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class SetupView {
    public SetupView() {

    }

    public Scene getSetupScene() {
        VBox gameBox = new VBox();
        //gameBox.setBackground(new Background(new BackgroundFill(Color.web("green"), CornerRadii.EMPTY, Insets.EMPTY)));

        VBox playBox = new VBox();
        //playBox.setBackground(new Background(new BackgroundFill(Color.web("blue"), CornerRadii.EMPTY, Insets.EMPTY)));

        HBox sceneBox = new HBox();
        //sceneBox.setBackground(new Background(new BackgroundFill(Color.web("red"), CornerRadii.EMPTY, Insets.EMPTY)));

        ArrayList<String> gameList = new ArrayList<>();
        ArrayList<String> playList = new ArrayList<>();

        gameList.add("Reversie");
        gameList.add("Tic Tac Toe");

        playList.add("Player 1");
        playList.add("Player 2");

        for (String game: gameList) {
            gameBox.getChildren().add(new Label(game));
        }
        for (String name: playList) {
            playBox.getChildren().add(new Label(name));
        }
        gameBox.setMinWidth(100);
        gameBox.setPrefWidth(100);
        gameBox.setMaxWidth(100);

        playBox.setMinWidth(100);
        playBox.setPrefWidth(100);
        playBox.setMaxWidth(100);

        Region spacer = new Region();
        spacer.setPrefWidth(375);
        HBox.setHgrow(spacer,Priority.ALWAYS);

        sceneBox.getChildren().add(gameBox);
        sceneBox.getChildren().add(spacer);
        sceneBox.getChildren().add(playBox);

        sceneBox.setPrefWidth(600);


        sceneBox.setPadding(new Insets(20,20,20,20));
        sceneBox.setSpacing(10);
        return new Scene(sceneBox,600,300);
    }

}
