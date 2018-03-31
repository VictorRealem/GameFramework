package Controllers;

import DAL.TCPConnection;
import Models.GameType;
import Views.LoginView;
import Views.SetupView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


//import Views.SetupView;

public class SetupController {
    private Stage primaryStage;
    private String host;
    private String port;
    private TCPConnection connection;

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
        connection = TCPConnection.getInstance();
        this.host = host;
        this.port = port;
        try {
            connection.initializeConnection(this.host, Integer.parseInt(this.port));
            connection.start();
        } catch (Exception e) {
            return false;
        }

        connection.sentCommand("login " + name);

        /*connection.sentCommand("get gamelist");
        DataController dataController = DataController.getInstance();
        dataController.setDatasetType(GameType.Tictactoe);
        connection.sentCommand("subscribe Tic-tac-toe");*/


        //returning true will close the login screen.
        return true;
    }

    public void setAI(boolean value) {
        DataController dataController = DataController.getInstance();
        dataController.setAI(value);

    }

    public void setGameType(String game) {
        System.out.println("Switcher game to " + game);
        DataController dataController = DataController.getInstance();
        switch (game) {
            case "Reversi": {
                dataController.setDatasetType(GameType.Reversi);
                break;
            }
            case "Tic-tac-toe": {
                dataController.setDatasetType(GameType.Tictactoe);
                break;
            }
        }
    }

    public List<String> getDataList(int type) {
        List<String> dataList;
        switch(type) {
            case 0: {
                connection.sentCommand("get gamelist");
                break;
            }
            case 1: {
                connection.sentCommand("get playerlist");
                break;
            }
            default: {
                break;
            }
        }
        DataController dataController = DataController.getInstance();
        //give the server time to respond
        try {
            Thread.sleep(20);
        } catch(InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        switch(type) {
            case 0: {
                dataList = dataController.getGamelist();
                break;
            }
            case 1: {
                dataList = dataController.getPlayerList();
                break;
            }
            default: {
                dataList = new ArrayList<>();
                break;
            }
        }

        return dataList;
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
        return new SetupView(this).getSetupScene();
    }
}
