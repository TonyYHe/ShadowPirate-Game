import bagel.Image;
import bagel.util.*;

/** represents a game object, which has a pair of coordinates, can be rendered to the screen and can collide with other
 * game objects
 */
public abstract class GameObject {
    private double x;
    private double y;
    private Rectangle objectBox;
    private Image currentImage;

    /** creates a game object
     * @param x top left x coordinate of the game object
     * @param y top left y coordinate of the game object
     */
    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** method computes the centre point of the current image
     * @return a point object (from the Bagel class)
     */
    public Point computeCentrePoint() {
        return new Point(x+getCurrentImage().getWidth()/2, y+getCurrentImage().getHeight()/2);
    }

    /** method updates the state of the game object
     * @param level level that is associated with the game object
     */
    public abstract void update(Level level);

    /** method checks if there's a collision with another game object
     * @param object another game object
     * @return boolean
     */
    protected boolean checkCollision (GameObject object) {
        if (object!=null) {
            return object.getObjectBox().intersects(getObjectBox());
        }
        return false;
    }

    /** retrieves the rectangle that represents the game object
     * @return Rectangle object
     */
    public Rectangle getObjectBox() {
        return objectBox;
    }

    /** sets a new rectangle to represent the calling game object
     * @param objectBox a new rectangle object for the game object
     */
    public void setObjectBox(Rectangle objectBox) {
        this.objectBox = objectBox;
    }

    /** sets a new x coordinate (top left) for the game object
     * @param x a new x coordinate for the game object
     */
    public void setX(double x) {
        this.x = x;
    }

    /** sets a new y coordinate (top left) for the game object
     * @param y a new y coordinate for the game object
     */
    public void setY(double y) {
        this.y = y;
    }

    /** retrieves the top left x coordinate of the game object
     * @return double
     */
    public double getX() {
        return x;
    }

    /** retrieves the top left y coordinate of the game object
     * @return double
     */
    public double getY() {
        return y;
    }

    /** retrieves the current image that represents the game object
     * @return Image object (from the bagel class)
     */
    public Image getCurrentImage() {
        return currentImage;
    }

    /** sets a new image to represent the game object
     * @param currentImage a new image to represent the game object
     */
    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }
}
