package Monster;
import GamePlay.*;
public class Bogeyman extends Monster{
	public Bogeyman(Dungeon theDungeon){
		super(Types.MonsterType.MONSTERTYPE_BOGEYMAN, theDungeon);
		setLifePoint(100);
		setArmor(20);
		setDexterity(10);
		setStrength(25);
		setWeapon(Types.WeaponType.WEAPONTYPE_AXE);
		setSmellRange(10);
	}
}
