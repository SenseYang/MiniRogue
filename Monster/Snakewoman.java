package Monster;
import GamePlay.Dungeon;
import GamePlay.Monster;
import GamePlay.Types;
public class Snakewoman extends Monster{
	public Snakewoman(Dungeon theDungeon){
		super(Types.MonsterType.MONSTERTYPE_SNAKEWOMAN, theDungeon);
		setLifePoint(70);
		setArmor(5);
		setDexterity(10);
		setStrength(5);
		setWeapon(Types.WeaponType.WEAPONTYPE_WARD);
		setSmellRange(15);
	}
}
