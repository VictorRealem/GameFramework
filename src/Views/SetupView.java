package Views;

import Controllers.DataController;
import Controllers.SetupController;
import DAL.TCPConnection;
import Models.GameType;
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
import javafx.scene.paint.Color;

import java.util.List;

/**
 * This view is the view in which you can select the game you want to play, and if possible to player you want to challenge.
 */
public class SetupView {
    private SetupController controller;
    private Label selectedGame;
    private Label selectedPlayer;
    private Button start;
    private VBox playBox;
    private HBox scnBox;
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

        scnBox = new HBox();
        playBox = new VBox();

        GridPane optionsBox = new GridPane();
        HBox bottomBox = new HBox();

        scnBox.setMinWidth(400);
        optionsBox.setMinWidth(150);

        scnBox.setPadding(new Insets(20,20,20,20));
        scnBox.setSpacing(10);

        optionsBox.setPadding(new Insets(20,20,20,20));
        optionsBox.setHgap(10);

        bottomBox.setPadding(new Insets(10,10,10,10));
        bottomBox.setAlignment(Pos.CENTER);

        setupGameList();
        setupSpacer();
        setupPlayList();

        optionsBox.add(new Label("OPTIONS"), 0,0);
        setupAIOption(optionsBox);
        setupSelected(optionsBox);

        setupBottomBox(bottomBox);

        pane.setCenter(scnBox);
        pane.setRight(optionsBox);
        pane.setBottom(bottomBox);

        return new Scene(pane,700,300);
    }

    /**
     * Sets up the bottom of the borderPane with a start game button and the update players button
     * @param BBox the HBox in the bottom
     */
    private void setupBottomBox(HBox BBox) {
        start = new Button("Start game");
        Button updatePlayList = new Button("Update players");

        start.setOnAction( (ActionEvent e) -> {
            TCPConnection connection = TCPConnection.getInstance();
            //System.out.println("challenge " + "\"" + selectedPlayer.getText() + "\" \"" + selectedGame.getText() + "\"");
            DataController dataController = DataController.getInstance();
            switch(selectedGame.getText()) {
                case "Tic-tac-toe": {
                    dataController.setDatasetType(GameType.Tictactoe);
                    connection.sentCommand("challenge " + "\"" + selectedPlayer.getText() + "\" \"" + selectedGame.getText() + "\"");
                    break;
                }
                case "Reversi": {
                    dataController.setDatasetType(GameType.Reversi);
                    connection.sentCommand("challenge " + "\"" + selectedPlayer.getText() + "\" \"" + selectedGame.getText() + "\"");
                    break;
                }
                default: {
                    System.out.println("This game doesn't exist");
                }
            }
        });
        start.setDisable(true);

        updatePlayList.setOnAction( (ActionEvent e) -> {
            setupPlayList();
        });

        BBox.getChildren().add(updatePlayList);
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
                DataController dataController = DataController.getInstance();
                if(newValue.equals("AI")) {
                    dataController.setAI(true);
                } else {
                    dataController.setAI(false);
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
        selectedGame = new Label("");

        selectedPlayer = new Label("");
        Label selLabel = new Label("SELECTED GAME");
        Label selPlayer = new Label ("SELECTED PLAYER");

        OBox.add(selLabel,0,2);
        OBox.add(selPlayer,0,3);

        OBox.add(selectedGame,1,2);
        OBox.add(selectedPlayer,1,3);
    }

    /**
     * Adds a VBox containing all the games on the server to the center HBox, uses the setupController to retrieve all the games
     * It alse sets up the mouse click event to change the selected game
     */
    private void setupGameList() {
        VBox gameBox = new VBox();
        gameBox.getChildren().add(new Label("GAMES"));
        List<String> gameList = controller.getDataList(0);

        for (String game: gameList) {
            Label l = new Label(game);
            l.setOnMouseClicked((MouseEvent) -> {
                DataController dataController = DataController.getInstance();
                switch (game) {
                    case "Tic-tac-toe": {
                        dataController.setDatasetType(GameType.Tictactoe);
                        break;
                    }
                    case "Reversi": {
                        dataController.setDatasetType(GameType.Reversi);
                        break;
                    }
                    default: {
                        System.out.println("This game is not supported by this application");
                    }
                }
                selectedGame.setText(l.getText());
                TCPConnection connection = TCPConnection.getInstance();
                connection.sentCommand("subscribe " + game);
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
     */
    private void setupPlayList() {
        playBox.getChildren().clear();
        playBox.getChildren().add(new Label("PLAYERS"));

        List<String> playList = controller.getDataList(1);

        for (String name: playList) {
            Label l = new Label(name);
            if(!name.equals(controller.getUserName())) {
                l.setOnMouseClicked((MouseEvent) -> {
                    selectedPlayer.setText(name);
                    start.setDisable(false);
                });
            } else {
                l.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
            playBox.getChildren().add(l);
        }

        playBox.setMinWidth(100);
        playBox.setPrefWidth(100);
        playBox.setMaxWidth(100);

        scnBox.getChildren().remove(playBox);
        scnBox.getChildren().add(playBox);
    }


    /**
     * Adds the spacer to the center HBox
     * This spacer makes it so there is some room between the games- and player list
     */
    private void setupSpacer() {
        Region spacer = new Region();
        spacer.setPrefWidth(100);
        HBox.setHgrow(spacer,Priority.ALWAYS);

        scnBox.getChildren().add(spacer);
    }

}
