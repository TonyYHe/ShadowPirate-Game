/** entities that can attack and cause damage to other entities should implement this interface
 */
public interface Attackable {
    /** method print details of a how the calling entity damaged the input character
     * @param character the character that received damage from the calling entity
     */
    void printDamageLog(Character character);
}
