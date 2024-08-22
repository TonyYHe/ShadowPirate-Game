/** represents entities that can move in some way
 */
public abstract class MovingEntity extends GameObject {
    private double speed;

    /** creates a moving entity
     * @param x top left x coordinate of the moving entity
     * @param y top left y coordinate of the moving entity
     * @param speed speed of the moving entity
     */
    public MovingEntity(double x, double y, double speed) {
        super(x, y);
        this.speed = speed;
    }

    /** checks if the moving entity has reached the border of the map
     * @param level current level
     * @return a boolean value
     */
    public boolean outOfBound(Level level) {
        return (getX() <= level.getTopLeftBoundary().x || getX() >= level.getBottomRightBoundary().x ||
                getY() <= level.getTopLeftBoundary().y || getY() >= level.getBottomRightBoundary().y);
    }

    /** method moves the entity by the input amount in the x and y directions
     * */
    public void move(double xMove, double yMove) {
        setX(getX() + xMove);
        setY(getY() + yMove);
    }

    /** retrieves the speed of the moving entity
     * @return double
     */
    public double getSpeed() {
        return speed;
    }

    /** method sets a new speed for the moving eneity
     * @param speed a new speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
