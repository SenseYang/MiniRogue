package Weapon;
import GamePlay.Actor;
import GamePlay.Types;
import GamePlay.Weapon;
/**
 * @author Sen*/
public class Hammer extends Weapon{
	public Hammer(){
		super(Types.WeaponType.WEAPONTYPE_HAMMER);
		setWeaponDexterityBonus(3);
		setWeaponDamageBonus(12);
		setAttackRange(2);
	}
	public void specialEffect(Actor a, Actor b){
		
	}
}
