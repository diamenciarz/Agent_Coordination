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

}
