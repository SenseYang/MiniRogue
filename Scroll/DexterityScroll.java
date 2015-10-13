package Scroll;
import GamePlay.Actor;
import GamePlay.Facilities;
import GamePlay.Scroll;
import GamePlay.Types;
public class DexterityScroll extends Scroll{
	public DexterityScroll(){
		super(Types.ScrollType.SCROLLTYPE_DEXTERITY);
	}
	public Types.GameStatus takeEffect(Actor a){
		a.setDexterity(a.getDexterity() + amount);
		Facilities._drawer.message += "You use Dexterity scroll.\n";
		return Types.GameStatus.GAMESTATUS_UNDETERMINED;
	}
	private int amount = 5;
}
