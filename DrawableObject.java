import javafx.scene.canvas.GraphicsContext;

public abstract class DrawableObject {
    protected double x, y;

    public DrawableObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static void drawBackground(GraphicsContext gc, double offsetX, double offsetY) {
        gc.setFill(javafx.scene.paint.Color.DARKSLATEGRAY);
        gc.fillRect(0, 0, 800, 600);

        for (int x = -1; x < 10; x++) {
            for (int y = -1; y < 8; y++) {
                gc.setFill((x + y) % 2 == 0 ? javafx.scene.paint.Color.DIMGRAY : javafx.scene.paint.Color.GRAY);
                gc.fillRect(x * 100 - offsetX % 100, y * 100 - offsetY % 100, 100, 100);
            }
        }
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void draw(GraphicsContext gc, double offsetX, double offsetY) {
        drawMe(gc, offsetX, offsetY);
    }

    public abstract void drawMe(GraphicsContext gc, double offsetX, double offsetY);
}
