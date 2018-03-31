package Controllers;

import DAL.TCPConnection;
import Models.GameType;
import Views.LoginView;
import Views.SetupView;

import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.crypto.Data;

//import Views.SetupView;

public class SetupController {
    private Stage primaryStage;
    public SetupController(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }

    public Scene InitializeLogin()
    {
        return new LoginView(this).getLoginScene();
    }

    public boolean login(String name, String host, String port)
    {

        // Initialize server connection
        TCPConnection connection = TCPConnection.getInstance();
        try {
            connection.initializeConnection(host, Integer.parseInt(port));
            connection.start();
        } catch (Exception e) {
            return false;
        }

        connection.sentCommand("login " + name);
        connection.sentCommand("get gamelist");
        DataController dataController = DataController.getInstance();
        dataController.setDatasetType(GameType.Tictactoe);
        connection.sentCommand("subscribe Tic-tac-toe");

        //returning true will close the login screen.
        return true;
    }

    public boolean checkName(String name) {
        for (Character c: name.toCharArray()) {
            if(c.equals(' ')) {
                return false;
            }
        }
        return true;
    }

    public void setScene(Scene sceneToSet) {
        synchronized (primaryStage) {
            primaryStage.setScene(sceneToSet);
        }
    }

    public Scene InitializeSetupView()
    {
        return new SetupView().getSetupScene();
    }
}
