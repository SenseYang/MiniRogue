package Monster;
import GamePlay.Dungeon;
import GamePlay.Monster;
import GamePlay.Types;
public class Goblin extends Monster{
	public Goblin(Dungeon theDungeon){
		super(Types.MonsterType.MONSTERTYPE_GOBLIN, theDungeon);
		setLifePoint(50);
		setArmor(5);
		setDexterity(15);
		setStrength(10);
		setWeapon(Types.WeaponType.WEAPONTYPE_SWORD);
		setSmellRange(20);
	}
}
