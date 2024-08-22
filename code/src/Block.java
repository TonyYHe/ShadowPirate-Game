import bagel.*;

/** represents a block, which cannot be passed through by characters
 */
public class Block extends GameObject {
    private final Image BLOCK_IMAGE = new Image("res/block.png");

    /** creates a new block object
     * @param x top left x coordinate of the block
     * @param y top left y coordinate of the block
     */
    public Block(int x, int y) {
        super(x, y);
        setCurrentImage(BLOCK_IMAGE);
        setObjectBox(BLOCK_IMAGE.getBoundingBoxAt(computeCentrePoint()));
    }

    @Override
    public void update(Level level) {
        getCurrentImage().drawFromTopLeft(getX(), getY());
    }
}
