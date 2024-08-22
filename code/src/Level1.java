import bagel.Image;
import bagel.Window;
import java.util.ArrayList;

/** represents level 1 of the game ShadowPirate, which has bombs as obstacles and Blackbeard as boss
 */
public class Level1 extends Level {
    private static final String OBJECTIVE_MESSAGE = "FIND THE TREASURE";
    private static final String WIN_MESSAGE = "CONGRATULATIONS!";
    private final Image LEVEL1_BACKGROUND = new Image("res/background1.png");
    private static final String WORLD_FILE1 = "res/level1.csv";

    private ArrayList<Bomb> bombs = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private Treasure treasure;

    /** creates level 1
     */
    public Level1() {
        readCSV(WORLD_FILE1);
        setLevelTransition(new CountDown());
    }

    @Override
    public void readComponents(String[] sections) {
        switch (sections[0]) {
            case "Blackbeard":
                getPirates().add(new Blackbeard(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                break;
            case "Block":
                bombs.add(new Bomb(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                break;
            case "Treasure":
                treasure = new Treasure(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                break;
            case "Potion":
                items.add(new Potion(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                break;
            case "Elixir":
                items.add(new Elixir(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                break;
            case "Sword":
                items.add(new Sword(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
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
        LEVEL1_BACKGROUND.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
    }

    @Override
    protected void update() {
        drawBackground();
        for (int i=0; i<bombs.size(); i++) {
            bombs.get(i).update(this);
            if (bombs.get(i).isExploded()) {
                bombs.remove(bombs.get(i));
            }
        }
        for (int i=0; i<items.size(); i++) {
            items.get(i).update(this);
            if (items.get(i).isPickedUp()) {
                getSailor().getInventory().add(items.get(i));
                items.remove(items.get(i));
            }
        }
        treasure.update(this);
    }

    @Override
    protected void playLevelTransition() {
        drawEndScreen();
    }

    @Override
    protected void drawWinScreen() {
        FONT.drawString(WIN_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(WIN_MESSAGE)/2.0)), FONT_Y_POS);
    }

    /** retrieves the treasure object
     * @return Treasure
     */
    public Treasure getTreasure() {
        return treasure;
    }

    /** retrieves the list of bombs in level 1
     * @return ArrayList<Bomb>
     */
    public ArrayList<Bomb> getBombs() {
        return bombs;
    }
}
