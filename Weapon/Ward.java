package Weapon;
import GamePlay.Actor;
import GamePlay.Facilities;
import GamePlay.Types;
import GamePlay.Weapon;
/**
 * @author Sen*/
/*
 * The magic effect of ward is UNIMPLEMENTED.*/
public class Ward extends Weapon{
	public Ward(){
		super(Types.WeaponType.WEAPONTYPE_WARD);
		setWeaponDexterityBonus(10);
		setWeaponDamageBonus(5);
		setAttackRange(5);
		maxSleepTime = 10;
	}
	
	public void specialEffect(Actor a, Actor b){
		makeSleep(a, b);
	}
	/**
	 * A magic ward has 30% to make an actor sleep.
	 * If taking effect, Actor b will have 2-6 turns of sleeping.*/
	private void makeSleep(Actor a, Actor b){
		int sleepTurns = Math.random() <= 0.1 ? (int)(Math.random() * 4 + 2) : 0;
		b.setSleepTime(Math.min(b.getSleepTime() + sleepTurns, maxSleepTime));
		String subject = a.getMyActorType() == Types.ActorType.ACTORTYPE_PLAYER ?
				"Your" : "Its";
		if(a.getMyActorType() != b.getMyActorType() && sleepTurns != 0){
			Facilities._drawer.message += (subject + " ward makes " + sleepTurns + " turns of sleep.\n");
		}
	}
	private int maxSleepTime;
}
