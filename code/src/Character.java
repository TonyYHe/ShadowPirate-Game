import bagel.*;
import bagel.util.*;
import java.util.ArrayList;

/** represents a character, who can move, attack and take damage
 */
public abstract class Character extends MovingEntity {
    private int healthPoints;
    private int damagePoints;
    private int maxHealthPoints;
    private Image currentImage;
    private double oldX;
    private double oldY;
    private int coolDown;
    private String name;

    protected static final Colour GREEN = new Colour(0, 0.8, 0.2);
    protected static final Colour ORANGE = new Colour(0.9, 0.6, 0);
    protected static final Colour RED = new Colour(1, 0, 0);
    protected static final int ORANGE_BOUNDARY = 65;
    protected static final int RED_BOUNDARY = 35;

    /** displays the percentage of health points the character has
     */
    protected abstract void displayHealth();

    /** Method renders the appropriate image of the character according to its current state and direction
     */
    protected abstract void render();

    /** creates a new character
     * @param x top left x coordinate of the character
     * @param y top left y coordinate of the character
     * @param movementSpeed step size of character per frame
     * @param damagePoints damage the character could inflict on other characters
     * @param maxHealthPoints maximum health points
     */
    public Character(double x, double y, double movementSpeed, int damagePoints, int maxHealthPoints) {
        super(x, y, movementSpeed);
        setDamagePoints(damagePoints);
        setHealthPoints(maxHealthPoints);
        setMaxHealthPoints(maxHealthPoints);
        setOldX(x);
        setOldY(y);
    }

    /** Method checks if the character has collided with any of the game objects in the input arrayList
     * @param stationaryEntities an arrayList of game objects
     * @param <T> any class that is a subclass of GameObject
     * @return a boolean value
     */
    protected <T extends GameObject> boolean checkCollision(ArrayList<T> stationaryEntities) {
        for (int i=0; i<stationaryEntities.size(); i++) {
            T stationaryEntity = stationaryEntities.get(i);
            if (stationaryEntity!=null && getObjectBox().intersects(stationaryEntity.getObjectBox())) {
                return true;
            }
        }
        return false;
    }

    /** method that checks if character is dead
     * @return a boolean value
     */
    public boolean isDead() {
        return getHealthPoints() <= 0;
    }

    /** set a new value for the character's current health points
     * @param healthPoints the new health point for the character
     */
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    /** set a new value for the character's current damage point
     * @param damagePoints the new value for the character's damage point
     */
    public void setDamagePoints(int damagePoints) {
        this.damagePoints = damagePoints;
    }

    /** set a new value for the character's maximum health points
     * @param maxHealthPoints the new value for the character's maximum health points
     */
    public void setMaxHealthPoints(int maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    /** set a new image for the character's image to be rendered
     * @param currentImage new image for the character's current image to be rendered
     */
    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }

    /** set a new name for the character's type
     * @param name new name for the type of character (e.g. sailor, pirate etc)
     */
    public void setName(String name) {
        this.name = name;
    }

    /** retrieves the current health points of the character
     * @return int
     */
    public int getHealthPoints() {
        return healthPoints;
    }

    /** retrieves the damage points of the character
     * @return int
     */
    public int getDamagePoints() {
        return damagePoints;
    }

    /** retrieves the maximum health points the character can have
     * @return int
     */
    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    /** retrieves the current image of the character
     * @return Image object of Bagel class
     */
    public Image getCurrentImage() {
        return currentImage;
    }

    /** sets a new value to the previous x position of the character
     * @param oldX old x coordinate
     */
    public void setOldX(double oldX) {
        this.oldX = oldX;
    }

    /** sets a new value to the previous y position of the character
     * @param oldY old y coordinate
     */
    public void setOldY(double oldY) {
        this.oldY = oldY;
    }

    /** retrieves the old x position
     * @return double
     */
    public double getOldX() {
        return oldX;
    }

    /** retrieves the old y position
     * @return double
     */
    public double getOldY() {
        return oldY;
    }

    /** retrieves the length of the attack cool down of the character
     * @return int
     */
    public int getCoolDown() {
        return coolDown;
    }

    /** sets the length of the attack cool down to a new value
     * @param coolDown new length of cool down
     */
    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    /** retrieves the name of the character, e.g. sailor, pirate etc
     * @return String
     */
    public String getName() {
        return name;
    }
}