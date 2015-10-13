package Scroll;
import GamePlay.Actor;
import GamePlay.Facilities;
import GamePlay.Scroll;
import GamePlay.Types;
public class StrengthScroll extends Scroll{
	public StrengthScroll(){
		super(Types.ScrollType.SCROLLTYPE_STRENGTH);
	}
	public Types.GameStatus takeEffect(Actor a){
		a.setStrength(a.getStrength() + amount);
		Facilities._drawer.message += "You use Levelup scroll.\n";
		return Types.GameStatus.GAMESTATUS_UNDETERMINED;
	}
	private int amount = 5;
}
