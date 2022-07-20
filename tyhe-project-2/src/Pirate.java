import bagel.*;
import bagel.util.*;
import java.util.*;

/** represents a normal pirate in the game, which moves in one of the four basic orientations, and can shoot
 * projectiles
 */
public class Pirate extends Character {

    private final Image READY_LEFT = new Image("res/pirate/pirateLeft.png");
    private final Image READY_RIGHT = new Image("res/pirate/pirateRight.png");
    private final Image INVINCIBLE_LEFT = new Image("res/pirate/pirateHitLeft.png");
    private final Image INVINCIBLE_RIGHT = new Image("res/pirate/pirateHitRight.png");

    protected static final int ATTACK_COOLDOWN = 3000;
    private static final int INVINCIBLE_COOLDOWN = 1500;
    protected static final int MAX_HEALTH_POINTS = 45;
    private static final int HEALTH_OFFSET = -6;
    private static final int FONT_SIZE = 15;
    private final Font FONT = new Font("res/wheaton.otf", FONT_SIZE);
    protected static final int ATTACK_RANGE = 100;
    private static final double MIN_SPEED = 0.2;
    private static final double MAX_SPEED = 0.7;
    protected static final int DAMAGE_POINTS = 10;
    protected static final int LEFT = -1;

    private CountDown attackCoolDown;
    private CountDown invincible;
    private int attackRange;
    private int xDirection;
    private int yDirection;
    private int oldHealthPoints;
    private Rectangle hitBox;

    /** creates a new pirate
     * @param x top left x coordinate
     * @param y top left y coordinate
     */
    public Pirate(double x, double y) {
        super(x, y, Math.random()*(MAX_SPEED-MIN_SPEED)+MIN_SPEED, DAMAGE_POINTS, MAX_HEALTH_POINTS);
        attackRange = ATTACK_RANGE;
        setCoolDown(ATTACK_COOLDOWN);
        invincible = new CountDown(INVINCIBLE_COOLDOWN);
        oldHealthPoints = getHealthPoints();
        randomDirection();
        setCurrentImage();
        Point centrePoint = computeCentrePoint();
        Point hitBoxTopLeft = new Point(centrePoint.x-attackRange/2, centrePoint.y-attackRange/2);
        setObjectBox(getCurrentImage().getBoundingBoxAt(centrePoint));
        hitBox = new Rectangle(hitBoxTopLeft, attackRange, attackRange);
        attackCoolDown = new CountDown(getCoolDown());
        setName(getClass().getSimpleName());
    }

    private void randomDirection() {
        Random random = new Random();
        do {
            // method randomly chooses one of the four directions
            // left(-1, 0), right(1, 0), up(0, -1) and down(0, 1)
            xDirection = random.nextInt(3)-1;
            yDirection = random.nextInt(3)-1;
        } while (Math.abs(xDirection)==Math.abs(yDirection));
    }

    // method sets the appropriate image based on current state and orientation
    private void setCurrentImage() {
        if (!invincible.isState()) {
            if (xDirection == LEFT) {
                setCurrentImage(READY_LEFT);
            } else {
                setCurrentImage(READY_RIGHT);
            }
        } else {
            if (xDirection == LEFT) {
                setCurrentImage(INVINCIBLE_LEFT);
            } else {
                setCurrentImage(INVINCIBLE_RIGHT);
            }
        }
    }

    @Override
    protected void render() {
        setCurrentImage();
        getCurrentImage().drawFromTopLeft(getX(), getY());
    }

    @Override
    protected void displayHealth() {
        double percentageHP = ((double) getHealthPoints()/getMaxHealthPoints()) * 100;
        DrawOptions colour = new DrawOptions();
        if (percentageHP <= RED_BOUNDARY){
            colour.setBlendColour(RED);
        } else if (percentageHP <= ORANGE_BOUNDARY){
            colour.setBlendColour(ORANGE);
        } else {
            colour.setBlendColour(GREEN);
        }
        FONT.drawString(Math.round(percentageHP) + "%", getX(), getY()+HEALTH_OFFSET, colour);
    }

