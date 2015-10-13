package Weapon;
import GamePlay.Actor;
import GamePlay.Types;
import GamePlay.Weapon;
public class Sword extends Weapon{
	public Sword(){
		super(Types.WeaponType.WEAPONTYPE_SWORD);
		setWeaponDexterityBonus(7);
		setWeaponDamageBonus(8);
		setAttackRange(1);
	}
	public void specialEffect(Actor a, Actor b){
		
	}
}
