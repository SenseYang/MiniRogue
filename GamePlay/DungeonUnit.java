package GamePlay;
/**
 * @author Sen
 * Remember to specify the type of current DungeonUnit.*/
public class DungeonUnit {
	public DungeonUnit(Types.UnitType aType){
		myType = aType;
		under = null;
	}
	public Types.UnitType getMyType(){
		return myType;
	}
	public void setMyType(Types.UnitType aType){
		myType = aType;
	}
	public DungeonUnit getUnder(){
		return under;
	}
	public void setUnder(DungeonUnit aUnit){
		under = aUnit;
	}
	/**
	 * Record the type of current unit. See defined types in enum type UnitType.*/
	private Types.UnitType myType;
	/**
	 * A reference to link the unit under it, considering that actors may stand on non-actors*/
	private DungeonUnit under;
	/**
	 * If current label is on current unit*/
	boolean labelOn = false;
}
