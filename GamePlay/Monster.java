package GamePlay;
import java.util.*;
public abstract class Monster extends Actor{
	public Monster(Types.MonsterType aType, Dungeon theDungeon){
		super(Types.ActorType.ACTORTYPE_MONSTER, theDungeon);
		monsterType = aType;
	}
	public Types.MonsterType getMonsterType(){
		return monsterType;
	}
	public int getSmellRange(){
		return smellRange;
	}
	public void setSmellRange(int s){
		smellRange = s;
	}
	/**
	 * Defines the monsters' action.
	 * If the player is within monster's attack range, attack the player;
	 * if not, then see if the monster can smell the player. If yes, give the direction and make movement;
	 * if not, do not move.*/
	public Types.GameStatus action(){
		if(getSleepTime() > 0){
			setSleepTime(getSleepTime() - 1);
			return Types.GameStatus.GAMESTATUS_UNDETERMINED;
		}
		if(canAttack()){
			return attack();
		}
		Types.DirectionType nextDirection = canSmell();
		if(nextDirection != Types.DirectionType.DIRECTIONTYPE_NONE){
			move(nextDirection);
		}
		return Types.GameStatus.GAMESTATUS_UNDETERMINED;
	}
	/**
	 * Determines if the monster can smell the playe.
	 * Different monster has different smell range. If the player is in the range of monster, return the direction. 
	 * If not, return Types.DirectionType.DIRECTIONTYPE_NONE, and the monster will not move.*/
	public Types.DirectionType canSmell(){
		// this method consists of two parts: 1. determine if can smell; 2. if can smell, which direction to go.
		// use BFS. 
		int myRow = getRow(), myCol = getCol(), targetRow = getDungeon().getPlayer().getRow(), targetCol = getDungeon().getPlayer().getCol(); 
		int [][] table = new int[Types.SIZE_ROW][Types.SIZE_COL];
		for(int i = 0; i < Types.SIZE_ROW; i++){
			for(int j = 0; j < Types.SIZE_COL; j++){
				table[i][j] = smellRange + 1;
			}
		}
		table[myRow][myCol] = 0;
		int counter = 1;
		Stack<Point> prev = new Stack<Point>();
		Stack<Point> next = new Stack<Point>();
		prev.push(new Point(myRow - 1, myCol));
		prev.push(new Point(myRow, myCol + 1));
		prev.push(new Point(myRow + 1, myCol));
		prev.push(new Point(myRow, myCol - 1));
		boolean found = false;
		while(counter <= smellRange && !found){
			while(!prev.empty() && !found){
				Point cur = prev.peek();
				if(cur.row < 1 || cur.col < 1 || cur.row > Types.SIZE_ROW - 2 || cur.col > Types.SIZE_COL - 2) prev.pop();
				else if(getDungeon().getType(cur.row, cur.col) == Types.UnitType.UNITTYPE_WALL) prev.pop();
				else if(getDungeon().getType(cur.row, cur.col) == Types.UnitType.UNITTYPE_ACTOR){
					if(getDungeon().getActorType(cur.row, cur.col) == Types.ActorType.ACTORTYPE_PLAYER){
						// target found
						found = true;
						table[cur.row][cur.col] = counter;
					}
					else prev.pop();// meet a monster, cannot fuck it
				}
				else if(table[cur.row][cur.col] != smellRange + 1) prev.pop();
				else{// room or hallway, only push in surrounding points that has value > current
					table[cur.row][cur.col] = counter;
					int nextrow = cur.row - 1, nextcol = cur.col;
					if(table[nextrow][nextcol] > counter) next.push(new Point(nextrow, nextcol));
					nextrow = cur.row; nextcol = cur.col + 1;
					if(table[nextrow][nextcol] > counter) next.push(new Point(nextrow, nextcol));
					nextrow = cur.row + 1; nextcol = cur.col;
					if(table[nextrow][nextcol] > counter) next.push(new Point(nextrow, nextcol));
					nextrow = cur.row; nextcol = cur.col - 1;
					if(table[nextrow][nextcol] > counter) next.push(new Point(nextrow, nextcol));
				}
			}
			if(next.empty()) counter = smellRange;
			prev = next;
			next = new Stack<Point>();
			counter++;
		}
		if(found){// reverse the level gradient, search uphill
			int prevRow = targetRow, prevCol = targetCol;
			while(table[prevRow][prevCol] != 1){
				int tempRow = prevRow, tempCol = prevCol;
				if(table[prevRow - 1][prevCol] < table[tempRow][tempCol]){
					tempRow = prevRow - 1; tempCol = prevCol;
				}
				if(table[prevRow][prevCol + 1] < table[tempRow][tempCol]){
					tempRow = prevRow; tempCol = prevCol + 1;
				}
				if(table[prevRow + 1][prevCol] < table[tempRow][tempCol]){
					tempRow = prevRow + 1; tempCol = prevCol;
				}
				if(table[prevRow][prevCol - 1] < table[tempRow][tempCol]){
					tempRow = prevRow; tempCol = prevCol - 1;
				}
				prevRow = tempRow; prevCol = tempCol;
			}
			return prevRow < myRow ? Types.DirectionType.DIRECTIONTYPE_NORTH
					: prevRow > myRow ? Types.DirectionType.DIRECTIONTYPE_SOUTH
							:prevCol < myCol ? Types.DirectionType.DIRECTIONTYPE_WEST
									: Types.DirectionType.DIRECTIONTYPE_EAST;
		}
		else return Types.DirectionType.DIRECTIONTYPE_NONE;
	}
	
