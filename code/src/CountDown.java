/** a class for calculating cool downs (e.g. attack cool down, invincibility cool down, etc.)
 */
public class CountDown {
    private static double SECONDS_TO_MS = 1000;
    private static int FRAMES_PER_SECOND = 60;

    private boolean state;
    private int counter;
    private final int coolDown;

    /** creates a new count-down object, with default cool down of 0.
     */
    public CountDown() {
        state = false;
        this.coolDown = counter = 0;
    }

    /** creates a new count-down object, with specified cool-down time, which can be used to track cool downs of
     * entities with such mechanism. During cool down, state is set to true, and otherwise state is set to false.
     * @param coolDown how long the count-down should be
     */
    public CountDown(int coolDown) {
        state = false;
        counter = 0;
        this.coolDown = coolDown;
    }

    /**
     * method starts the count-down, and state is set to true to indicate that object is currently in cool down
     */
    public void startCountDown() {
        state = true;
    }

    /**
     * method increments the counter, so the cool down is one step closer to finishing
     */
    public void countDown() {
        if (counter < coolDown/SECONDS_TO_MS*FRAMES_PER_SECOND) {
            counter++;
        } else {
            state = false;
            counter = 0;
        }
    }

    /** method checks if the object is still in cool down
     * @return a boolean value
     */
    public boolean isState() {
        return state;
    }

}
