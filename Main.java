import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    private Player thePlayer;
    private Canvas canvas;
    private Image background;
    private double highScore = 0;

    public void start(Stage stage) {
        thePlayer = new Player();
        background = new Image("bg1.png");

        Pane root = new Pane();
        canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(root);
        setupInput(scene);

        new AnimationTimer() {
            public void handle(long now) {
                thePlayer.act();
                drawBackground(gc, thePlayer.getX(), thePlayer.getY());
                thePlayer.drawMe(gc);
                drawScore(gc);
            }
        }.start();

        stage.setTitle("Mine Avoider");
        stage.setScene(scene);
        stage.show();
    }

    private void setupInput(Scene scene) {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W -> thePlayer.setDirection("W", true);
                case A -> thePlayer.setDirection("A", true);
                case S -> thePlayer.setDirection("S", true);
                case D -> thePlayer.setDirection("D", true);
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W -> thePlayer.setDirection("W", false);
                case A -> thePlayer.setDirection("A", false);
                case S -> thePlayer.setDirection("S", false);
                case D -> thePlayer.setDirection("D", false);
            }
        });
    }

    private void drawBackground(GraphicsContext gc, double x, double y) {
        // Draw background relative to player position
        double bgX = -(x % background.getWidth());
        double bgY = -(y % background.getHeight());

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                gc.drawImage(background, bgX + i * background.getWidth(), bgY + j * background.getHeight());
            }
        }
    }

    private void drawScore(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 20));
        gc.fillText("Score: " + String.format("%.2f", thePlayer.getScore()), 20, 30);
        gc.fillText("High Score: " + String.format("%.2f", highScore), 20, 55);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
