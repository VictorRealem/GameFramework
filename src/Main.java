import Controllers.SetupController;
import DAL.TCPConnection;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    protected Stage primaryStage;
    protected GridPane grid;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        SetupController setupController = new SetupController(primaryStage);
        primaryStage.setScene(setupController.InitializeLogin());

        primaryStage.setTitle("Project 2.3");
        primaryStage.show();
    }

    @Override
    public void stop() {
        //log out on exit
        TCPConnection connection = TCPConnection.getInstance();
        if (connection.checkConnection() && connection.checkSocket()) {
            connection.logout(); }
    }

    public static void main(String args[])
    {
        launch(args);
    }
}
