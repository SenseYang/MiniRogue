package Scroll;
import GamePlay.Actor;
import GamePlay.Facilities;
import GamePlay.Player;
import GamePlay.Scroll;
import GamePlay.Types;
public class LevelScroll extends Scroll{
	public LevelScroll(){
		super(Types.ScrollType.SCROLLTYPE_LEVEL);
	}
	public Types.GameStatus takeEffect(Actor a){
		Player p = (Player) a;
		p.addExp(100);
		Facilities._drawer.message += "You use Levelup scroll.\n";
		return Types.GameStatus.GAMESTATUS_UNDETERMINED;
	}
}
