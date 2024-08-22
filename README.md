# ShadowPirate-Game
A role-playing game that consists of two levels. The sailor can move continuously, and can perform attacks when the player presses a certain key.

## Game Overview
The game features two levels : Level 0 is on the ship and Level 1 is on the island.

### Level 0
To win the level, the sailor must reach the exit, located by the ladder (in the bottom right). If the sailor’s health reduces to 0 or less, the game ends.
There are pirates that move in certain directions and have health points associated with them. They can shoot projectiles at the sailor when the sailor comes close. If a projectile hits the sailor, he will lose health points, but the sailor can still fight back.

### Level 1
In Level 1, the sailor has escaped the ship and arrived on Blackbeard’s island. To win the level and the game, the sailor must get to the treasure. However, the sailor has to deal with bombs on the island which explode (causing damage) if the sailor collides with them. In addition to the bombs, the sailor will encounter items on the island that can help him. These items (potion, elixir and sword) will have different effects on the sailor when collided with and the item will disappear as they collide (i.e. the item gets picked up). And of course, Level 1 will feature even more pirates, including Blackbeard himself! The game will end if the sailor’s health reduces to 0 or less.

## The Player
- The sailor is controlled by the four arrow keys and can move continuously in one of four directions (left, right, up, down). 
- The sailor starts with 15 damage points and 100 health points.
- The sailor can be in either of two states which determines its behavior when colliding with other entities. These two states are IDLE and ATTACK.
- Pressing the ‘S’ key will place the sailor in the ATTACK state.

## Others
| Entity | HP | Movement Speed | Damage Points | Attack Range | Collision effect on Sailor |
| ------ | ------- | ------- | ------- | ------- | ------- |
| Pirate | 45 | Random | 10 | 100 | fires projectile |
| Blackbeard | 90 | Random | 20 | 200 | fires projectile |
| Sword | - | - | - | - | increases sailor damage points by 15 |
| Potion | - | - | - | - | increases sailor HP by 25 |
| Elixir | - | - | - | - | increases sailor max HP by 35 and fully restore HP |
| Block | - | - | - | - | prevents sailor from moving through it |
| Bomb | - | - | 10 | - | inflicts its full damage points on sailor, behaves like Block while exploding |
| Treasure | - | - | - | - | ends the game with a victory screen |

