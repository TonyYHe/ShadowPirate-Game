import bagel.*;
import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2022
 *
 * Please fill your name below
 * @author Tony Yong He
 */
public class ShadowPirate extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "ShadowPirate";

    private ArrayList<Level> levels = new ArrayList<>();
    private int currentLevel;

    /** creates new Shadow Pirate game with level 0 and level 1 implemented
     */
    public ShadowPirate() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        levels.add(new Level0());
        levels.add(new Level1());
        currentLevel = 0;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPirate game = new ShadowPirate();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        if (levels.get(currentLevel).moveToNextLevel() && currentLevel < levels.size() - 1) {
            currentLevel++;
        } else {
            levels.get(currentLevel).update(input);
        }
    }
}
