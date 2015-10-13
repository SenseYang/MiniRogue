package Weapon;
import GamePlay.Actor;
import GamePlay.Types;
import GamePlay.Weapon;
/**
 * @author Sen*/
public class Axe extends Weapon{
	public Axe(){
		super(Types.WeaponType.WEAPONTYPE_AXE);
		setWeaponDexterityBonus(4);// axe is heavy and you cannot use it unless you have enough strength!
		setWeaponDamageBonus(10);
		setAttackRange(2);
	}
	public void specialEffect(Actor a, Actor b){
		
	}
}
