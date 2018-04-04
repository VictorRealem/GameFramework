package Views;

import Controllers.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Random;

public class GameBoardView{

    private GameController controller;
    private int boardSize;
    boolean turn;
    private ArrayList<Tile> board;


    public GameBoardView(GameController controller,int boardSize,boolean turn) {
        this.controller = controller;
        this.boardSize = boardSize;
        this.turn = turn;

    }


    public Scene createBoardScene(int[] dataSet){
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new TopPane(turn));
        borderPane.setBottom(new BottomPane());
        CenterPane centerPane = new CenterPane(boardSize,controller, dataSet);
        board = centerPane.getBoard();
        centerPane.setHgap(10);
        centerPane.setVgap(10);
        borderPane.setCenter(centerPane);
        borderPane.setLeft(new LeftPane());
        borderPane.setRight(new RightPane());

        return new Scene(borderPane,1000,900);
    }
}


class BottomPane extends HBox {

    // private TextField serverMessage = new TextField("You win");
    private Label serverMessage;


    public BottomPane() {
        setAlignment(Pos.CENTER);
        setLabel();
        setLayout();

    }

    private void setLabel(){
        serverMessage = new Label("You win");
        serverMessage.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        getChildren().add(serverMessage);
    }

    private void setLayout(){
        setStyle("-fx-border-color: black");
        setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
    }
}

class TopPane extends HBox {

    private Label labelTurn;
    private boolean turn;

    public TopPane(boolean turn) {
        this.turn = turn;

        setLayout();
        setTurn();

    }

    private void setTurn(){
        labelTurn = new Label();
        labelTurn.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        if (turn){
            labelTurn.setText("Your turn");
        }else {
            labelTurn.setText("Opponents turn");
        }
        getChildren().add(labelTurn);
    }

    private void setLayout(){
        setAlignment(Pos.CENTER);
        setSpacing(5);
        setStyle("-fx-border-color: black");
        setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
    }
}

class CenterPane extends GridPane {

    int boardSize;
    ArrayList<Tile> board;
    GameController controller;

    public CenterPane(int boardSize, GameController controller, int[] dataSet) {

        this.boardSize = boardSize;
        this.controller = controller;

        boardSize =  (int) Math.sqrt(boardSize);
        drawBoard(boardSize, dataSet);
        //setStylingPane();
        setLayout();

    }

    private void drawBoard(int boardSize, int[] dataset) {
        int count = 0;
        setAlignment(Pos.CENTER);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Tile tile = new Tile(controller,count);
                tile.setTranslateY(i * -1);
                int val = dataset[count];
                System.out.println("val: " + val);
                if(val == 1) {
                    tile.getTextField().setText("x");
                } else if(val == 2) {
                    tile.getTextField().setText("o");
                }
                add(tile, j, i);
                count++;
            }
        }
        System.out.println("-----------");
    }

    public ArrayList<Tile> getBoard() {
        return board;
    }

    private void setLayout(){
        setStyle("-fx-background-color: #FAFAFA");
        setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
    }
}

class LeftPane extends VBox {

    private Label p1 = new Label("Player 1 \n  score");
    private Label score = new Label("3");
    private Separator separator = new Separator();

    public LeftPane(){
        setLayout();
        setText();
    }

    private void setText(){
        p1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        score.setFont(Font.font("Verdana", FontWeight.BOLD, 35));

        separator.setOrientation(Orientation.HORIZONTAL);

        getChildren().add(p1);
        getChildren().add(separator);
        getChildren().add(score);
    }

    private void setLayout(){
        setAlignment(Pos.TOP_CENTER);
        setSpacing(5.0);
        setStyle("-fx-background-color: #005b96");

        //setStyle("-fx-border-color: blue");
        setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
    }
}

class RightPane extends VBox{

    private Label p2 = new Label("Player 2 \n  score");
    private Label score = new Label("5");
    private Separator separator = new Separator();

    public RightPane(){
        setLayout();
        setText();
    }

    private void setText(){
        p2.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        score.setFont(Font.font("Verdana", FontWeight.BOLD, 35));

        separator.setOrientation(Orientation.HORIZONTAL);

        getChildren().add(p2);
        getChildren().add(separator);
        getChildren().add(score);
    }

    private void setLayout(){
        setAlignment(Pos.TOP_CENTER);
        setSpacing(5.0);
        setStyle("-fx-background-color:  #ff4c4c");
        //setStyle("-fx-border-color: red");
        setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
    }

}
class Tile extends StackPane {
    private Text text = new Text();
    private GameController controller;
    private int index;

    public Tile(GameController controller, int count) {
        this.controller = controller;
        Random random = new Random();
        index = count;
        Rectangle border = new Rectangle(80, 80);
        border.setFill(null);
        border.setStroke(Color.BLACK);

        text.setFont(Font.font(72));

        setAlignment(Pos.CENTER);
        getChildren().addAll(border, text);

        this.setOnMouseClicked(event -> {
            /*if (!playable)
                return;*/
                System.out.println("Sending move");
                controller.sentMove(index);
                System.out.println("Move send");
                //System.out.println("sending to server");
                // checkState();

        });
    }

    public double getCenterX() {
        return getTranslateX();
    }

    public double getCenterY() {
        return getTranslateY();
    }

    public Text getTextField() {
        return text;
    }

    public String getValue() {
        return text.getText();
    }

}