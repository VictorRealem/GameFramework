package Views;

/*public class LoginView extends View {

    private TextField name, serverUrl, port ;
    private Button Login;

    public LoginView (String args[])
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        super.start(primaryStage);
        this.setLabels(super.grid);
        this.setTextboxes(super.grid);
        this.setbutton(super.grid);
        this.setEventHandlers();
    }

    private void setEventHandlers() {
        this.Login.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                SetupController setupController = new SetupController();
                if(setupController.login(name.getText(), serverUrl.getText(), port.getText())) {
                     Stage stage = (Stage)Login.getScene().getWindow();
                     stage.hide();
                } else {
                    // set error message
                }
            }
        });
    }

    private void setLabels(GridPane grid)
    {
        Platform.runLater( () -> {
            Label namelable = new Label("Name:");
            grid.add(namelable, 0, 0);

            Label serverUrlLabel = new Label("Server address:");
            grid.add(serverUrlLabel, 0, 1);

            Label portLabel = new Label("Port:");
            grid.add(portLabel, 0, 2);});
    }

    private void setTextboxes(GridPane grid)
    {
        name = new TextField();
        name.setPrefColumnCount(20);

        serverUrl = new TextField();
        serverUrl.setPrefColumnCount(20);
        serverUrl.setText("Localhost");

        port = new TextField();
        port.setPrefColumnCount(20);
        port.setText("7789");


        GridPane.setConstraints(name, 1, 0);
        grid.getChildren().add(name);

        GridPane.setConstraints(serverUrl, 1, 1);
        grid.getChildren().add(serverUrl);

        GridPane.setConstraints(port, 1, 2);
        grid.getChildren().add(port);
    }

    private void setbutton(GridPane grid)
    {
        Login = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(Login);
        grid.add(hbBtn, 1, 3);
    }
}*/

import Controllers.SetupController;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class LoginView {
    private TextField name, serverUrl, port ;
    private Button login;
    private SetupController controller;

    public LoginView(SetupController controller) {
        this.controller = controller;
    }

    public Scene getLoginScene() {
        GridPane gridPane = new GridPane();
        setLabels(gridPane);
        setTextboxes(gridPane);
        setbutton(gridPane);
        setEventHandlers();
        return new Scene(gridPane,600,300);
    }

    private void setEventHandlers() {
        this.login.setOnAction((ActionEvent e) -> {
            if(controller.checkName(name.getText())) {
                if (controller.login(name.getText(), serverUrl.getText(), port.getText())) {
                        controller.setScene(controller.InitializeSetupView());
                } else {
                    //error with loging in
                    port.setText("7789");
                    serverUrl.setText("Localhost");

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Could not Login");
                    alert.setContentText("The server could not log you in");
                    alert.showAndWait();
                }
            } else {
                //error with name syntax
                name.setText("");

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setHeaderText("Name contains a 'space' ");
                alert.setContentText("Your player name can't contain spaces");

                alert.showAndWait();
            }
        });
    }

    private void setLabels(GridPane grid)
    {
        Label nameLabel = new Label("Name:");
        grid.add(nameLabel, 0, 0);
        Label serverUrlLabel = new Label("Server address:");
        grid.add(serverUrlLabel, 0, 1);
        Label portLabel = new Label("Port:");
        grid.add(portLabel, 0, 2);
    }

    private void setTextboxes(GridPane grid)
    {
        name = new TextField();
        name.setPrefColumnCount(20);

        serverUrl = new TextField();
        serverUrl.setPrefColumnCount(20);
        serverUrl.setText("Localhost");

        port = new TextField();
        port.setPrefColumnCount(20);
        port.setText("7789");


        GridPane.setConstraints(name, 1, 0);
        grid.getChildren().add(name);

        GridPane.setConstraints(serverUrl, 1, 1);
        grid.getChildren().add(serverUrl);

        GridPane.setConstraints(port, 1, 2);
        grid.getChildren().add(port);
    }

    private void setbutton(GridPane grid)
    {
        login = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(login);
        grid.add(hbBtn, 1, 3);
    }
}
