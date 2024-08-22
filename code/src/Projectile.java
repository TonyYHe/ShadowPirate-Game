import bagel.*;

/** represents a projectile fired by a pirate
 */
public class Projectile extends MovingEntity implements Attackable {
    private Image NORMAL = new Image("res/pirate/pirateProjectile.png");
    protected static double NORMAL_SPEED = 0.4;
    private final double direction;
    private final DrawOptions rotation;
    private Pirate owner;

    /** creates a new projectile
     * @param pirateX the x coordinate of centre of the pirate
     * @param pirateY the y coordinate of centre of the pirate
     * @param sailorX the x coordinate of centre of the sailor (target)
     * @param sailorY the y coordinate of centre of the sailor (target)
     * @param owner the pirate that fired this projectile
     */
    public Projectile(double pirateX, double pirateY, double sailorX, double sailorY, Pirate owner) {
        super(pirateX, pirateY, NORMAL_SPEED);
        this.owner = owner;
        setCurrentImage(NORMAL);
        direction = computeDirection(pirateX, pirateY, sailorX, sailorY);
        rotation = new DrawOptions().setRotation(direction);
    }

    @Override
    public void update(Level level) {
        if (outOfBound(level) || level.getSailor().receiveDamage(this)) {
            level.getProjectiles().remove(this);
        } else {
            move();
            getCurrentImage().draw(getX(), getY(), rotation);
        }
    }

    // method moves the projectile accordingly
    private void move() {
        double xMove = getSpeed()*Math.cos(direction);
        double yMove = getSpeed()*Math.sin(direction);
        move(xMove, yMove);
    }

    // method returns the direction that the projectile should travel
    private double computeDirection(double pirateX, double pirateY, double sailorX, double sailorY) {
        double xChange = sailorX-pirateX;
        double yChange = sailorY-pirateY;
        return Math.atan2(yChange, xChange);
    }
    /** retrieves the pirate that fired this projectile
     * @return Pirate object
     */
    public Pirate getOwner() {
        return owner;
    }

    @Override
    public void printDamageLog(Character character) {
        System.out.println(owner.getName() + " inflicts " + owner.getDamagePoints() + " damage points on " +
                character.getName() + ". " + character.getName() + "'s current health: " +
                character.getHealthPoints() + "/" + character.getMaxHealthPoints());
    }
}
