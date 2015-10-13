package GamePlay;
import java.util.*;

/**
 * @author Sen
 * This class is for all the enum type definitions*/
public class Types {
	// GameStatus, to determine the current status of the game
	public enum GameStatus{
		GAMESTATUS_LOSE,
		GAMESTATUS_WIN,
		GAMESTATUS_UNDETERMINED,
		GAMESTATUS_QUIT,
		GAMESTATUS_NEXT
	}
	// UnitType, is used to determine the type of dungeon units 
	public enum UnitType{
		UNITTYPE_WALL,
		UNITTYPE_WALL_TEMP,// to assist hallwayGeneratorHelper() function
		UNITTYPE_ROOM,
		UNITTYPE_HALLWAY,
		UNITTYPE_NEXT,
		UNITTYPE_IDOL,
		UNITTYPE_ACTOR,
		UNITTYPE_NONACTOR,
		// to assist some functionalities
		UNITTYPE_ROOM_TEMP// assist the room generation 
	}
	// Actor Types, used to determine the type of actors
	public enum ActorType{
		ACTORTYPE_PLAYER,
		ACTORTYPE_PLAYER_DEAD,
		ACTORTYPE_MONSTER
	}
	public enum MonsterType{
		MONSTERTYPE_SNAKEWOMAN,
		MONSTERTYPE_GOBLIN,
		MONSTERTYPE_BOGEYMAN,//level >= 2
		MONSTERTYPE_DRAGON// level >= 3		
	}
	public static final List<MonsterType> MONSTER_VALUES = 
			Collections.unmodifiableList(Arrays.asList(MonsterType.values()));
	public enum NonActorType{
		NONACTORTYPE_WEAPON,
		NONACTORTYPE_SCROLL,
		NONACTORTYPE_DRUG
		//, NONACTORTYPE_ARMOR
	}
	public enum WeaponType{
		WEAPONTYPE_AXE,
		WEAPONTYPE_SWORD,
		WEAPONTYPE_WARD,
		WEAPONTYPE_HAMMER
	}
	public static final List<WeaponType> WEAPON_VALUES = 
			Collections.unmodifiableList(Arrays.asList(WeaponType.values()));
	
	// scroll types
	public enum ScrollType{
		SCROLLTYPE_HEALTH,// add health permanently
		SCROLLTYPE_DEXTERITY,// add dexterity permanently
		SCROLLTYPE_STRENGTH,// add strength permanently
		SCROLLTYPE_LEVEL,// level up permanently
		SCROLLTYPE_INVISIBLE// make you invisible to monsters during some time. Not permanent
	}
	public static final List<ScrollType> SCROLL_VALUES =
			Collections.unmodifiableList(Arrays.asList(ScrollType.values()));
	// define action types for player
	public enum ActionType{
		ACTIONTYPE_QUIT,
		ACTIONTYPE_MOVEUP,
		ACTIONTYPE_MOVEDOWN,
		ACTIONTYPE_MOVELEFT,
		ACTIONTYPE_MOVERIGHT,
		ACTIONTYPE_ATTACK,
		ACTIONTYPE_PICKUP,
		ACTIONTYPE_WIELD,
		ACTIONTYPE_INVENTORY,
		ACTIONTYPE_CHEAT,
		ACTIONTYPE_STAY// do nothing
	}
	// directions
	public enum DirectionType{
		DIRECTIONTYPE_NORTH,
		DIRECTIONTYPE_SOUTH,
		DIRECTIONTYPE_WEST,
		DIRECTIONTYPE_EAST,
		DIRECTIONTYPE_NONE// do not move
	}
	public static char LABEL_ON = '|';
	public static int ROOM_ROW_MAX = 8;
	public static int ROOM_ROW_MIN = 3;
	public static int ROOM_COL_MAX = 10; 
	public static int ROOM_COL_MIN = 4; 
	public static int WALL_WIDTH = 3;
	public static int SIZE_ROW;
	public static int SIZE_COL;
	public static int INVENTORY_CAPACITY = 10;
}
