import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Mine extends DrawableObject {

    private double redness = Math.random() * Math.PI * 2; // starting phase for oscillation
    private double radius = 6;

    public Mine(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    public void act() {
        redness += 0.05; // update oscillation
        if (redness > Math.PI * 2) {
            redness -= Math.PI * 2;
        }
    }

    
    public void drawMe(GraphicsContext gc) {
        double interp = (Math.sin(redness) + 1) / 2; // oscillates between 0 and 1
        Color color = new Color(1, interp, interp, 1); // red to white
        gc.setFill(color);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.fillOval(getX() - radius, getY() - radius, radius * 2, radius * 2);
        gc.strokeOval(getX() - radius, getY() - radius, radius * 2, radius * 2);
    }
}
