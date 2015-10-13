package Scroll;
import GamePlay.Actor;
import GamePlay.Facilities;
import GamePlay.Player;
import GamePlay.Scroll;
import GamePlay.Types;

public class InvisibleScroll extends Scroll{
	public InvisibleScroll(){
		super(Types.ScrollType.SCROLLTYPE_INVISIBLE);
	}
	public Types.GameStatus takeEffect(Actor a){
		Facilities._drawer.message += "You use Invisible scroll. The monsters cannot see you in next 5 turns, but they can still SMELL!\n";
		Player p = (Player) a;
		p.setInvisibleTime(5);
		return Types.GameStatus.GAMESTATUS_UNDETERMINED;
	}
}
