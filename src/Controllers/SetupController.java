package Controllers;

import DAL.TCPConnection;
import Views.LoginView;
import Views.SetupView;
import javafx.scene.Scene;
import javafx.stage.Stage;

//import Views.SetupView;

public class SetupController {
    String args[];
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

        // open setup screen
        this.InitializeSetupView();

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
