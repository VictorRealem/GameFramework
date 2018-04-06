package Views;

import Controllers.SetupController;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class LoginView
 * This class manages the login scene
 * Its controller is the SetupController
 */
public class LoginView {
    private TextField name, serverUrl, port ;
    private Button login;
    private SetupController controller;

    public LoginView(SetupController controller) {
        this.controller = controller;
    }

    /**
     * Makes a gridpane and calls the setters methods in this class to set the labels/textfields etc...
     * @return the scene of the login screen
     */
    public Scene getLoginScene() {
        BorderPane bp = new BorderPane();
        GridPane gridPane = new GridPane();
        bp.setTop(gridPane);
        setStyling(gridPane,bp);
        setLabels(gridPane);
        setTextboxes(gridPane);
        setbutton(gridPane);
        setEventHandlers();
        return new Scene(bp,600,300);
    }

    /**
     * Ties the login button to the login function in the SetupController
     * Gives appropiate error msgs
     */
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
                alert.setHeaderText("Name error");
                alert.setContentText("Your player name can't contain spaces and must at least be 1 character long and cant be longer than 7 characters");

                alert.showAndWait();
            }
        });
    }

    /**
     * Sets the labels name/serverURl and port
     * @param grid the grid to which the labels need to be added
     */
    private void setLabels(GridPane grid)
    {
        Label nameLabel = new Label("Name:");
        grid.add(nameLabel, 0, 0);
        //77.162.40.81      Pi server
        //145.33.225.170    School server
        nameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        Label serverUrlLabel = new Label("Server address:");
        serverUrlLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        grid.add(serverUrlLabel, 0, 1);
        Label portLabel = new Label("Port:");
        portLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        grid.add(portLabel, 0, 2);
    }

    /**
     * Sets the textboxes name, server address and port
     * @param grid the grid to which the textfields need to be added
     */
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

    /**
     * Sets the button the logs the user in on the grid
     * @param grid the grid to which the button need to be added
     */
    private void setbutton(GridPane grid)
    {
        login = new Button("Login");
        login.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(login);
        grid.add(hbBtn, 1, 3);
    }

    private void setStyling(GridPane grid,BorderPane bp){
        Image image = new Image("Images/loginLine.png");
        bp.setBottom(new ImageView(image));
        bp.setStyle("-fx-background-color: #FAFAFA");
        grid.setVgap(5.0);
        grid.setHgap(5.0);
    }

}
