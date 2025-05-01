import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import java.util.Set;

public class Player extends DrawableObject {
    private double vx = 0, vy = 0;

    public Player() {
        super(300, 300);
    }

    public void act(Set<KeyCode> keys) {
        double ax = 0, ay = 0;
        if (keys.contains(KeyCode.W)) ay -= 0.2;
        if (keys.contains(KeyCode.S)) ay += 0.2;
        if (keys.contains(KeyCode.A)) ax -= 0.2;
        if (keys.contains(KeyCode.D)) ax += 0.2;

        vx += ax;
        vy += ay;

        vx *= 0.95;
        vy *= 0.95;

        x += vx;
        y += vy;
    }

    public double distanceFromStart() {
        return DrawableObject.distance(this.x, this.y, 300, 300);
    }

    
    public void drawMe(GraphicsContext gc, double offsetX, double offsetY) {
        gc.setFill(Color.BLUE);
        gc.fillOval(this.x - offsetX + 400 - 10, this.y - offsetY + 300 - 10, 20, 20);
    }
}
