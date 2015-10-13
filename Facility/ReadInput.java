package Facility;
import GamePlay.*;
import java.util.*;
public class ReadInput {
	public static Scanner _scanner;
	public static char getKey(){
		String line = _scanner.nextLine();
		//scanner.close();
		return line.charAt(0);
	}
	public static String getLine(){
		return _scanner.nextLine();
	}
	public static Types.ActionType getActionFromKeyboard(){
		String line = _scanner.nextLine();
		Types.ActionType res; 
		switch(line){
		case "w"://up
			res = Types.ActionType.ACTIONTYPE_MOVEUP;
			break;
		case "a"://left
			res = Types.ActionType.ACTIONTYPE_MOVELEFT;
			break;
		case "s"://south
			res = Types.ActionType.ACTIONTYPE_MOVEDOWN;
			break;
		case "d"://right
			res = Types.ActionType.ACTIONTYPE_MOVERIGHT;
			break;
		case "p"://pickup
			res = Types.ActionType.ACTIONTYPE_PICKUP;
			break;
		case "f"://fire!
			res = Types.ActionType.ACTIONTYPE_ATTACK;
			break;
		case "q"://quit
			res = Types.ActionType.ACTIONTYPE_QUIT;
			break;
		case "i"://see inventory, call responding function in Player
			res = Types.ActionType.ACTIONTYPE_INVENTORY;
			break;
		case "c"://cheat
			res = Types.ActionType.ACTIONTYPE_CHEAT;
			break;
			default:// bad input
				System.out.println("Bad input. Please give order again!");
				res = getActionFromKeyboard();
				break;
		}
		//scanner.close();
		return res;
	}
	public static Types.ActionType getActionFromMouse(){
		return Types.ActionType.ACTIONTYPE_ATTACK;
	}
}
