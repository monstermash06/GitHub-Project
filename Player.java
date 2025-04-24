import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends DrawableObject {
    private double forceX = 0;
    private double forceY = 0;

    private boolean up, down, left, right;
    private double startX = 300, startY = 300;
    private double score = 0;

    public Player() {
        super(300, 300); // Start at 300,300
    }

    public void setDirection(String dir, boolean pressed) {
        switch (dir) {
            case "W": up = pressed; break;
            case "S": down = pressed; break;
            case "A": left = pressed; break;
            case "D": right = pressed; break;
        }
    }

    @Override
    public void act() {
        // Adjust forces
        if (up) forceY = Math.max(forceY - 0.1, -5);
        else if (!down) slowY();

        if (down) forceY = Math.min(forceY + 0.1, 5);
        else if (!up) slowY();

        if (left) forceX = Math.max(forceX - 0.1, -5);
        else if (!right) slowX();

        if (right) forceX = Math.min(forceX + 0.1, 5);
        else if (!left) slowX();

        // Update position
        setX(getX() + forceX);
        setY(getY() + forceY);

        // Update score
        score = Math.sqrt(Math.pow(getX() - startX, 2) + Math.pow(getY() - startY, 2));
    }

    private void slowX() {
        if (Math.abs(forceX) < 0.25) forceX = 0;
        else forceX *= 0.975;
    }

    private void slowY() {
        if (Math.abs(forceY) < 0.25) forceY = 0;
        else forceY *= 0.975;
    }

    public double getScore() {
        return score;
    }

    @Override
    public void drawMe(GraphicsContext gc) {
        gc.setFill(Color.DODGERBLUE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.fillOval(getX() - 17.5, getY() - 17.5, 35, 35);
        gc.strokeOval(getX() - 17.5, getY() - 17.5, 35, 35);
    }
}
