import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Mine extends DrawableObject {
    private double phase = Math.random() * Math.PI * 2;

    public Mine(double x, double y) {
        super(x, y);
    }

    public void act() {
        phase += 0.1;
    }

    public double distance(Player p) {
        return DrawableObject.distance(this.x, this.y, p.getX(), p.getY());
    }

    
    public void drawMe(GraphicsContext gc, double offsetX, double offsetY) {
        double alpha = 0.5 + 0.5 * Math.sin(phase);
        gc.setFill(new Color(1, 0, 0, alpha));
        gc.fillOval(this.x - offsetX + 400 - 10, this.y - offsetY + 300 - 10, 20, 20);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Mine)) return false;
        Mine other = (Mine) obj;
        return (int) other.x == (int) this.x && (int) other.y == (int) this.y;
    }

    public int hashCode() {
        return ((int) x) * 31 + ((int) y);
    }
}
