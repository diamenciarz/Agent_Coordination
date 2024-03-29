import java.util.List;
import java.util.Random;

public class RandomWolf implements Wolf {

	@Override
	public WolfAction moveAll(List<int[]> wolvesSight, List<int[]> preysSight, List<Howl> howls) {
		System.out.println();
		Random r = new Random();
		int[] mymove = new int[2];
		// Random value from -1 to 1
		mymove[0] = r.nextInt(3)-1;
		mymove[1] = r.nextInt(3)-1;
		return new WolfAction(mymove, false, null);
	}
	
	public int moveLim(List<int[]> wolvesSight, List<int[]> preysSight) {
		Random r = new Random();
		// Random direction from 1 to 4
		return r.nextInt(4) + 1;
	}


}
