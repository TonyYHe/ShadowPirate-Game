import bagel.*;

/** represents a sword, which increases the sailor's damage points if picked up by the sailor
 */
public class Sword extends Item {
    private final Image SWORD = new Image("res/items/sword.png");
    private final Image SWORDICON = new Image("res/items/swordIcon.png");
    private int DAMAGE_BOOST = 15;

    /** creates new sword
     * @param x top left x coordinate of the sword
     * @param y top left y coordinate of the sword
     */
    public Sword(double x, double y) {
        super(x, y);
        setCurrentImage(SWORD);
        setObjectBox(getCurrentImage().getBoundingBoxAt(computeCentrePoint()));
    }

    @Override
    public void update(Level level) {
        super.update(level);
        if (isPickedUp()) {
            setCurrentImage(SWORDICON);
        }
    }

    @Override
    public void takeEffect(Sailor sailor) {
        boostDamage(sailor);
        printEffectLog(sailor);
    }

    // method increases sailor's damage points by a certain amount
    private void boostDamage(Sailor sailor) {
        sailor.setDamagePoints(sailor.getDamagePoints() + DAMAGE_BOOST);
    }

    @Override
    public void printEffectLog(Sailor sailor) {
        System.out.println("Sailor finds Sword. Sailor's damage points increased to " + sailor.getDamagePoints());
    }
}
