import bagel.Image;

/** represents Blackbeard, who has increased battle statistics compared to a normal pirate
 * more specifically, Blackbeard has improved damage points, health points, movement speed and reduced attack cool
 * down period
 */
public class Blackbeard extends Pirate {
    private final Image BLACKBEARD_LEFT = new Image("res/blackbeard/blackbeardLeft.png");
    private final Image BLACKBEARD_RIGHT = new Image("res/blackbeard/blackbeardRight.png");
    private final Image BB_INVINCIBLE_LEFT = new Image("res/blackbeard/blackbeardHitLeft.png");
    private final Image BB_INVINCIBLE_RIGHT = new Image("res/blackbeard/blackbeardHitRight.png");

    /** creates a Blackbeard object
     * @param x top left x coordinate of Blackbeard
     * @param y top left y coordinate of Blackbeard
     */
    public Blackbeard(double x, double y) {
        super(x, y);
        setAttackRange(ATTACK_RANGE*2);
        setDamagePoints(DAMAGE_POINTS*2);
        setHealthPoints(MAX_HEALTH_POINTS*2);
        setMaxHealthPoints(MAX_HEALTH_POINTS*2);
        setAttackCoolDown(new CountDown(ATTACK_COOLDOWN/2));
        setName(getClass().getSimpleName());
    }

    @Override
    protected void render() {
        if (!getInvincible().isState()) {
            if (getxDirection() == LEFT) {
                setCurrentImage(BLACKBEARD_LEFT);
            } else {
                setCurrentImage(BLACKBEARD_RIGHT);
            }
        } else {
            if (getxDirection() == LEFT) {
                setCurrentImage(BB_INVINCIBLE_LEFT);
            } else {
                setCurrentImage(BB_INVINCIBLE_RIGHT);
            }
        }
        getCurrentImage().drawFromTopLeft(getX(), getY());
    }

    /** method creates a Blackbeard type of projectile
     * @param sailor a sailor object which is the target of the projectile
     * @return a projectile object which is specific to Blackbeard
     */
    @Override
    protected Projectile createProjectile(Sailor sailor) {
        return new BBProjectile(getHitBox().centre().x, getHitBox().centre().y,
                sailor.getObjectBox().centre().x, sailor.getObjectBox().centre().y, this);
    }
}
