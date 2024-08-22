import bagel.*;

/** represents a treasure, which is the objective of level 1
 */
public class Treasure extends GameObject {
    private final Image TREASURE = new Image("res/treasure.png");

    /** creates a new treasure
     * @param x top left x coordinate of the treasure
     * @param y top left y coordinate of the treasure
     */
    public Treasure(double x, double y) {
        super(x, y);
        setCurrentImage(TREASURE);
        setObjectBox(getCurrentImage().getBoundingBoxAt(computeCentrePoint()));
    }

    @Override
    public void update(Level level) {
        getCurrentImage().drawFromTopLeft(getX(), getY());
    }
}
