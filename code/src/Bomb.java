import bagel.*;

/** represents a bomb, which cannot be passed through by characters, and explodes upon contact with sailor
 */
public class Bomb extends GameObject implements Attackable {
    private final Image BOMB = new Image("res/bomb.png");
    private final Image EXPLOSION = new Image("res/explosion.png");
    protected static final int DAMAGE_POINTS = 10;
    private static final int EXPLOSION_DURATION = 500;
    private CountDown exploding;
    private boolean exploded;

    /** creates a Bomb object
     * @param x top left x coordinate of the bomb
     * @param y top left y coordinate of Blackbeard
     */
    public Bomb(double x, double y) {
        super(x, y);
        setCurrentImage(BOMB);
        setObjectBox(getCurrentImage().getBoundingBoxAt(computeCentrePoint()));
        exploding = new CountDown(EXPLOSION_DURATION);
        exploded = false;
    }

    @Override
    public void update(Level level) {
        getCurrentImage().drawFromTopLeft(getX(), getY());
        // collision with sailor, bomb is set off
        if (!exploding.isState() && level.getSailor().receiveDamage(this)) {
            setCurrentImage(EXPLOSION);
            exploding.startCountDown();
        }
        // bomb is exploding
        if (exploding.isState()) {
            exploding.countDown();
            if (!exploding.isState()) {
                exploded = true;
            }
        }
    }

    @Override
    public void printDamageLog(Character character) {
        System.out.println("Bomb inflicts " + DAMAGE_POINTS + " damage points on " + character.getName() + ". " +
                character.getName() + "'s current health: " + character.getHealthPoints() + "/" +
                character.getMaxHealthPoints());
    }

    /** a getter method indicating whether the bomb has exploded or not
     * @return a boolean value
     */
    public boolean isExploded() {
        return exploded;
    }
}
