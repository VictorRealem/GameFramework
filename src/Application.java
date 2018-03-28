import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        stage.setTitle("Game App");
        stage.setHeight(800);
        stage.setWidth(1200);

        stage.show();
    }
}
