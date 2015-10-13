package GamePlay;
/**@author Sen
 * */
import java.util.*;
public class DataManager {
	
	/**
	 * Initialization method for DataManager object. */
	public void init(){
		// parameters set, it can be modified as user defined value
		maxLevel = 5;
		Types.SIZE_ROW = 20;
		Types.SIZE_COL = 50;
		currentStatus = Types.GameStatus.GAMESTATUS_UNDETERMINED;
		_dungeon = new Dungeon(1);
		_dungeon._dataManager = this;
	}
	/**
	 * Get to the next level, when currentStatus == GAMESTATUS_NEXT. Remember to reset the status to undetermined when this method ends.*/
	public void nextLevel(){
		if(_dungeon.currentLevel < maxLevel)
			currentStatus = _dungeon.nextLevel();
	}
	/**
	 * Get DataManager.currentStatus*/
	public Types.GameStatus getStatus(){
		return currentStatus;
	}
	/**
	 * Handle player actions. It will call Player object's method to make player's move.
	 * Remember to check the status after all action is finished.
	 * Use another class ReadInput to handle this problem.*/
	public void playerAction(){
		currentStatus = _dungeon.getPlayer().action();
		determineStatus();
	}
	/**
	 * Handle Monster actions. It will call Monsters' methods to make each monster's move.
	 * Remember to check the status after all actions are finished.
	 * UNIMPLEMENTED*/
	public void monsterAction(){
		if(currentStatus == Types.GameStatus.GAMESTATUS_UNDETERMINED){
			ArrayList<Monster> monsterArray = _dungeon.monsterArray;
			for(int i = 0; i < monsterArray.size() && currentStatus != Types.GameStatus.GAMESTATUS_LOSE; i++){
				Monster m = monsterArray.get(i);
				currentStatus = m.action();
				determineStatus();
			}
		}
	}

	/**
	 * Determine current game status and save it in DataManager.currentStatus instance
	 * UNIMPLEMENTED*/
	private void determineStatus(){
		if(_dungeon.player.getLifePoint() <= 0) currentStatus = Types.GameStatus.GAMESTATUS_LOSE;
		// win and next, the status will be updated in Player::pickUp() function so no need to check here
	}
	
	// --------------------CMDShow methods----------------
	/**
	 * To communicate with CMDShow.updateFrame() and Dungeon.getType() methods*/
	public Types.UnitType getUnitType(int row, int col){
		return _dungeon.getType(row, col);
	}
	/**To communicate with CMDShow.updateFrame() and Dungeon.getActorType() methods
	 * */
	public Types.ActorType getActorType(int row, int col){
		return _dungeon.getActorType(row, col);
	}
	/**To communicate with CMDShow.updataFram() and Dungeon::getMonsterType() methods
	 * */
	public Types.MonsterType getMonsterType(int row, int col){
		return _dungeon.getMonsterType(row, col);
	}
	/**To communicate with CMDShow.updateFrame() and Dungeon.getNonActorType() methods
	 * */
	public Types.NonActorType getNonActorType(int row, int col){
		return _dungeon.getNonActorType(row, col);
	}
	/**To communicate with CMDShow.updateFrame() and Dungeon.getWeaponType() methods
	 * */
	public Types.WeaponType getWeaponType(int row, int col){
		return _dungeon.getWeaponType(row, col);
	}
	/**To communicate with CMDShow.updateFrame() and Dungeon.getScrollType() methods
	 * */
	public Types.ScrollType getScrollType(int row, int col){
		return _dungeon.getScrollType(row, col);
	}
	public boolean isLabelOn(int row, int col){
		return _dungeon.isLabelOn(row, col);
	}
	
	//----------------------Dungeon methods--------------
	
	// ------------------------------------instances----------------------------------------------------
	/**
	 * Current status of the game. It will be updated by calling DataManager.determineStatus() method.*/
	private Types.GameStatus currentStatus;
	/**
	 * Maximum dungeon level. It is set in DataManager.init() method.*/
	private int maxLevel;
	// player object reference
	public Dungeon _dungeon;
	// monster objects reference
	// constants
}
