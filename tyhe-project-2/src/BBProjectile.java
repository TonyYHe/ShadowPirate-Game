import bagel.Image;

/** represents a projectile fired by Blackbeard, which travels at a faster speed
 */
public class BBProjectile extends Projectile {
    private Image BLACKBEARD = new Image("res/blackbeard/blackbeardProjectile.png");

    /** creates a projectile specific to Blackbeard
     * @param pirateX x coordinate of the centre point of the pirate
     * @param pirateY y coordinate of the centre point of the pirate
     * @param sailorX x coordinate of the centre point of the sailor
     * @param sailorY y coordinate of the centre point of the sailor
     * @param owner pirate (Blackbeard) that fired this projectile
     */
    public BBProjectile(double pirateX, double pirateY, double sailorX, double sailorY, Pirate owner) {
        super(pirateX, pirateY, sailorX, sailorY, owner);
        setCurrentImage(BLACKBEARD);
        setSpeed(NORMAL_SPEED*2);
    }
}
