package Facility;
/**
 * @author Sen
 * Every time calling any method in this class, remember to reset the message.*/
import java.util.*;

import GamePlay.*;

public class CMDShow {
	/**
	 * Clear command-line window, and give the first frame according to current Dungeon and Player.*/
	public void init(){
		updateFrame();
	}
	/**
	 * update current command-line demonstration according to current data manager.*/
	public void updateFrame(){
		clearScreen();
		int row = Types.SIZE_ROW;
		int col = Types.SIZE_COL;
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				String res = "?";
				if(_dataManager.isLabelOn(i, j)){
					System.out.print(Types.LABEL_ON);
					continue;
				}
				switch(_dataManager.getUnitType(i, j)){
				case UNITTYPE_WALL:
					res = "#";
					break;
				case UNITTYPE_ROOM:
				case UNITTYPE_HALLWAY:
					res = " ";
					break;
				case UNITTYPE_NEXT:
					res = "+";
					break;
				case UNITTYPE_NONACTOR:
					switch(_dataManager.getNonActorType(i, j)){
					case NONACTORTYPE_WEAPON:
						switch(_dataManager.getWeaponType(i, j)){
						case WEAPONTYPE_AXE:
							res = "[";
							break;
						case WEAPONTYPE_SWORD:
							res = "(";
							break;
						case WEAPONTYPE_WARD:
							res = "{";
							break;
						case WEAPONTYPE_HAMMER:
							res = "<";
							break;
							default:
								break;
						}
						break;
					case NONACTORTYPE_SCROLL:
						switch(_dataManager.getScrollType(i, j)){
						case SCROLLTYPE_HEALTH:
							res = "h";
							break;
						case SCROLLTYPE_DEXTERITY:
							res = "d";
							break;
						case SCROLLTYPE_STRENGTH:
							res = "s";
							break;
						case SCROLLTYPE_LEVEL:
							res = "l";
							break;
						case SCROLLTYPE_INVISIBLE:
							res = "i";
							break;
							default:
								break;
						}
						break;
						default:
							break;
					
					}
					break;
				case UNITTYPE_ACTOR:
					switch(_dataManager.getActorType(i, j)){
					case ACTORTYPE_PLAYER:
						res = "I";
						break;
					case ACTORTYPE_PLAYER_DEAD:
						res = "X";
						break;
					case ACTORTYPE_MONSTER:
						Types.MonsterType m = _dataManager.getMonsterType(i, j);
						switch(m){
						case MONSTERTYPE_SNAKEWOMAN:
							res = "S";
							break;
						case MONSTERTYPE_GOBLIN:
							res = "G";
							break;
						case MONSTERTYPE_BOGEYMAN:
							res = "B";
							break;
							default:
								res = "D";
								break;
						}
						break;
					default:
						break;
					}
					break;
				default:
					res = "V";
					break;
				}
				System.out.print(res);
			}
			System.out.println("");
		}
		Types.WeaponType wt = _dataManager._dungeon.player.getWeapon().getWeaponType();
		String weapon = wt==Types.WeaponType.WEAPONTYPE_AXE? "axe" : wt==Types.WeaponType.WEAPONTYPE_HAMMER? "Hammer" : wt==Types.WeaponType.WEAPONTYPE_SWORD? "Sword": "Ward";
		System.out.println("Lv." + _dataManager._dungeon.player.getLevel() 
				+ " Life: " + _dataManager._dungeon.player.getLifePoint() 
				+ " Dexterity: " + _dataManager._dungeon.player.getDexterity()
				+ " Strength: " + _dataManager._dungeon.player.getStrength()
				+ " Weapon: " + weapon
				+ " Exp: " + _dataManager._dungeon.player.getExperience());
		if(message != null) System.out.println(message);
	}
	/**
	 * Will be called by Player's seeInventory() function to show inventory on screen.*/
	public int showInventory(ArrayList<NonActor> list){
		clearScreen();
		System.out.println("INVENTORY LIST:");
		for(int i = 1; i <= Types.INVENTORY_CAPACITY; i++){
			String noun = new String();
			noun = Integer.toString(i);
			noun += ". ";
			if(i - 1 < list.size()){
				switch(list.get(i - 1).getNonActorType()){
				case NONACTORTYPE_WEAPON:
					Weapon w = (Weapon)list.get(i - 1);
					switch(w.getWeaponType()){
					case WEAPONTYPE_AXE:
						noun += "Axe";
						break;
					case WEAPONTYPE_SWORD:
						noun += "Sword";
						break;
					case WEAPONTYPE_WARD:
						noun += "Ward";
						break;
					case WEAPONTYPE_HAMMER:
						noun += "Hammer";
						break;
						default:
							break;
					}
					break;
				case NONACTORTYPE_SCROLL:
					Scroll s = (Scroll)list.get(i - 1);
					switch(s.getScrollType()){
					case SCROLLTYPE_HEALTH:
						noun += "Health Scroll";
						break;
					case SCROLLTYPE_DEXTERITY:
						noun += "Dexterity Scroll";
						break;
					case SCROLLTYPE_STRENGTH:
						noun += "Strength Scroll";
						break;
					case SCROLLTYPE_LEVEL:
						noun += "Level Scroll";
						break;
					case SCROLLTYPE_INVISIBLE:
						noun += "Invisible Scroll";
						break;
						default:
							break;
					}
					break;
					default:
						break;
				}
			}
			System.out.print(noun);
			for(int j = 0; j < 32 - noun.length(); j++){
				System.out.print(" ");
			}
			if(i % 2 == 0) System.out.println();
		}
		System.out.println("Choose the item to use, or type q to return");
		System.out.println(message);
		String line = ReadInput.getLine();
		if(line.charAt(0) == 'q'){
			return -1;// quit inventory list;
		}
		else{
			int res = Integer.parseInt(line);
			if(res - 1 >= list.size()){
				message = "This is Empty Slot! Input again.";
				return showInventory(list);
			}
			return res - 1;
		}
	}
	public void setDataManager(DataManager d){
		_dataManager = d;
	}
	private void clearScreen(){// valid in linux commandline
		String esc = "\033[";
		System.out.print(esc + "2J"); 
	}
	private DataManager _dataManager;
	public String message;
}
