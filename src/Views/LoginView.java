package Views;

import Controllers.SetupController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class LoginView extends View {

    TextField name, serverUrl, port ;
    Button Login;

    public LoginView (String args[])
    {
        super.LaunchView(args);

        this.setLabels(grid);
        this.setTextboxes(grid);
        this.setbutton(grid);
        this.setEventHandlers();

        super.ShowView();
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
        Label namelable = new Label("Name:");
        grid.add(namelable, 0, 0);

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
        Login = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(Login);
        grid.add(hbBtn, 1, 3);
    }


}
