package GamePlay;
/**
 * @author Sen
 * This abstract class designs common characteristics of non-actors*/
public abstract class NonActor extends DungeonUnit{
	public NonActor(Types.NonActorType aType){
		super(Types.UnitType.UNITTYPE_NONACTOR);
		setNonActorType(aType);
		MAX_LIFEPOINT = 20;
	}
	public void setNonActorType(Types.NonActorType aType){
		myNonActorType = aType;
	}
	public Types.NonActorType getNonActorType(){
		return myNonActorType;
	}
	private Types.NonActorType myNonActorType;
	@SuppressWarnings("unused")
	private final int MAX_LIFEPOINT;
	// no need to know where it is
}
