package GamePlay;
/**
 * @author Sen*/
import java.util.*;

import Facility.CMDShow;
import Facility.ReadInput;
public class GamePlay {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GamePlay g = new GamePlay();
		g.init();
		g.play();
	}
	/**
	 * Initialization of the whole game. The things it will do:
	 * 1. Create new objects for DataManager and CMDShow;
	 * 2. Call DataManager's initialization method;
	 * 3. Link CMDShow object with DataManager object*/
	public void init(){
		_dataManager = new DataManager();
		Facilities._drawer = new CMDShow();
		_dataManager.init();
		Facilities._drawer.setDataManager(_dataManager);
		Facilities._drawer.init();
		Facilities._reader = new ReadInput();
	}
	public void play(){
		ReadInput._scanner = new Scanner(System.in);
		Types.GameStatus currentStatus = _dataManager.getStatus();
		while(currentStatus == Types.GameStatus.GAMESTATUS_UNDETERMINED){
			Facilities._drawer.message = new String();
			_dataManager.playerAction();
			_dataManager.monsterAction();
			Facilities._drawer.updateFrame();
			currentStatus = _dataManager.getStatus();
			// if the current status is next, then it is still in the loop but with a new dungeon map
			if(currentStatus == Types.GameStatus.GAMESTATUS_NEXT){
				_dataManager.nextLevel();
				Facilities._drawer.updateFrame();
				currentStatus = _dataManager.getStatus();
			}
		}
		if(currentStatus == Types.GameStatus.GAMESTATUS_LOSE){
			System.out.println("You failed to win the game!");
		}
		else if(currentStatus == Types.GameStatus.GAMESTATUS_WIN){
			System.out.println("You WIN the game! The golden idol is YOURS!");
		}
		else{
			System.out.println("You choose to quit the game.");
		}
		System.out.println("Restart? If yes, press Y; if not, press any other keys.");
		String n = ReadInput.getLine();	
		if(n == "Y" || n == "y"){
			init();
			play();
		}
		ReadInput._scanner.close();
	}
	private DataManager _dataManager;
}