	/**
	 * Determine if the player is in the range of monster's attack*/
	public boolean canAttack(){
		return Math.abs(getRow() - getDungeon().getPlayer().getRow()) 
				+ Math.abs(getCol() - getDungeon().getPlayer().getCol()) <= getWeapon().getAttackRange() ? true : false;
	}
	/**
	 * */
	public Types.GameStatus attack(){
		// attack the player
		Player p = getDungeon().getPlayer();
		if(p.getInvisibleTime() >0) return Types.GameStatus.GAMESTATUS_UNDETERMINED;
		int attackPoint = getAttackPoint(), defendPoint = p.getDefendPoint();
		Types.MonsterType t = getMonsterType();
		if(attackPoint > defendPoint){
			int damage = getDamagePoint();
			getDungeon().getPlayer().setLifePoint(getDungeon().getPlayer().getLifePoint() - damage);
			if(Facilities._drawer.message == null) Facilities._drawer.message = new String();
			if(p.getLifePoint() <= 0){
				p.setMyActorType(Types.ActorType.ACTORTYPE_PLAYER_DEAD);
				Facilities._drawer.message += ("You are killed by " + 
						(t == Types.MonsterType.MONSTERTYPE_BOGEYMAN ? "Bogeyman" 
								: t == Types.MonsterType.MONSTERTYPE_DRAGON ? "Dragon" 
										: t == Types.MonsterType.MONSTERTYPE_GOBLIN ? "Goblin"
												: "Snakewoman") + ".\n");
				;
				return Types.GameStatus.GAMESTATUS_LOSE;
			}
			else{
				Facilities._drawer.message += ( 
						(t == Types.MonsterType.MONSTERTYPE_BOGEYMAN ? "Bogeyman" 
								: t == Types.MonsterType.MONSTERTYPE_DRAGON ? "Dragon" 
										: t == Types.MonsterType.MONSTERTYPE_GOBLIN ? "Goblin"
												: "Snakewoman")
												+ " give you " + damage + " points of damage.\n")
						;
				getWeapon().specialEffect(this, p);
			}
		}
		else{
			Facilities._drawer.message += ( 
					(t == Types.MonsterType.MONSTERTYPE_BOGEYMAN ? "Bogeyman" 
							: t == Types.MonsterType.MONSTERTYPE_DRAGON ? "Dragon" 
									: t == Types.MonsterType.MONSTERTYPE_GOBLIN ? "Goblin"
											: "Snakewoman")
											+ " missed its attack. \n")
					;
		}
		return Types.GameStatus.GAMESTATUS_UNDETERMINED;
	}
	private Types.MonsterType monsterType;
	/*The smell range of this monster*/
	private int smellRange;
}
class Point{
	public Point(int r, int c){
		row = r;
		col = c;
	}
	public int row;
	public int col;
}
