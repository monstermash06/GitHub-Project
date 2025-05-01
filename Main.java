import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Main extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String HIGHSCORE_FILE = "highscore.txt";

    private Set<KeyCode> keys = new HashSet<>();
    private List<DrawableObject> objects = new ArrayList<>();
    private List<Mine> mines = new ArrayList<>();
    private Player thePlayer;
    private int score = 0;
    private int highScore = 0;
    private boolean gameRunning = true;

    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);

        // Read high score
        try {
            File file = new File(HIGHSCORE_FILE);
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                if (scanner.hasNextInt()) {
                    highScore = scanner.nextInt();
                }
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        scene.setOnKeyPressed(e -> keys.add(e.getCode()));
        scene.setOnKeyReleased(e -> keys.remove(e.getCode()));

        thePlayer = new Player();
        objects.add(thePlayer);

        new AnimationTimer() {
            public void handle(long now) {
                if (!gameRunning) return;

                // Background
                DrawableObject.drawBackground(gc, thePlayer.getX(), thePlayer.getY());

                // Player logic
                thePlayer.act(keys);

                // Score
                score = (int) thePlayer.distanceFromStart();

                // Spawn mines
                int cgridx = ((int) thePlayer.getX()) / 100;
                int cgridy = ((int) thePlayer.getY()) / 100;

                for (int dx = -4; dx <= 3; dx++) {
                    for (int dy = -4; dy <= 3; dy++) {
                        int gx = cgridx + dx;
                        int gy = cgridy + dy;
                        int gxCoord = gx * 100;
                        int gyCoord = gy * 100;
                        double dist = DrawableObject.distance(gxCoord, gyCoord, 0, 0);
                        int maxMines = (int)(dist / 1000);
                        for (int i = 0; i < maxMines; i++) {
                            if (Math.random() < 0.3) {
                                Mine mine = new Mine(gxCoord + Math.random() * 100, gyCoord + Math.random() * 100);
                                if (!mines.contains(mine)) {
                                    mines.add(mine);
                                }
                            }
                        }
                    }
                }

                mines.removeIf(m -> m.distance(thePlayer) > 800);

                for (Mine m : mines) {
                    if (m.distance(thePlayer) < 20) {
                        gameRunning = false;
                        if (score > highScore) {
                            highScore = score;
                            try {
                                PrintWriter writer = new PrintWriter(HIGHSCORE_FILE);
                                writer.println(highScore);
                                writer.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                }

                // Draw
                for (DrawableObject obj : objects) {
                    obj.draw(gc, thePlayer.getX(), thePlayer.getY());
                }

                for (Mine m : mines) {
                    m.act();
                    m.draw(gc, thePlayer.getX(), thePlayer.getY());
                }

                gc.setFill(Color.WHITE);
                gc.fillText("Score: " + score, 20, 20);
                gc.fillText("High Score: " + highScore, 20, 40);
            }
        }.start();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Mine Dodger Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
