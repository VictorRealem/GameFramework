package Controllers;

import DAL.TCPConnection;
import Models.GameType;
import Views.LoginView;
import Views.SetupView;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.util.ArrayList;
import javax.xml.crypto.Data;


//import Views.SetupView;

public class SetupController {
    private Stage primaryStage;
    private String host;
    private String port;

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
        if(host.equals("") || port.equals("")) { return false; }
        // Initialize server connection
        TCPConnection connection = TCPConnection.getInstance();
        this.host = host;
        this.port = port;
        try {
            connection.initializeConnection(this.host, Integer.parseInt(this.port));
            connection.start();
        } catch (Exception e) {
            return false;
        }

        //connection.sentCommand("login " + name);

        /*connection.sentCommand("get gamelist");
        DataController dataController = DataController.getInstance();
        dataController.setDatasetType(GameType.Tictactoe);
        connection.sentCommand("subscribe Tic-tac-toe");*/


        //returning true will close the login screen.
        return true;
    }

    public ArrayList<String> getGameList() {
        ArrayList<String> gameList = new ArrayList<>();

        TCPConnection connection = TCPConnection.getInstance();
        try {
            connection.initializeConnection(host, Integer.parseInt(port));
            connection.start();
        } catch (Exception e) {
            return gameList;
        }
        connection.sentCommand("get gamelist");
        return gameList;
    }

    public boolean checkName(String name) {
        if(name.equals("")) { return false; }
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
        ArrayList<String> gameList = getGameList();
        return new SetupView().getSetupScene();
    }
}
