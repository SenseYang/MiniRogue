package Scroll;
import GamePlay.Actor;
import GamePlay.Facilities;
import GamePlay.Scroll;
import GamePlay.Types;
public class HealthScroll extends Scroll{
	public HealthScroll(){
		super(Types.ScrollType.SCROLLTYPE_HEALTH);
	}
	public Types.GameStatus takeEffect(Actor a){
		a.setLifePoint(a.getLifePoint() + amount);
		Facilities._drawer.message += "You use Health scroll.\n";
		return Types.GameStatus.GAMESTATUS_UNDETERMINED;
	}
	/*
	 * The amount of increased health*/
	private int amount = 30;
}
