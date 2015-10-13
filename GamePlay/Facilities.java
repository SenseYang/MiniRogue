package GamePlay;
/**
 * @author Sen
 * This class includes the objects of facility classes.*/
import Facility.CMDShow;
import Facility.ReadInput;
public class Facilities {
	public static CMDShow _drawer;
	public static ReadInput _reader;
	public static int getDistance(int row1, int col1, int row2, int col2){
		return (int)Math.sqrt(Math.pow(row1 - row2, 2) + Math.pow(col1 - col2, 2));
	}
}
