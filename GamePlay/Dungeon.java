package GamePlay;
/**
 * @author Sen*/
import java.util.*;

import Monster.Bogeyman;
import Monster.Dragon;
import Monster.Goblin;
import Monster.Snakewoman;
import Scroll.DexterityScroll;
import Scroll.HealthScroll;
import Scroll.InvisibleScroll;
import Scroll.LevelScroll;
import Scroll.StrengthScroll;
import Weapon.Axe;
import Weapon.Hammer;
import Weapon.Sword;
import Weapon.Ward;
public class Dungeon {
	public Dungeon(int currentLevel){
		this.currentLevel = currentLevel;
		numOfRooms = (int)(Math.random() * 10) + 5;
		roomArray = new ArrayList<Room>();
		monsterArray = new ArrayList<Monster>();
		init();
	}
	/**
	 * Generate rooms and connect the rooms with hallway*/
	private void init(){
		dungeon = new DungeonUnit[Types.SIZE_ROW][Types.SIZE_COL];
		for(int i = 0; i < Types.SIZE_ROW; i++){
			for(int j = 0; j < Types.SIZE_COL; j++){
				dungeon[i][j] = new DungeonUnit(Types.UnitType.UNITTYPE_WALL);
			}
		}
		generateRooms();
		generateHallway();
		generatePlayer();
		generateMonsters();
		generateNonActors();
	}
	
