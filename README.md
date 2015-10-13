# MiniRogue
Project of Game MiniRogue
This game is currently played in linux command-line but a GUI will be updated on android platform for this game. 
CMDShow class and ReadInput class will be replaced. Current game is to show the skeleton and gameplay of the game system.

This game is a classic dungeon adventure game and I hope that I can make it into a turn-based two-dimensional DIABLO. 
Current features:
1. Denotations:   
              (1) Environments
                  "#": wall
                  " ": space that you can walk on
                  "+": stairway to next dungeon level. If you are in final level, this is the dungeon exit.
              (2) Actors:
                  "I": the player.
                  "X": the dead player.
                  "S": snakewoman.
                  "G": goblin.
                  "B": bogeyman.
                  "D": dragon.
              (3) Items:
              Weapons:
                  "[": axe.
                  "(": sword.
                  "{": ward.
                  "<": hammer.
              Scrolls:
                  "h": health scroll.
                  "d": dexterity scroll.
                  "l": level up scroll.
                  "i": invisible scroll.
                  "s": strength scroll.
2. Player actions: Note that your input need ENTER key to input.
                   (1) Make movement: "w", "a", "s" and "d" are going up, left, south and down.
                   (2) Attack and choose attack subject: type "f" and enter, the player will be in "attack" mode. A "|" label will be shown on player's position as the label of attack subjects, and you can use w, a, s and d to move this label and choose subject. 
                       Put the label on the subject and input "f" again, the player will attack the subject;
                       You can also input "q" to cancel attack mode, and you can still finish your action in this turn.
                       once player killed a monster, it will gain experience and may level up. The monster has some possibility to drop things.
                   (3) Pick up items: move on items on ground and input "p";
                   (4) See inventory: input "i" and you will enter inventory window. "q" will go back from inventory window.
                   (5) Use items in inventory: once you are in inventory window, input the number before item to use it.
                   (6) Cheat to have overwhelming power: input "c" and you will be able to win the game without any efforts.
                   (7) Quit the game. Input "q".
3. Monster behavior for each turn: 
                   (1) smell if the player is in monster's smell range and if so, move one step toward the player;
                   (2) if the player is in monster's weapon's attack range, attack the player.
4. Weapon features:
                   (1) Sword: has lowest attacking range, medium dexterity bonus and medium damage points.
                   (2) Ward: has best attacking range, medium dexterity bonus and low medium damage points. Besides, onced attacked by ward(no matter who uses it), the subject may get "charmed" and fall into sleep and will not make any movements in the next a few turns.
                   (3) Axe: has medium attacking range, low dexterity bonus and good damage points.
                   (4) Hammer: has good attacking rannge, lowest dexterity bonus and best damage points.
5. Scroll features:
                   (1) Dexterity scroll: permanently add player's dexterity.
                   (2) Health scroll: add playere's health point.
                   (3) Level scroll: increase player's level.
                   (4) Strength scroll: permanently add player's strength.
                   (5) Invisible scroll: make the player "invisible" to monsters for next 5 turns. However the monsters will still be able to smell where the player is and move toward the player.
                   
