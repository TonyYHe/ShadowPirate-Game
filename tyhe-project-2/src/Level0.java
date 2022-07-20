import bagel.*;

import java.util.ArrayList;

/** represents level 0 of the game ShadowPirate, which has blocks as obstacles
 */
public class Level0 extends Level {
    private static final String OBJECTIVE_MESSAGE = "USE ARROW KEYS TO FIND LADDER";
    private static final String WIN_MESSAGE = "LEVEL COMPLETE!";
    private final Image LEVEL0_BACKGROUND = new Image("res/background0.png");
    private static final String WORLD_FILE0 = "res/level0.csv";
    protected static final int WIN_X = 990;
    protected static final int WIN_Y = 630;
    private static final int WIN_DISPLAY_TIME = 3000;

    private ArrayList<Block> blocks = new ArrayList<>();

    /** creates level 0
     */
    public Level0() {
        readCSV(WORLD_FILE0);
        setLevelTransition(new CountDown(WIN_DISPLAY_TIME));
    }

    @Override
    public void readComponents(String[] sections) {
        switch (sections[0]) {
            case "Block":
                blocks.add(new Block(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                break;
        }
    }

    @Override
    protected void drawObjectiveMessage() {
        FONT.drawString(OBJECTIVE_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(OBJECTIVE_MESSAGE)/2.0)),
                (FONT_Y_POS + LINE_OFFSET*2));
    }

    @Override
    protected void drawBackground() {
        LEVEL0_BACKGROUND.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
    }

    @Override
    protected void drawWinScreen() {
        FONT.drawString(WIN_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(WIN_MESSAGE)/2.0)), FONT_Y_POS);
    }

    @Override
    public void update() {
        drawBackground();
        for (int i=0; i<blocks.size(); i++) {
            blocks.get(i).update(this);
        }
    }

    @Override
    protected void playLevelTransition() {
        getLevelTransition().countDown();
        drawEndScreen();
    }

    /** retrieves the list of blocks in level 0
     * @return ArrayList<Block>
     */
    public ArrayList<Block> getBlocks() {
        return blocks;
    }
}

