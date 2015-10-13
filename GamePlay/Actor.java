package GamePlay;
import Weapon.*;
/**
 * @author Sen 
 * This class is the abstract class for Actors, including player and monster*/
public abstract class Actor extends DungeonUnit {
	public Actor(Types.ActorType aType, Dungeon theDungeon){
		super(Types.UnitType.UNITTYPE_ACTOR);
		myActorType = aType;
		_dungeon = theDungeon;
	}
	public void setWeapon(Types.WeaponType aType){
		Weapon w;
		switch(aType){
		case WEAPONTYPE_SWORD:
			w = new Sword();
			break;
		case WEAPONTYPE_HAMMER:
			w = new Hammer();
			break;
		case WEAPONTYPE_WARD:
			w = new Ward();
			break;
			default:
				w = new Axe();
				break;
		}
		myWeapon = w;
	}
	public void setWeapon(Weapon w){
		myWeapon = w;
	}
	public Weapon getWeapon(){
		return myWeapon;
	}
	public void setMyActorType(Types.ActorType a){
		myActorType = a;
	}
	public Types.ActorType getMyActorType(){
		return myActorType;
	}
	public void setDungeon(Dungeon dungeon){
		_dungeon = dungeon;
	}
	public Dungeon getDungeon(){
		return _dungeon;
	}
	/**
	 * An interface for Actor objects to handle actions*/
	public abstract Types.GameStatus action();
	public void setRow(int a){
		row = a;
	}
	public int getRow(){
		return row;
	}
	public void setCol(int a){
		col = a;
	}
	public int getCol(){
		return col;
	}
	public void setLifePoint(int a){
		lifePoint = a;
	}
	public int getLifePoint(){
		return lifePoint;
	}
	public void setArmor(int a){
		armor = a;
	}
	public int getArmor(){
		return armor;
	}
	public void setDexterity(int a){
		dexterity = a;
	}
	public int getDexterity(){
		return dexterity;
	}
	public void setStrength(int a){
		strength = a;
	}
	public int getStrength(){
		return strength;
	}
	/**
	 * Get charmed*/
	public void setSleepTime(int s){
		sleepTime = s;
	}
	public int getSleepTime(){
		return sleepTime;
	}
	/**
	 * this is to get the possibility that this actor will succeed in making an attack. It will be compared with the defend point of the actor that it attacks. If attack point > defend point, the attack is successful. The damage is from getDamagePoint().*/
	public int getAttackPoint(){
		return (int)(Math.random() * (dexterity + myWeapon.getWeaponDexterityBonus()));
	}
	/**
	 * This is to get the possibility that this actor will succeed in making a defence. It will be compared with the attack point of the actor that attacks it. If attack point > defend point, the attack is successful. The damage is from getDamagePoint().*/
	public int getDefendPoint(){
		return (int)(Math.random() * (dexterity + armor));
	}
	public int getDamagePoint(){
		return strength + myWeapon.getWeaponDamageBonus();
	}
	/**
	 * Current version: simply drop the weapon of that Actor. Other characteristics will be developed in subclasses.*/
	public void dead(){
		getWeapon().setUnder(getUnder());
		getDungeon().dungeon[getRow()][getCol()] = getWeapon();
	}
	/**
	 * Make movement. Even if this method is called and a direction is given, it still cannot move if there are other actors or obstacles on the path.*/
	protected void move(Types.DirectionType direction){
		int nextRow = getRow(), nextCol = getCol();
		switch(direction){
		case DIRECTIONTYPE_NORTH:
			if(nextRow > 1
					&& getDungeon().getType(nextRow - 1, nextCol) != Types.UnitType.UNITTYPE_WALL
					&& getDungeon().getType(nextRow - 1, nextCol) != Types.UnitType.UNITTYPE_ACTOR) nextRow--;
			break;
		case DIRECTIONTYPE_EAST:
			if(nextCol < Types.SIZE_COL - 2
					&& getDungeon().getType(nextRow, nextCol + 1) != Types.UnitType.UNITTYPE_WALL
					&& getDungeon().getType(nextRow, nextCol + 1) != Types.UnitType.UNITTYPE_ACTOR) nextCol++;
			break;
		case DIRECTIONTYPE_SOUTH:
			if(nextRow < Types.SIZE_ROW - 2
					&& getDungeon().getType(nextRow + 1, nextCol) != Types.UnitType.UNITTYPE_WALL
					&& getDungeon().getType(nextRow + 1, nextCol) != Types.UnitType.UNITTYPE_ACTOR) nextRow++;
			break;
			default:
				if(nextCol > 1
						&& getDungeon().getType(nextRow, nextCol - 1) != Types.UnitType.UNITTYPE_WALL
						&& getDungeon().getType(nextRow, nextCol - 1) != Types.UnitType.UNITTYPE_ACTOR) nextCol--;
				break;
		}
		if(nextRow != getRow() || nextCol != getCol()){
			getDungeon().dungeon[getRow()][getCol()] = getUnder();
			setUnder(getDungeon().dungeon[nextRow][nextCol]);
			getDungeon().dungeon[nextRow][nextCol] = this;
			setRow(nextRow);
			setCol(nextCol);
		}
	}
	private Types.ActorType myActorType;
	private Dungeon _dungeon;
	private Weapon myWeapon;
	private int row;
	private int col;
	private int lifePoint;
	private int armor;
	private int dexterity;
	private int strength;
	private int sleepTime;
}