	//------Dungeon generetion functions---------------
	/**
	 * Generate rooms randomly in the dungeon*/
	private void generateRooms(){
		int rmCt = 0;
		while(rmCt <= numOfRooms){			
			int tryTime = 0;
			boolean found = false;
			while(!found && tryTime <= 1000){
				int horiTrans = (int) (Math.random() * Types.ROOM_ROW_MAX * 5);
				int vertTrans = (int) (Math.random() * Types.ROOM_COL_MAX * 5);
				int r1 = (int) (Math.random() * (Types.SIZE_ROW - Types.WALL_WIDTH * 2 - horiTrans)) + Types.WALL_WIDTH + horiTrans;
				int c1 = (int) (Math.random() * (Types.SIZE_COL - Types.WALL_WIDTH * 2 - vertTrans)) + Types.WALL_WIDTH + vertTrans;
				int r2 = (int) (Math.random() * (Types.ROOM_ROW_MAX - Types.ROOM_ROW_MIN)) + Types.ROOM_ROW_MIN + r1;
				int c2 = (int) (Math.random() * (Types.ROOM_COL_MAX - Types.ROOM_COL_MIN)) + Types.ROOM_COL_MIN + c1;
				r2 = Math.min(r2, Types.SIZE_ROW - (Types.WALL_WIDTH + 1));
				c2 = Math.min(c2, Types.SIZE_COL - (Types.WALL_WIDTH + 1));
				Room curRoom = new Room(r1, c1, r2 ,c2);
				if(roomValid(curRoom)) found = true;
				else found = adjustRoom(curRoom);
				if(found){
					roomArray.add(curRoom);
					makeRoomInDungeon(curRoom);
					rmCt++;
				}
				tryTime++;
			}
			rmCt++;
		}
	}
	/**
	 * See if current room is valid*/
	private boolean roomValid(Room r){
		int row1 = Math.max(Types.WALL_WIDTH, r.startRow - (Types.WALL_WIDTH + 1)), 
				row2 = Math.min(Types.SIZE_ROW - (Types.WALL_WIDTH + 1), r.endRow + (Types.WALL_WIDTH + 1)), 
				col1 = Math.max(Types.WALL_WIDTH, r.startCol - (Types.WALL_WIDTH + 1)),
				col2 = Math.min(Types.SIZE_COL - (Types.WALL_WIDTH + 1), r.endCol + (Types.WALL_WIDTH + 1));
		for(int i = row1; i <= row2; i++){
			for(int j = col1; j <= col2; j++){
				if(dungeon[i][j].getMyType() == Types.UnitType.UNITTYPE_ROOM) return false;
			}
		}
		if(r.endRow - r.startRow + 1 < Types.ROOM_ROW_MIN 
				||
				r.endCol - r.startCol + 1 < Types.ROOM_COL_MIN) return false;
		return true;
	}
	/**
	 * Update newly generated rooms into the dungeon map*/
	void makeRoomInDungeon(Room r){
		for(int i = r.startRow; i <= r.endRow; i++ ){
			for(int j = r.startCol; j <= r.endCol; j++){
				dungeon[i][j].setMyType(Types.UnitType.UNITTYPE_ROOM);
			}
		}
	}
	/**
	 * Adjust current room to see if it can fit around
	 * */
	private boolean adjustRoom(Room r){
		if(!translateRoom(r)){
			if(!shrinkRoom(r)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Translate the room*/
	private boolean translateRoom(Room r){
		int step = 1;
		Room temp = new Room(r);
		// go north
		while(temp.startRow >= Types.WALL_WIDTH){
			if(roomValid(temp)){
				r.startRow = temp.startRow;
				r.endRow = temp.endRow;
				return true;
			}
			temp.startRow -= step;
			temp.endRow -= step;
		}
		// go south
		temp = new Room(r);
		while(temp.endRow <= Types.SIZE_ROW - Types.WALL_WIDTH - 1){
			if(roomValid(temp)){
				r.startRow = temp.startRow;
				r.endRow = temp.endRow;
				return true;
			}
			temp.startRow += step;
			temp.endRow += step;
		}
		// go left
		temp = new Room(r);
		while(temp.startCol >= Types.WALL_WIDTH){
			if(roomValid(temp)){
				r.startCol = temp.startCol;
				r.endCol = temp.endCol;
				return true;
			}
			temp.startCol -= step;
			temp.endCol -= step;
		}
		// go right
		temp = new Room(r);
		while(temp.endCol <= Types.SIZE_COL - Types.WALL_WIDTH - 1){
			if(roomValid(temp)){
				r.startCol = temp.startCol;
				r.endCol = temp.endCol;
				return true;
			}
			temp.startCol += step;
			temp.endCol += step;
		}
		return false;
	}
	/**
	 * Shrink the room*/
	private boolean shrinkRoom(Room r){
		Room temp = new Room(r);
		while(temp.endRow - temp.startRow + 1 >= Types.ROOM_ROW_MIN){
			if(roomValid(temp)){
				r.startRow = temp.startRow;
				r.endRow = temp.endRow;
				return true;
			}
			temp.endRow--;
		}
		
		temp = new Room(r);
		while(temp.endRow - temp.startRow + 1 >= Types.ROOM_ROW_MIN){
			if(roomValid(temp)){
				r.startRow = temp.startRow;
				r.endRow = temp.endRow;
				return true;
			}
			temp.startRow++;
		}
		
		temp = new Room(r);
		while(temp.endCol - temp.startCol + 1 >= Types.ROOM_COL_MIN){
			if(roomValid(temp)){
				r.startCol = temp.startCol;
				r.endCol = temp.endCol;
				return true;
			}
			temp.startCol++;
		}
		
		temp = new Room(r);
		while(temp.endCol - temp.startCol + 1 >= Types.ROOM_COL_MIN){
			if(roomValid(temp)){
				r.startCol = temp.startCol;
				r.endCol = temp.endCol;
				return true;
			}
			temp.endCol--;
		}
		return false;
	}
	/**
	 * Generate hallway among rooms*/
	private void generateHallway(){
		// generate Hallway Among Those Rooms. use a helper to assist it
		int tot = roomArray.size();
		for(int i = 1; i < tot; i++){
			int numOfHallway = (int) (Math.random() * 3) + 3, ct = 0;
			while(ct < numOfHallway){
				int index = (int) (Math.random() * i);
				hallwayGenerator(roomArray.get(index), roomArray.get(i));
				ct++;
			}
		}
	}
	/**
	 * To help generaeteHallway() function and make a hallway between two rooms*/
	private void hallwayGenerator(Room r1, Room r2){
		int edge = (int)(Math.random() * 4);
		int row, col;
		switch(edge){
		case 0://left
			row = (int)(Math.random() * (r2.endRow - r2.startRow)) + r2.startRow;
			col = r2.startCol - 1;
			break;
		case 1://up
			col = (int)(Math.random() * (r2.endCol - r2.startCol)) + r2.startCol;
			row = r2.startRow - 1;
			break;
		case 2://right
			row = (int)(Math.random() * (r2.endRow - r2.startRow)) + r2.startRow;
			col = r2.endCol + 1;
			break;
		default://bottom
			col = (int)(Math.random() * (r2.endCol - r2.startCol)) + r2.startCol;
			row = r2.endRow + 1;
			break;
		}
		hallwayGeneratorHelper(row, col, r1);
		// reset the map
		for(int i = 0; i < Types.SIZE_ROW; i++){
			for(int j = 0; j < Types.SIZE_COL; j++){
				if(dungeon[i][j].getMyType() == Types.UnitType.UNITTYPE_WALL_TEMP)
					dungeon[i][j].setMyType(Types.UnitType.UNITTYPE_WALL);
			}
		}
	}
	/**
	 * Recusive function to find a way from room 1 to room 2 */
	private boolean hallwayGeneratorHelper(int row, int col, Room r){
		if(row < 1 || col < 1 || row >= Types.SIZE_ROW - 1 || col >= Types.SIZE_COL - 1) return false;
		if(row <= r.endRow
				&& row >= r.startRow
				&& col <= r.endCol
				&& col >= r.startCol) return true;
		if(dungeon[row][col].getMyType() == Types.UnitType.UNITTYPE_HALLWAY) return true;
		if((dungeon[row - 1][col].getMyType() == Types.UnitType.UNITTYPE_HALLWAY && dungeon[row + 1][col].getMyType() == Types.UnitType.UNITTYPE_HALLWAY)
				||
				(dungeon[row][col - 1].getMyType() == Types.UnitType.UNITTYPE_HALLWAY && dungeon[row][col + 1].getMyType() == Types.UnitType.UNITTYPE_HALLWAY)
				)return false;
		if(dungeon[row][col].getMyType() == Types.UnitType.UNITTYPE_ROOM
				|| dungeon[row][col].getMyType() == Types.UnitType.UNITTYPE_WALL_TEMP) return false;
		dungeon[row][col].setMyType(Types.UnitType.UNITTYPE_WALL_TEMP);
		int direction = (int) (Math.random() * 4);
		boolean res;
		switch(direction){
		case 0://north
			res = hallwayGeneratorHelper(row - 1, col, r);
			if(!res){
				res = hallwayGeneratorHelper(row, col + 1, r);
				if(!res){
					res = hallwayGeneratorHelper(row + 1, col, r);
					if(!res){
						res = hallwayGeneratorHelper(row, col - 1, r);
						if(!res) return false;
					}
				}
			}
			break;
		case 1://east
			res = hallwayGeneratorHelper(row, col + 1, r);
			if(!res){
				res = hallwayGeneratorHelper(row + 1, col, r);
				if(!res){
					res = hallwayGeneratorHelper(row, col - 1, r);
					if(!res){
						res = hallwayGeneratorHelper(row - 1, col, r);
						if(!res) return false;
					}
				}
			}
			break;
		case 2://south
			res = hallwayGeneratorHelper(row + 1, col, r);
			if(!res){
				res = hallwayGeneratorHelper(row, col - 1, r);
				if(!res){
					res = hallwayGeneratorHelper(row - 1, col, r);
					if(!res){
						res = hallwayGeneratorHelper(row, col + 1, r);
						if(!res) return false;
					}
				}
			}
			break;
		default:// west
			res = hallwayGeneratorHelper(row, col - 1, r);
			if(!res){
				res = hallwayGeneratorHelper(row - 1, col, r);
				if(!res){
					res = hallwayGeneratorHelper(row, col + 1, r);
					if(!res){
						res = hallwayGeneratorHelper(row + 1, col, r);
						if(!res) return false;
					}
				}
			}
			break;
		}
		if(res){
			dungeon[row][col].setMyType(Types.UnitType.UNITTYPE_HALLWAY);
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * Generate Actors in the well-generated dungeon. May only be called in Dungeon.init()*/
	private void generatePlayer(){
		// first pick a room for player, then randomly choose a place there
		int roomNum = (int) (Math.random() * roomArray.size());
		Room r = roomArray.get(roomNum);
		int row = (int)(Math.random() * (r.endRow - r.startRow)) + r.startRow;
		int col = (int)(Math.random() * (r.endCol - r.startCol)) + r.startCol;
		player = new Player(this);
		player.setRow(row);
		player.setCol(col);
		player.setUnder(dungeon[row][col]);
		dungeon[row][col] = player;
	}
	/**
	 * Generate Monsters in the dungeon*/
	private void generateMonsters(){ 
		int lastMonster = (currentLevel >= 3) ? Types.MONSTER_VALUES.size(): (currentLevel >= 2? Types.MONSTER_VALUES.size() - 1: Types.MONSTER_VALUES.size() - 2);
		int totMonsters = (int)(Math.random() * 5 * (currentLevel + 1) + 2);
		for(int i = 0; i < totMonsters; i++){
			int kindOfMonster = (int)(Math.random() * lastMonster);
			Monster newMonster;
			switch(Types.MONSTER_VALUES.get(kindOfMonster)){
			case MONSTERTYPE_SNAKEWOMAN:
				newMonster = new Snakewoman(this);
				break;
			case MONSTERTYPE_GOBLIN:
				newMonster = new Goblin(this);
				break;
			case MONSTERTYPE_BOGEYMAN:
				newMonster = new Bogeyman(this);
				break;
			/*case MONSTERTYPE_DRAGON:
				newMonster = new Dragon();
				break;*/
			default:
				newMonster = new Dragon(this);
				break;
			}
			boolean settled = false;
			int counter = 0;
			while(!settled && counter++ < 200){
				int roomNum = (int)(Math.random() * roomArray.size());
				Room r = roomArray.get(roomNum);
				int row = (int)(Math.random() * (r.endRow - r.startRow)) + r.startRow;
				int col = (int)(Math.random() * (r.endCol - r.startCol)) + r.startCol;
				if(dungeon[row][col].getMyType() == Types.UnitType.UNITTYPE_ROOM){
					newMonster.setUnder(dungeon[row][col]);
					dungeon[row][col] = newMonster;
					newMonster.setRow(row);
					newMonster.setCol(col);
					settled = true;
				}
				counter++;
			}
			monsterArray.add(newMonster);
		}
	}
	/**
	 * Generate Non-Actors in the well-generated dungeon.
	 * 1. Generate stairway or gold idol.
	 * 2. Generate Weapons and Scrolls and put them on ground.*/
	private void generateNonActors(){
		// generate idol or stairway
		DungeonUnit idol;
		if(currentLevel < 5) idol = new DungeonUnit(Types.UnitType.UNITTYPE_NEXT);
		else idol = new DungeonUnit(Types.UnitType.UNITTYPE_IDOL);
		int roomNum = (int)(Math.random() * roomArray.size());
		Room r = roomArray.get(roomNum);
		int row = (int)(Math.random() * (r.endRow - r.startRow)) + r.startRow;
		int col = (int)(Math.random() * (r.endCol - r.startCol)) + r.startCol;
		if(dungeon[row][col].getMyType() == Types.UnitType.UNITTYPE_ROOM){
			idol.setUnder(dungeon[row][col]);
			dungeon[row][col] = idol;
		}
		else{// already occupied by another actor or non actor
			boolean settled = false;
			for(; row < Types.SIZE_ROW - 1 && !settled; row++){
				for(; col < Types.SIZE_COL - 1 && !settled; col++){
					if(dungeon[row][col].getMyType() == Types.UnitType.UNITTYPE_ROOM){
						idol.setUnder(dungeon[row][col]);
						dungeon[row][col] = idol;
						settled = true;
					}
				}
			}
			if(!settled){
				row = 1; col = 1;
				for(; row < Types.SIZE_ROW - 1 && !settled; row++){
					for(; col < Types.SIZE_COL - 1 && !settled; col++){
						if(dungeon[row][col].getMyType() == Types.UnitType.UNITTYPE_ROOM){
							idol.setUnder(dungeon[row][col]);
							dungeon[row][col] = idol;
							settled = true;
						}
					}
				}
			}
		}
		// generate Weapon 
		int numOfWeapons = (int)(Math.random() * (currentLevel + 1) * 5);
		int counter = 0;
		while(counter < numOfWeapons){
			double dice = Math.random();
			Types.NonActorType type = (dice < 0.5) ? Types.NonActorType.NONACTORTYPE_WEAPON : Types.NonActorType.NONACTORTYPE_SCROLL;
			NonActor item = null;
			switch(type){
			case NONACTORTYPE_WEAPON:
				int kindOfWeapon = (int)(Math.random() * Types.WEAPON_VALUES.size());
				Types.WeaponType wt = Types.WEAPON_VALUES.get(kindOfWeapon);
				switch(wt){
				case WEAPONTYPE_AXE:
					item = new Axe();
					break;
				case WEAPONTYPE_SWORD:
					item = new Sword();
					break;
				case WEAPONTYPE_WARD:
					item = new Ward();
					break;
				case WEAPONTYPE_HAMMER:
					item = new Hammer();
					break;
					default:
						break;
				}
				break;
			case NONACTORTYPE_SCROLL:
				int kindOfScroll = (int)(Math.random() * Types.SCROLL_VALUES.size());
				Types.ScrollType st = Types.SCROLL_VALUES.get(kindOfScroll);
				switch(st){
				case SCROLLTYPE_HEALTH:
					item = new HealthScroll();
					break;
				case SCROLLTYPE_DEXTERITY:
					item = new DexterityScroll();
					break;
				case SCROLLTYPE_STRENGTH:
					item = new StrengthScroll();
					break;
				case SCROLLTYPE_LEVEL:
					item = new LevelScroll();
					break;
				case SCROLLTYPE_INVISIBLE:
					item = new InvisibleScroll();
					default:
						break;
				}
				break;
				default:
					break;
			}
			// put in room
			if(item != null){
				roomNum = (int)(Math.random() * roomArray.size());
				r = roomArray.get(roomNum);
				row = (int)(Math.random() * (r.endRow - r.startRow)) + r.startRow;
				col = (int)(Math.random() * (r.endCol - r.startCol)) + r.startCol;
				if(dungeon[row][col].getMyType() == Types.UnitType.UNITTYPE_ROOM){
					item.setUnder(dungeon[row][col]);
					dungeon[row][col] = item;
				}
				else{// already occupied by another actor or non actor
					boolean settled = false;
					for(; row < Types.SIZE_ROW - 1 && !settled; row++){
						for(; col < Types.SIZE_COL - 1 && !settled; col++){
							if(dungeon[row][col].getMyType() == Types.UnitType.UNITTYPE_ROOM){
								item.setUnder(dungeon[row][col]);
								dungeon[row][col] = item;
								settled = true;
							}
						}
					}
					if(!settled){
						row = 1; col = 1;
						for(; row < Types.SIZE_ROW - 1 && !settled; row++){
							for(; col < Types.SIZE_COL - 1 && !settled; col++){
								if(dungeon[row][col].getMyType() == Types.UnitType.UNITTYPE_ROOM){
									item.setUnder(dungeon[row][col]);
									dungeon[row][col] = item;
									settled = true;
								}
							}
						}
					}
				}
			}
			counter++;
		}
	}
	//------------DM communication methods---------
	/**
	 * When the CMDShow wants to draw the map, it need information for the unittypes. */
	public Types.UnitType getType(int row, int col){
		return dungeon[row][col].getMyType();
	}
	/**
	 * determine actor types, only when unittype = actor!!!*/
	public Types.ActorType getActorType(int row, int col){
		Actor a = (Actor) dungeon[row][col];
		return a.getMyActorType();
	}
	/**
	 * determine monster Types. only when actortype == monster!*/
	public Types.MonsterType getMonsterType(int row, int col){
		Monster m = (Monster) dungeon[row][col];
		return m.getMonsterType();
	}
	/**
	 * determine monster Types. only when unittype == nonactor!*/
	public Types.NonActorType getNonActorType(int row, int col){
		NonActor m = (NonActor) dungeon[row][col];
		return m.getNonActorType();
	}
	/**
	 * determine weapon Types. only when nonactortype == weapon!*/
	public Types.WeaponType getWeaponType(int row, int col){
		Weapon m = (Weapon) dungeon[row][col];
		return m.getWeaponType();
	}
	/**
	 * determine scroll Types. only when nonactortype == scroll!*/
	public Types.ScrollType getScrollType(int row, int col){
		Scroll m = (Scroll) dungeon[row][col];
		return m.getScrollType();
	}
	public boolean isLabelOn(int row, int col){
		return dungeon[row][col].labelOn;
	}
	//---------------------
	/**
	 * Get Player object reference and pass it to data manager for further use.*/
	public Player getPlayer(){
		return player;
	}
	/**
	 * Put existing player into current Dungeon*/
	public void putPlayer(){
		int roomNum = (int)(Math.random() * roomArray.size());
		Room r = roomArray.get(roomNum);
		int row = (int)(Math.random() * (r.endRow - r.startRow)) + r.startRow;
		int col = (int)(Math.random() * (r.endCol - r.startCol)) + r.startCol;
		if(dungeon[row][col].getMyType() == Types.UnitType.UNITTYPE_ROOM){
			player.setUnder(dungeon[row][col]);
			dungeon[row][col] = player;
			player.setRow(row);
			player.setCol(col);
		}
		else{// already occupied by another actor or non actor
			boolean settled = false;
			for(; row < Types.SIZE_ROW - 1 && !settled; row++){
				for(; col < Types.SIZE_COL - 1 && !settled; col++){
					if(dungeon[row][col].getMyType() == Types.UnitType.UNITTYPE_ROOM){
						player.setUnder(dungeon[row][col]);
						dungeon[row][col] = player;
						settled = true;
						player.setRow(row);
						player.setCol(col);
					}
				}
			}
			if(!settled){
				row = 1; col = 1;
				for(; row < Types.SIZE_ROW - 1 && !settled; row++){
					for(; col < Types.SIZE_COL - 1 && !settled; col++){
						if(dungeon[row][col].getMyType() == Types.UnitType.UNITTYPE_ROOM){
							player.setUnder(dungeon[row][col]);
							dungeon[row][col] = player;
							settled = true;
							player.setRow(row);
							player.setCol(col);
						}
					}
				}
			}
		}
	}
	/**
	 * Used to update the Dungeon when DM's nextLevel() is called.
	 * */
	public Types.GameStatus nextLevel(){
		currentLevel++;
		roomArray.clear();
		monsterArray.clear();
		dungeon = new DungeonUnit[Types.SIZE_ROW][Types.SIZE_COL];
		for(int i = 0; i < Types.SIZE_ROW; i++){
			for(int j = 0; j < Types.SIZE_COL; j++){
				dungeon[i][j] = new DungeonUnit(Types.UnitType.UNITTYPE_WALL);
			}
		}
		player.setUnder(null);
		generateRooms();
		generateHallway();
		putPlayer();// do not need to generate the player again; simply put existing player into it
		generateMonsters();
		generateNonActors();
		return Types.GameStatus.GAMESTATUS_UNDETERMINED;
	}
	//---------------------------Actor communication methods-------
	
	// --------------------------instances------------------------
	/**
	 * current dungeon level*/
	public int currentLevel;
	/**
	 * Number of rooms in the dungeon*/
	private int numOfRooms; 
	/**
	 * The storage of room information*/
	private ArrayList<Room> roomArray;
	/**
	 * The storage of the 2D array of DungonUnit objects*/
	public DungeonUnit [][] dungeon;
	/**
	 * An ArrayList of Monsters*/
	public ArrayList<Monster> monsterArray;
	/**
	 * Player reference*/
	public Player player;
	public DataManager _dataManager;
}

class Room{
	public Room(int r1, int c1, int r2, int c2){
		startRow = r1;
		startCol = c1;
		endRow = r2;
		endCol = c2;
	}
	public Room(Room r){
		startRow = r.startRow;
		startCol = r.startCol;
		endRow = r.endRow;
		endCol = r.endCol;
	}
	public int startRow;
	public int startCol;
	public int endRow;
	public int endCol;
}
