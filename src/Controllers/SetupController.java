package Controllers;

import DAL.TCPConnection;
import Views.LoginView;
import Views.SetupView;

public class SetupController {
    String args[];
    public SetupController()
    {

    }

    public void InitializeLogin(String args[])
    {
        this.args = args;
        new LoginView(args);
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

    public void InitializeSetupView()
    {
        SetupView setupView = new SetupView(this.args);
    }
}
