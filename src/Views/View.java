package Views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class View extends Application{

    protected Stage primaryStage;
    GridPane grid;
    Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        scene = new Scene(grid,600, 300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Project 2.3");
        primaryStage.show();
    }

    public void ShowView()
    {

    }

    public void LaunchView(String args[])
    {
        launch(args);
    }
}
