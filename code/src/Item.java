/** represents an item that can be picked up and consumed by the sailor
 */
public abstract class Item extends GameObject {
    private boolean pickedUp;

    /** creates a new item
     * @param x top left x coordinate of the item
     * @param y top left y coordinate of the item
     */
    public Item(double x, double y) {
        super(x, y);
        pickedUp = false;
    }

    @Override
    public void update(Level level) {
        if (!isPickedUp()) {
            getCurrentImage().drawFromTopLeft(getX(), getY());
            if (checkCollision(level.getSailor())) {
                pickedUp = true;
                takeEffect(level.getSailor());
            }
        }
    }

    /** Method applies the effect of the item on the sailor
     * @param sailor sailor object that picked up the item
     */
    public abstract void takeEffect(Sailor sailor);

    /** Method prints to the command line of details relating to the interaction between the sailor and the item
     * @param sailor Sailor object that consumed the item
     */
    public abstract void printEffectLog(Sailor sailor);

    /** checks if the item has been picked up by the sailor or not
     * @return a boolean value
     */
    public boolean isPickedUp() {
        return pickedUp;
    }
}
