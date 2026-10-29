package objects.movement;

public class Movement {

    // ==== variables ====
    private double x;
    private double y;
    private double targetX;
    private double targetY;
    private double speed;
    private boolean moving = false;

    // ==== Constructor ====
    public Movement(double startX, double startY, double speed) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
    }

    /// creates a starting position and an end position
    /// @param startX   start coordinate x
    /// @param startY   start coordinate y
    /// @param endX     end coordinate x
    /// @param endY     end coordinate y
    public void start(double startX, double startY, double endX, double endY) {
        this.x = startX;
        this.y = startY;
        this.targetX = endX;
        this.targetY = endY;
        moving = true;
    }

    /// updates each "step" the image takes by using the passed time
    /// @param delta    passed time
    /// @return         true if the animation is done aka the time passed - the steps taken is less then the total distance
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
