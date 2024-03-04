import java.util.List;
import java.util.Random;

public class LoudWolf implements Wolf {

    private Random r = new Random();
    public int howlLoudness;

    public LoudWolf(int visibility) {
        howlLoudness = r.nextInt(visibility * 3);
    }

    @Override
    public int[] moveAll(List<int[]> wolvesSight, List<int[]> preysSight, List<Howl> howls) {
        return null;
        // If sees prey, create howl

    }

    @Override
    public int moveLim(List<int[]> wolvesSight, List<int[]> preysSight) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveLim'");
    }

    public int createHowl(List<int[]> preysSight) {
        return 0;
    }

    public int[] strongestHowl(List<int[]> howlsHeard) {
        


        // Exclude own howl
        // Calculate closest based on number of moves, not actual distance
        // So if relative distance = (4, -7), total distance = 7 (since can move diagonal)
        // So max of absolute relative distance
        return new int[0];
    }
}