    @Override
    public void update(Level level) {
        // record original position
        setOldX(getX());
        setOldY(getY());
        // record original health points
        setOldHealthPoints(getHealthPoints());
        // move logic
        move(xDirection*getSpeed(), yDirection*getSpeed());
        // update location of the object box
        getObjectBox().moveTo(new Point(getX(), getY()));
        //update location of the hitbox
        Point centrePoint = computeCentrePoint();
        setObjectBox(getCurrentImage().getBoundingBoxAt(centrePoint));
        Point hitBoxTopLeft = new Point(centrePoint.x-attackRange/2, centrePoint.y-attackRange/2);
        hitBox = new Rectangle(hitBoxTopLeft, attackRange, attackRange);
        // damage receiving logic
        receiveDamage(level.getSailor());
        becomeInvincible();
        // collision logic
        reverseDirection(level);
        // attack logic
        fireProjectile(level);
        // display logic
        displayHealth();
        render();
    }

    /** method reverses the movement direction of the pirate after colliding with the level boundary or an obstacle
     * @param level current level the pirate is in
     */
    protected void reverseDirection(Level level) {
        boolean changeDirection = false;
        if (level instanceof Level0) {
            changeDirection = checkCollision(((Level0)level).getBlocks());
        } else if (level instanceof Level1) {
            changeDirection = checkCollision(((Level1)level).getBombs());
        }
        if (outOfBound(level) || changeDirection) {
            xDirection *= -1;
            yDirection *= -1;
        }
    }

    /** method allows pirate to become immune from damage caused by sailor, if appropriate
     */
    protected void becomeInvincible() {
        if (oldHealthPoints!=getHealthPoints() && !invincible.isState()) {
            invincible.startCountDown();
        } else if (invincible.isState()) {
            invincible.countDown();
        }
    }

    /** reduces the health points of the pirate if necessary
     * @param sailor sailor that attacked the pirate
     */
    protected void receiveDamage(Sailor sailor) {
        if (sailor.getAttackState().isState() && checkCollision(sailor) && !invincible.isState()) {
            setHealthPoints(getHealthPoints() - sailor.getDamagePoints());
            sailor.printDamageLog(this);
        }
    }

    /** method allows pirate to fire a projectile at the sailor if appropriate
     * @param level current level
     */
    protected void fireProjectile(Level level) {
        if (!attackCoolDown.isState() && hitBox.intersects(level.getSailor().getObjectBox())) {
            level.getProjectiles().add(createProjectile(level.getSailor()));
            attackCoolDown.startCountDown();
        } else if (attackCoolDown.isState()) {
            attackCoolDown.countDown();
        }
    }

    /** creates a new projectile
     * @param sailor target of the projectile
     * @return a projectile object
     */
    protected Projectile createProjectile(Sailor sailor) {
        return new Projectile(hitBox.centre().x, hitBox.centre().y,
                sailor.getObjectBox().centre().x, sailor.getObjectBox().centre().y, this);
    }

    /** retrieves the count-down object responsible for calculating invincible state cool down
     * @return a count-down object
     */
    public CountDown getInvincible() {
        return invincible;
    }

    /** sets a new value for the old health points, used for checking if the pirate has received damage recently
     * @param oldHealthPoints updated old health points
     */
    public void setOldHealthPoints(int oldHealthPoints) {
        this.oldHealthPoints = oldHealthPoints;
    }

    /** sets a new attack range for the pirate
     * @param attackRange new attack range
     */
    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    /** retrieves the horizontal direction of the sailor:
     * 0 indicates pirate is not moving horizontally
     * 1 indicates pirate is moving to the right
     * -1 indicates pirate is moving to the left
     * @return int
     */
    public int getxDirection() {
        return xDirection;
    }

    /** sets a new cool down value for pirates attack state
     * @param attackCoolDown new cool down for pirate's attacks
     */
    public void setAttackCoolDown(CountDown attackCoolDown) {
        this.attackCoolDown = attackCoolDown;
    }

    /** retrieves the rectangle that represents the attack range of the pirate
     * @return Rectangle
     */
    public Rectangle getHitBox() {
        return hitBox;
    }
}
