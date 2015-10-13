package GamePlay;
/**
 * @author Sen
 * This class is definition for weapon.
 * As for now, this class is enough for use. For further development, we can extend this class.*/
public abstract class Weapon extends NonActor {
	public Weapon(Types.WeaponType aWeaponType){
		super(Types.NonActorType.NONACTORTYPE_WEAPON);
		setWeaponType(aWeaponType);
		MAX_LIFE = 10;
		life = MAX_LIFE;// a new weapon
		// use a switch to determine the parameters for different weapons
	}
	public void setWeaponType(Types.WeaponType aType){
		myWeaponType = aType;
	}
	public Types.WeaponType getWeaponType(){
		return myWeaponType;
	}
	public int getWeaponDexterityBonus(){
		return weaponDexterityBonus;
	}
	public void setWeaponDexterityBonus(int dexterity){
		weaponDexterityBonus = dexterity;
	}
	public int getWeaponDamageBonus(){
		return weaponDamageBonus;
	}
	public void setWeaponDamageBonus(int damage){
		weaponDamageBonus = damage;
	}
	public void setAttackRange(int a){
		attackRange = a;
	}
	public int getAttackRange(){
		return attackRange;
	}
	/**
	 * Special effect of the weapon*/
	public abstract void specialEffect(Actor a, Actor b);
	// just for simplicity, set them all as 10
	private Types.WeaponType myWeaponType;
	private int weaponDexterityBonus;
	private int weaponDamageBonus;
	@SuppressWarnings("unused")
	private int life;
	private int MAX_LIFE;
	private int attackRange;
}
