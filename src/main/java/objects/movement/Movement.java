package objects.movement;

public class Movement {

    private double x;
    private double y;
    private double targetX;
    private double targetY;
    private double speed;
    private boolean moving = false;

    // ==== Constructor ====
    public Movement(double speed) {
        this.speed = speed;
    }

    public void start(double startX, double startY, double endX, double endY) {
        this.x = startX;
        this.y = startY;
        this.targetX = endX;
        this.targetY = endY;
        moving = true;
    }

    // Returns true when the movement finishes
    public boolean update(double delta) {

        if (!moving) return false;

        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.hypot(dx, dy);
        double step = speed * delta;

        if (distance <= step) {
            x = targetX;
            y = targetY;
            moving = false;
            return true;
        }

        x += (dx / distance) * step;
        y += (dy / distance) * step;

        return false;
    }

    public double getX() { return x; }
    public double getY() { return y; }
}
