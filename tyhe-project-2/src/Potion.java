import bagel.*;

/** represents a potion, which recovers the sailor's health if picked up by the sailor
 */
public class Potion extends Item {
    private final Image POTION = new Image("res/items/potion.png");
    private final Image POTIONICON = new Image("res/items/potionIcon.png");
    private int HEALTH_RECOVER = 25;

    /** creates a new potion
     * @param x top left x coordinate of the potion
     * @param y top left y coordinate of the potion
     */
    public Potion(double x, double y) {
        super(x, y);
        setCurrentImage(POTION);
        setObjectBox(getCurrentImage().getBoundingBoxAt(computeCentrePoint()));
    }

    @Override
    public void update(Level level) {
        super.update(level);
        if (isPickedUp()) {
            setCurrentImage(POTIONICON);
        }
    }

    @Override
    public void takeEffect(Sailor sailor) {
        recoverHP(sailor);
        printEffectLog(sailor);
    }

    // method recovers sailor's health by a certain amount
    private void recoverHP(Sailor sailor) {
        if (sailor.getHealthPoints() + HEALTH_RECOVER > sailor.getMaxHealthPoints()) {
            sailor.setHealthPoints(sailor.getMaxHealthPoints());
        } else {
            sailor.setHealthPoints(sailor.getHealthPoints() + HEALTH_RECOVER);
        }
    }

    @Override
    public void printEffectLog(Sailor sailor) {
        System.out.println("Sailor finds Potion. Sailor's current health: " + sailor.getHealthPoints() + "/" +
                sailor.getMaxHealthPoints());
    }
}
