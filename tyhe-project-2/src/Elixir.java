import bagel.*;

/** represents elixir object, which can recover and also permanently increase health of the sailor
 */
public class Elixir extends Item {
    private final Image ELIXIR = new Image("res/items/elixir.png");
    private final Image ELIXIRICON = new Image("res/items/elixirIcon.png");
    private int HEALTH_BOOST = 35;

    /** creates an elixir object
     * @param x top left x coordinate of the elixir object
     * @param y top left y coordinate of the elixir object
     */
    public Elixir(double x, double y) {
        super(x, y);
        setCurrentImage(ELIXIR);
        setObjectBox(getCurrentImage().getBoundingBoxAt(computeCentrePoint()));
    }

    @Override
    public void update(Level level) {
        super.update(level);
        if (isPickedUp()) {
            setCurrentImage(ELIXIRICON);
        }
    }

    @Override
    public void takeEffect(Sailor sailor) {
        boostHP(sailor);
        printEffectLog(sailor);
    }

    // permanently increases the maximum health points of the sailor, increase the current health points to the
    // new maximum value
    private void boostHP(Sailor sailor) {
        sailor.setMaxHealthPoints(sailor.getMaxHealthPoints()+HEALTH_BOOST);
        sailor.setHealthPoints(sailor.getMaxHealthPoints());
    }

    @Override
    public void printEffectLog(Sailor sailor) {
        System.out.println("Sailor finds Elixir. Sailor's current health: " + sailor.getHealthPoints() + "/" +
                sailor.getMaxHealthPoints());
    }
}
