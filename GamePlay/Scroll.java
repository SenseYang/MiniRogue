package GamePlay;

public abstract class Scroll extends NonActor{
	public Scroll(Types.ScrollType aType){
		super(Types.NonActorType.NONACTORTYPE_SCROLL);
		scrollType = aType;
	}
	public Types.ScrollType getScrollType(){
		return scrollType;
	}
	/**
	 * The interface to take effect toward Actor object a*/
	public abstract Types.GameStatus takeEffect(Actor a);
	private Types.ScrollType scrollType;
}
