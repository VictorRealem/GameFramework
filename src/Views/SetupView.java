package Views;

/*
public class SetupView extends View {

    public SetupView(String args[])
    {
        super.LaunchView(args);
    }
}*/

import Controllers.SetupController;
import DAL.TCPConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.List;

public class SetupView {
    private SetupController controller;
    //private GridPane optionsBox;
    private Label selectedGame;
    public SetupView(SetupController controller) {
        this.controller = controller;
    }

    public Scene getSetupScene() {
        BorderPane pane = new BorderPane();

        HBox sceneBox = new HBox();
        GridPane optionsBox = new GridPane();
        HBox bottomBox = new HBox();

        sceneBox.setMinWidth(400);
        optionsBox.setMinWidth(150);

        sceneBox.setPadding(new Insets(20,20,20,20));
        sceneBox.setSpacing(10);

        optionsBox.setPadding(new Insets(20,20,20,20));
        optionsBox.setHgap(10);

        bottomBox.setPadding(new Insets(10,10,10,10));
        bottomBox.setAlignment(Pos.CENTER);

        setupGameList(sceneBox);
        setupSpacer(sceneBox);
        setupPlayList(sceneBox);

        optionsBox.add(new Label("OPTIONS"), 0,0);
        setupAIOption(optionsBox);
        setupSelected(optionsBox);

        setupBottomBox(bottomBox);

        pane.setCenter(sceneBox);
        pane.setRight(optionsBox);
        pane.setBottom(bottomBox);

        return new Scene(pane,700,300);
    }
    private void setupBottomBox(HBox BBox) {
        Button start = new Button("Start game");
        start.setOnAction( (ActionEvent e) -> {
            TCPConnection connection = TCPConnection.getInstance();
            connection.sentCommand("challenge ");
        });
        start.setDisable(true);
        BBox.getChildren().add(start);
    }
    private void setupAIOption(GridPane OBox) {
        ComboBox<String> playOptions = new ComboBox<>();
        playOptions.getItems().addAll("Player", "AI");
        playOptions.setEditable(false);
        playOptions.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.equals("AI")) {
                    controller.setAI(true);
                } else {
                    controller.setAI(false);
                }
            }
        });
        OBox.add(new Label("Player or AI"),0,1);
        OBox.add(playOptions,1,1);
    }

    private void setupSelected(GridPane OBox) {
        controller.setGameType("Tic-tac-toe");
        selectedGame = new Label("Tic-tac-toe");
        Label selLabel = new Label("SELECTED GAME");
        OBox.add(selLabel,0,2);
        OBox.add(selectedGame,1,2);
    }


    private void setupGameList(HBox scnBox) {
        VBox gameBox = new VBox();
        gameBox.getChildren().add(new Label("GAMES"));
        List<String> gameList = controller.getDataList(0);

        for (String game: gameList) {
            Label l = new Label(game);
            l.setOnMouseClicked((MouseEvent) -> {
                controller.setGameType(l.getText());
                selectedGame.setText(l.getText());
            });
            gameBox.getChildren().add(l);
        }

        gameBox.setMinWidth(100);
        gameBox.setPrefWidth(100);
        gameBox.setMaxWidth(100);

        scnBox.getChildren().add(gameBox);
    }

    private void setupPlayList(HBox scnBox) {
        VBox playBox = new VBox();
        playBox.getChildren().add(new Label("PLAYERS"));
        List<String> playList = controller.getDataList(1);

        for (String name: playList) {
            playBox.getChildren().add(new Label(name));
        }

        playBox.setMinWidth(100);
        playBox.setPrefWidth(100);
        playBox.setMaxWidth(100);

        scnBox.getChildren().add(playBox);
    }

    private void setupSpacer(HBox scnBox) {
        Region spacer = new Region();
        spacer.setPrefWidth(100);
        HBox.setHgrow(spacer,Priority.ALWAYS);

        scnBox.getChildren().add(spacer);
    }

}
