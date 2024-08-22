import bagel.*;
import bagel.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * represents a level in the game ShadowPirate, which consist of the sailor that can be controlled by the player, and
 * enemies that can attack the sailor
 */
public abstract class Level {
    private static final String START_MESSAGE = "PRESS SPACE TO START";
    private static final String ATTACK_MESSAGE = "PRESS S TO ATTACK";
    private static final String END_MESSAGE = "GAME OVER";

    protected static final int LINE_OFFSET = 50;
    protected static final int FONT_SIZE = 55;
    protected static final int FONT_Y_POS = 402;
    protected final Font FONT = new Font("res/wheaton.otf", FONT_SIZE);

    private Point topLeftBoundary;
    private Point bottomRightBoundary;

    private boolean levelStarted;
    private boolean levelEnded;
    private boolean moveToNextLevel;
    private boolean gameEnded;
    private CountDown levelTransition;

    private ArrayList<Pirate> pirates = new ArrayList<>();
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private Sailor sailor;

    /** creates a new level
     */
    public Level() {
        levelStarted = levelEnded = moveToNextLevel = gameEnded = false;
    }

    /** dissect the components in this line and record the coordinates of the object
     * @param sections a single line of csv entries
     */
    public abstract void readComponents(String[] sections);


    // method used to draw the start screen instructions
    private void drawStartScreen() {
        FONT.drawString(START_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(START_MESSAGE)/2.0)),
                FONT_Y_POS);
        FONT.drawString(ATTACK_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(ATTACK_MESSAGE)/2.0)),
                (FONT_Y_POS + LINE_OFFSET));
        drawObjectiveMessage();
    }
    /** method used to draw the objective message
     */
    protected abstract void drawObjectiveMessage();

    /** method used to draw level background
     */
    protected abstract void drawBackground();

    /** method updates the state of the level
     */
    protected abstract void update();

    /** another update method that can detect input from keyboard
     * @param input input from keyboard
     */
    public void update(Input input) {
        if (input.wasPressed(Keys.SPACE)) {
            levelStarted = true;
        }
        if (!levelStarted) {
            drawStartScreen();
        } else if (!levelEnded && !gameEnded) {
            update();
            for (int i=0; i<pirates.size(); i++) {
                if (!pirates.get(i).isDead()) {
                    pirates.get(i).update(this);
                }
            }
            for (int i=0; i<projectiles.size(); i++) {
                projectiles.get(i).update(this);
            }
            sailor.update(input, this);
            if (sailor.hasWon(this)) {
                levelEnded = true;
                levelTransition.startCountDown();
            }
            if (sailor.isDead()) {
                gameEnded = true;
            }
        }
        if (levelEnded && levelTransition.isState()) {
            playLevelTransition();
        } else if (levelEnded) {
            moveToNextLevel = true;
        } else if (gameEnded) {
            drawEndScreen();
        }
    }

    /**
     * display the transition to the next level
     */
    protected abstract void playLevelTransition();

    /** method used to read csv file and create objects
     */
    protected void readCSV(String fileName){
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = br.readLine()) != null){
                String[] sections = line.split(",");
                switch (sections[0]) {
                    case "Sailor":
                        sailor = new Sailor(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                    case "Pirate":
                        pirates.add(new Pirate(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                        break;
                    case "TopLeft":
                        topLeftBoundary = new Point(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                    case "BottomRight":
                        bottomRightBoundary = new Point(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                }
                readComponents(sections);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** method used to draw end screen messages
     */
    protected void drawEndScreen() {
        if (sailor.isDead()) {
            FONT.drawString(END_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(END_MESSAGE)/2.0)), FONT_Y_POS);
        } else {
            drawWinScreen();
        }
    }

    /** draws the win messages to the screen
     */
    protected abstract void drawWinScreen();

    /** retrieves the top left boundary of the level
     * @return Point object
     */
    public Point getTopLeftBoundary() {
        return topLeftBoundary;
    }

    /** retrieves the bottom right boundary of the level
     * @return Point object
     */
    public Point getBottomRightBoundary() {
        return bottomRightBoundary;
    }

    /** retrieves the list of pirates in the current level
     * @return ArrayList<Pirate>
     */
    public ArrayList<Pirate> getPirates() {
        return pirates;
    }

    /** method checks if it's necessary to move onto the next level
     * @return boolean
     */
    public boolean moveToNextLevel() {
        return moveToNextLevel;
    }

    /** retrieves the sailor of the current level
     * @return Sailor object
     */
    public Sailor getSailor() {
        return sailor;
    }

    /** sets a count down object, which enables level transition to last for a specified time
     * @param levelTransition a counter down object
     */
    public void setLevelTransition(CountDown levelTransition) {
        this.levelTransition = levelTransition;
    }

    /**
     * @return retrieves the count down object responsible for controlling level transition
     */
    public CountDown getLevelTransition() {
        return levelTransition;
    }

    /** retrieves the list of projectiles currently active in the level
     * @return ArrayList<Projectile>
     */
    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }
}
