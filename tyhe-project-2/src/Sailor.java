import bagel.Keys;
import bagel.*;
import bagel.util.*;

import java.util.ArrayList;

/** represents the sailor, which can be controlled by the player
 */
public class Sailor extends Character implements Attackable {
    private Keys ATTACK_KEY = Keys.S;
    private static final int ATTACK_DURATION = 1000;
    private static final int ATTACK_COOLDOWN = 2000;

    private CountDown attackState;
    private CountDown attackCoolDown;
    private boolean faceRight;

    private static final int INITIAL_HEALTH_POINTS = 100;
    private static final int HEALTH_X = 10;
    private static final int HEALTH_Y = 25;
    private static final int FONT_SIZE = 30;

    private final Font FONT = new Font("res/wheaton.otf", FONT_SIZE);
    private Image SAILOR_LEFT = new Image("res/sailor/sailorLeft.png");
    private Image SAILOR_RIGHT = new Image("res/sailor/sailorRight.png");
    private Image SAILOR_HIT_LEFT= new Image("res/sailor/sailorHitLeft.png");
    private Image SAILOR_HIT_RIGHT = new Image("res/sailor/sailorHitRight.png");

    private static final int MOVEMENT_SPEED = 1;
    private static final int INITIAL_DAMAGE_POINTS = 15;

    private ArrayList<Item> inventory = new ArrayList<>();
    private static final int ITEM_X_OFFSET = 15;
    private static final int ITEM_Y_OFFSET = 40;

    /** creates a new sailor
     * @param x top left x coordinate of the sailor
     * @param y top left y coordinate of the sailor
     */
    public Sailor(double x, double y) {
        super(x, y, MOVEMENT_SPEED, INITIAL_DAMAGE_POINTS, INITIAL_HEALTH_POINTS);
        faceRight = true;
        attackState = new CountDown(ATTACK_DURATION);
        attackCoolDown = new CountDown(ATTACK_COOLDOWN);
        setCurrentImage(SAILOR_RIGHT);
        setObjectBox(SAILOR_RIGHT.getBoundingBoxAt(computeCentrePoint()));
        setName(getClass().getSimpleName());
    }

    @Override
    public void printDamageLog(Character character) {
        System.out.println("Sailor inflicts " + getDamagePoints() + " damage points on " + character.getName() + ". " +
                character.getName() + "'s" + " current health: " + character.getHealthPoints() + "/" +
                character.getMaxHealthPoints());
    }

    private void enterAttackState(Input input) {
        // need to enter attack state
        if (!attackCoolDown.isState() && input.wasPressed(ATTACK_KEY)) {
            attackState.startCountDown();
        }
        if (attackState.isState()) {
            attackState.countDown();
            if (!attackState.isState()) {   // attack state is over, enter cool down
                attackCoolDown.startCountDown();
            }
        }
        if (attackCoolDown.isState()) {
            attackCoolDown.countDown();
        }
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
        FONT.drawString(Math.round(percentageHP) + "%", HEALTH_X, HEALTH_Y, colour);
    }

    // method moves the sailor back if the sailor is out of bound, or collides with a block or bomb
    private void setOldPoint(Level level) {
        boolean changeDirection = false;
        if (level instanceof Level0) {
            changeDirection = checkCollision(((Level0)level).getBlocks());
        } else if (level instanceof Level1) {
            changeDirection = checkCollision(((Level1)level).getBombs());
        }
        if (outOfBound(level) || changeDirection) {
            setX(getOldX());
            setY(getOldY());
        }
    }

    @Override
    public void update(Level level) {
        // render sailor image to screen
        render();
        // render sailor inventory to screen
        renderInventory();
        // update location of the object box
        getObjectBox().moveTo(new Point(getX(), getY()));
        // move back if necessary
        setOldPoint(level);
        // health display logic
        displayHealth();
    }

    /** another method to update state of sailor, with the added ability to process input from keyboard
     * @param input input from keyboard
     * @param level current level
     */
    public void update(Input input, Level level) {
        // record original position
        setOldX(getX());
        setOldY(getY());
        // attack logic
        enterAttackState(input);
        // move logic
        if (input.isDown(Keys.UP)){
            move(0, -getSpeed());
        } else if (input.isDown(Keys.DOWN)) {
            move(0, getSpeed());
        } else if (input.isDown(Keys.LEFT)) {
            faceRight = false;
            move(-getSpeed(),0);
        } else if (input.isDown(Keys.RIGHT)) {
            faceRight = true;
            move(getSpeed(),0);
        }
        update(level);
    }

    @Override
    protected void render() {
        if (attackState.isState()) {    // if sailor is in attack state
            if (faceRight) {
                setCurrentImage(SAILOR_HIT_RIGHT);
            } else {
                setCurrentImage(SAILOR_HIT_LEFT);
            }
        } else if (faceRight) { // if sailor is not in attack state
            setCurrentImage(SAILOR_RIGHT);
        } else {
            setCurrentImage(SAILOR_LEFT);
        }
        getCurrentImage().drawFromTopLeft(getX(), getY());
    }


    /** method that checks if sailor has reached the ladder or the treasure
     * @param level current level
     * @return a boolean value
     */
    public boolean hasWon(Level level) {
        if (level instanceof Level0) {
            return (getX() >= ((Level0)level).WIN_X) && (getY() > ((Level0)level).WIN_Y);
        }
        if (level instanceof Level1) {
            return checkCollision(((Level1)level).getTreasure());
        }
        return false;
    }

    /** method that reduces health points of sailor according to the attacker
     * @param attacker entity that attacked the sailor
     * @return a boolean value indicating whether the sailor received damage in any way
     */
    public boolean receiveDamage(Attackable attacker) {
        if (attacker instanceof Projectile) {
            Projectile projectile = (Projectile) attacker;
            if (getObjectBox().intersects(new Point(projectile.getX(), projectile.getY()))) {
                setHealthPoints(getHealthPoints() - projectile.getOwner().getDamagePoints());
                projectile.printDamageLog(this);
                return true;
            }
        }
        if (attacker instanceof Bomb) {
            Bomb bomb = (Bomb) attacker;
            if (checkCollision(bomb)) {
                setHealthPoints(getHealthPoints() - bomb.DAMAGE_POINTS);
                bomb.printDamageLog(this);
                return true;
            }
        }
        return false;
    }

    // method displays the inventory to the screen
    private void renderInventory() {
        double yPos = HEALTH_Y;
        for (Item item: inventory) {
            yPos += ITEM_Y_OFFSET;
            item.getCurrentImage().draw(HEALTH_X + ITEM_X_OFFSET, yPos);
        }
    }

    /** method retrieves the object responsible for calculating the attack cool down of the sailor
     * @return CountDown object
     */
    public CountDown getAttackState() {
        return attackState;
    }

    /** method retrieves the list of items in the sailor's inventory
     * @return ArrayList<Item>
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }
}
