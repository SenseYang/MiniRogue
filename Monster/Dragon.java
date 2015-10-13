package Monster;
import GamePlay.*;
public class Dragon extends Monster{
	public Dragon(Dungeon theDungeon){
		super(Types.MonsterType.MONSTERTYPE_DRAGON, theDungeon);
		setLifePoint(300);
		setArmor(30);
		setDexterity(5);
		setStrength(50);
		setWeapon(Types.WeaponType.WEAPONTYPE_HAMMER);
		setSmellRange(10);
	}
}
