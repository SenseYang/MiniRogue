package GamePlay;
import java.util.*;

import Facility.ReadInput;
public class Player extends Actor{
	public Player(Dungeon theDungeon){
		super(Types.ActorType.ACTORTYPE_PLAYER, theDungeon);
		setWeapon(Types.WeaponType.WEAPONTYPE_SWORD);
		setLifePoint(maxLife);
		setArmor(10);
		setDexterity(10);
		setStrength(10);
		setSleepTime(0);
		exp = 0;
		level = 1;
		inventory = new ArrayList<NonActor>();
		invisibleTime = 0;
	}
	/**The public interface for player to handle actions
	 * */
	public Types.GameStatus action(){
		// only preliminary version, only read operation from keyboard
		if(getSleepTime() > 0){
			setSleepTime(getSleepTime() - 1);
			System.out.println("You are asleep. Press anykey to continue;");
			ReadInput.getLine();
			return Types.GameStatus.GAMESTATUS_UNDETERMINED;
		}
		if(getInvisibleTime() > 0) invisibleTime--;
		Types.ActionType action = ReadInput.getActionFromKeyboard();
		switch(action){
		case ACTIONTYPE_QUIT:// directly quit the game
			System.out.println("Make sure to QUIT the game?");
			char c = ReadInput.getKey();
			if(c == 'y') return Types.GameStatus.GAMESTATUS_QUIT;
			else return action();
		case ACTIONTYPE_MOVEUP:
			move(Types.DirectionType.DIRECTIONTYPE_NORTH);
			break;
		case ACTIONTYPE_MOVEDOWN:
			move(Types.DirectionType.DIRECTIONTYPE_SOUTH);
			break;
		case ACTIONTYPE_MOVELEFT:
			move(Types.DirectionType.DIRECTIONTYPE_WEST);
			break;
		case ACTIONTYPE_MOVERIGHT:
			move(Types.DirectionType.DIRECTIONTYPE_EAST);
			break;
		case ACTIONTYPE_ATTACK:
			return attack();
		case ACTIONTYPE_PICKUP:
			return pickUp();
		//case ACTIONTYPE_WIELD:
			//return wieldWeapon();// this will be called in seeInventory();
		case ACTIONTYPE_INVENTORY:
			Types.ActionType res = seeInventory();
			if(res == Types.ActionType.ACTIONTYPE_QUIT) return action();
			break;
		case ACTIONTYPE_STAY:
			break;
		case ACTIONTYPE_CHEAT:
			cheat();
			Facilities._drawer.updateFrame();
			return action();
			default:
				break;
		}
		return Types.GameStatus.GAMESTATUS_UNDETERMINED;
	}
	/**Player's action to pick up a non-actor in dungeon.
	 * 
	 * */
	private Types.GameStatus pickUp(){
		if(getUnder().getMyType() == Types.UnitType.UNITTYPE_NEXT) return Types.GameStatus.GAMESTATUS_NEXT;
		if(getUnder().getMyType() == Types.UnitType.UNITTYPE_IDOL) return Types.GameStatus.GAMESTATUS_WIN;
		if(getUnder().getMyType() == Types.UnitType.UNITTYPE_NONACTOR){
			// what if multiple non-actors are piled together in one point?
			DungeonUnit temp = getUnder();
			while(temp.getMyType() == Types.UnitType.UNITTYPE_NONACTOR){
				if(inventory.size() < Types.INVENTORY_CAPACITY) inventory.add((NonActor)temp);
				NonActor i = (NonActor)temp;
				String noun = new String();
				switch(i.getNonActorType()){
				case NONACTORTYPE_WEAPON:
					Weapon w = (Weapon)i;
					switch(w.getWeaponType()){
					case WEAPONTYPE_AXE:
						noun = "Axe";
						break;
					case WEAPONTYPE_SWORD:
						noun = "Sword";
						break;
					case WEAPONTYPE_WARD:
						noun = "Ward";
						break;
					case WEAPONTYPE_HAMMER:
						noun = "Hammer";
						break;
						default:
							break;
					}
					break;
				case NONACTORTYPE_SCROLL:
					Scroll s = (Scroll) i;
					switch(s.getScrollType()){
					case SCROLLTYPE_HEALTH:
						noun = "Health Scroll";
						break;
					case SCROLLTYPE_DEXTERITY:
						noun = "Dexterity Scroll";
						break;
					case SCROLLTYPE_STRENGTH:
						noun = "Strength Scroll";
						break;
					case SCROLLTYPE_LEVEL:
						noun = "Level Scroll";
						break;
					case SCROLLTYPE_INVISIBLE:
						noun = "Invisible Scroll";
						break;
						default:
							break;
					}
					break;
					default:
						break;
				}
				temp = temp.getUnder();
				Facilities._drawer.message += ("You picked up " + noun + " and put it in your inventory.\n");
			}
			setUnder(temp);
		}
		return Types.GameStatus.GAMESTATUS_UNDETERMINED;
	}
	/**
	 * Player makes attack to possible enemy in range.
	 * Currently only support nearby attack. 
	 * FAR-FIELD ATTACK IS NOT IMPLEMENTED.
	 * */
	private Types.GameStatus attack(){
		int curRow = getRow(), curCol = getCol();	
		getDungeon().dungeon[curRow][curCol].labelOn = true;
		Facilities._drawer.updateFrame();
		System.out.println("Choose your victim!");
		boolean orderAccept = false;
		boolean isQuit = false;
		while(!orderAccept && !isQuit){
			Types.ActionType order = ReadInput.getActionFromKeyboard();
			int range = getWeapon().getAttackRange();
			switch(order){
			case ACTIONTYPE_MOVEUP:
				// if not out of bound
				if(curRow > 1 && Math.abs(curRow - 1 - getRow()) + Math.abs(curCol - getCol()) <= range
				&& Math.abs(curRow - getRow()) + Math.abs(curCol - getCol()) <= range){
					getDungeon().dungeon[curRow][curCol].labelOn = false;
					curRow--;
					getDungeon().dungeon[curRow][curCol].labelOn = true;
				}
				Facilities._drawer.updateFrame();
				break;
			case ACTIONTYPE_MOVEDOWN:
				if(curRow < Types.SIZE_ROW - 2 && Math.abs(curRow + 1 - getRow()) + Math.abs(curCol - getCol()) <= range
				&& Math.abs(curRow - getRow()) + Math.abs(curCol - getCol()) <= range){
					getDungeon().dungeon[curRow][curCol].labelOn = false;
					curRow++;
					getDungeon().dungeon[curRow][curCol].labelOn = true;
				}
				Facilities._drawer.updateFrame();
				break;
			case ACTIONTYPE_MOVELEFT:
				if(curCol > 1 && Math.abs(curRow - getRow()) + Math.abs(curCol - 1 - getCol()) <= range
				&& Math.abs(curRow - getRow()) + Math.abs(curCol - getCol()) <= range){
					getDungeon().dungeon[curRow][curCol].labelOn = false;
					curCol--;
					getDungeon().dungeon[curRow][curCol].labelOn = true;
				}
				Facilities._drawer.updateFrame();
				break;
			case ACTIONTYPE_MOVERIGHT:
				if(curCol < Types.SIZE_COL - 2 && Math.abs(curRow - getRow()) + Math.abs(curCol + 1 - getCol()) <= range
				&& Math.abs(curRow - getRow()) + Math.abs(curCol - getCol()) <= range){
					getDungeon().dungeon[curRow][curCol].labelOn = false;
					curCol++;
					getDungeon().dungeon[curRow][curCol].labelOn = true;
				}
				Facilities._drawer.updateFrame();
				break;
			case ACTIONTYPE_ATTACK:
				if(getDungeon().dungeon[curRow][curCol].getMyType() == Types.UnitType.UNITTYPE_ACTOR){
					Actor a = (Actor) getDungeon().dungeon[curRow][curCol];
					if(a.getMyActorType() == Types.ActorType.ACTORTYPE_MONSTER){
						System.out.println("Attack valid monster");
						orderAccept = true;
						getDungeon().dungeon[curRow][curCol].labelOn = false;// attack finished
					}
					else{
						Facilities._drawer.message = "Don't attack yourself!";
						Facilities._drawer.updateFrame();
					}
				}
				else{
					Facilities._drawer.message = "Invalid attack object!";
					Facilities._drawer.updateFrame();
				}
				break;
			case ACTIONTYPE_QUIT:// quit attack status
				isQuit = true;
				break;
				default:
					Facilities._drawer.message = "Invalid Input. Please use w/a/s/d/f as UP/LEFT/DOWN/RIGHT/FIRE.";
					Facilities._drawer.updateFrame();
					break;
			}
		}
		if(orderAccept){
			Monster m = (Monster) getDungeon().dungeon[curRow][curCol];
			int a = getAttackPoint(), d = m.getDefendPoint();
			if(a > d){
				int damage = getDamagePoint();
				m.setLifePoint(m.getLifePoint() - damage);
				Types.MonsterType t = m.getMonsterType();
				if(m.getLifePoint() <= 0){
					m.dead();// drop things
					getDungeon().monsterArray.remove(getDungeon().monsterArray.indexOf(m));// be careful here
					Facilities._drawer.message = "You attacked " +
							(t == Types.MonsterType.MONSTERTYPE_BOGEYMAN ? "Bogeyman" 
									: t == Types.MonsterType.MONSTERTYPE_DRAGON ? "Dragon" 
											: t == Types.MonsterType.MONSTERTYPE_GOBLIN ? "Goblin"
													: "Snakewoman")
					+ " and give it a final blow.\n";
					addExp(m);
				}
				else{
					Facilities._drawer.message = "You attacked " + 
					(t == Types.MonsterType.MONSTERTYPE_BOGEYMAN ? "Bogeyman" 
							: t == Types.MonsterType.MONSTERTYPE_DRAGON ? "Dragon" 
									: t == Types.MonsterType.MONSTERTYPE_GOBLIN ? "Goblin"
											: "Snakewoman")
				+ " and give it " + damage + " points of damage.\n";
				}
			}
			else{
				Facilities._drawer.message = "You missed your attack!\n";
			}
			getWeapon().specialEffect(this, m);
			return Types.GameStatus.GAMESTATUS_UNDETERMINED;
		}
		else{
			getDungeon().dungeon[curRow][curCol].labelOn = false;
			Facilities._drawer.updateFrame();
			return action();
		}
	}
	/**
	 * Look at inventory*/
	private Types.ActionType seeInventory(){
		Facilities._drawer.message = new String();
		int index = Facilities._drawer.showInventory(inventory);
		if(index == -1){
			Facilities._drawer.updateFrame();
			return Types.ActionType.ACTIONTYPE_QUIT;
		}
		NonActor item = inventory.get(index);
		if(item.getNonActorType() == Types.NonActorType.NONACTORTYPE_WEAPON){
			Weapon temp = getWeapon();
			setWeapon((Weapon)item);
			inventory.set(index, temp);
		}
		else if(item.getNonActorType() == Types.NonActorType.NONACTORTYPE_SCROLL){
			Scroll s = (Scroll)item;
			s.takeEffect(this);
			inventory.remove(item);
		}
		// else: other non-actor items
		return Types.ActionType.ACTIONTYPE_WIELD;
	}
	/**
	 * Cheat*/
	private void cheat(){// you a invincible!
		this.setArmor(1000);
		this.setLifePoint(1000);
		this.setStrength(1000);
		this.setDexterity(1000);
	}
	/**Add experience when you killed an monster. */
	private void addExp(Monster m){
		switch(m.getMonsterType()){
		case MONSTERTYPE_GOBLIN:
			exp += (level > 10 ? 10 : 10 + (10 - level) * 2);
			break;
		case MONSTERTYPE_SNAKEWOMAN:
			exp += (level > 15 ? 20 : 20 + (15 - level) * 1.5);
			break;
		case MONSTERTYPE_BOGEYMAN:
			exp += (level > 20 ? 20 : 20 + (20 - level) * 2);
			break;
		case MONSTERTYPE_DRAGON:
			exp += (level > 25 ? 25 : 25 + (25 - level) * 2);
			break;
			default:
				break;
		}
		if(exp >= 100){
			exp -= 100;
			levelUp();
		}
	}
	/**
	 * For LevelScroll to call*/
	public void addExp(int val){
		exp += val;
		if(exp >= 100){
			exp -= 100;
			levelUp();
		}
	}
	/**Player's level get up when exp >= 100.*/
	private void levelUp(){
		level++;
		setArmor((int)(getArmor() + 2));
		maxLife += 20;
		setLifePoint(maxLife);
		setDexterity((int)(getDexterity() + 3));
		setStrength((int)(getStrength() + 3));
	}
	public int getLevel(){
		return level;
	}
	public int getExperience(){
		return exp;
	}
	public int getInvisibleTime(){
		return invisibleTime;
	}
	public void setInvisibleTime(int i){
		invisibleTime = i;
	}
	/**
	 * Experience*/
	private int level;
	private int exp;
	private ArrayList<NonActor> inventory;
	private int maxLife = 200;
	private int invisibleTime;
}
