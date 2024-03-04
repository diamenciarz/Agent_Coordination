import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomWolf implements Wolf {

	@Override
	public int[] moveAll(List<int[]> wolvesSight, List<int[]> preysSight) {
		System.out.println();
		Random r = new Random();
		int[] mymove = new int[2];
		// Random value from -1 to 1
		mymove[0] = r.nextInt(3)-1;
		mymove[1] = r.nextInt(3)-1;
		return mymove;
	}
	
	public int moveLim(List<int[]> wolvesSight, List<int[]> preysSight) {
		Random r = new Random();
		// Random direction from 1 to 4
		return r.nextInt(4) + 1;
	}


}
