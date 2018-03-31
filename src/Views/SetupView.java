package Views;

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

/**
 * This view is the view in which you can select the game you want to play, and if possible to player you want to challenge.
 */
public class SetupView {
    private SetupController controller;
    private Label selectedGame;
    public SetupView(SetupController controller) {
        this.controller = controller;
    }

    /**
     * Sets up the setup scene
     * The scene is a borderPane
     * The center is the main part with the 3 list (games, player)
     * The right is options part
     * The bottom is the start game button
     * @return the setup scene
     */
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

    /**
     * Sets up the bottom of the borderPane with a start game button
     * @param BBox the HBox in the bottom
     */
    private void setupBottomBox(HBox BBox) {
        Button start = new Button("Start game");
        start.setOnAction( (ActionEvent e) -> {
            TCPConnection connection = TCPConnection.getInstance();
            connection.sentCommand("challenge ");
        });
        start.setDisable(true);
        BBox.getChildren().add(start);
    }

    /**
     * Adds the comboBox containing player or AI options to the gridpane and sets up the event to handle the selection
     * @param OBox The gridpane containing all the options
     */
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

    /**
     * Adds the selectedGame label to the options gridpane and the selected game
     * @param OBox The options gridpane
     */
    private void setupSelected(GridPane OBox) {
        controller.setGameType("Tic-tac-toe");
        selectedGame = new Label("Tic-tac-toe");
        Label selLabel = new Label("SELECTED GAME");
        OBox.add(selLabel,0,2);
        OBox.add(selectedGame,1,2);
    }

    /**
     * Adds a VBox containing all the games on the server to the center HBox, uses the setupController to retrieve all the games
     * It alse sets up the mouse click event to change the selected game
     * @param scnBox the center HBox
     */
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

    /**
     * Similair to setupGameList, retrieves all the players via SetupController, puts them in a VBox and adds this to the center HBox
     * @param scnBox the Center HBox
     */
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

    /**
     * Adds the spacer to the center HBox
     * This spacer makes it so there is some room between the games- and player list
     * @param scnBox the center HBox
     */
    private void setupSpacer(HBox scnBox) {
        Region spacer = new Region();
        spacer.setPrefWidth(100);
        HBox.setHgrow(spacer,Priority.ALWAYS);

        scnBox.getChildren().add(spacer);
    }

}
