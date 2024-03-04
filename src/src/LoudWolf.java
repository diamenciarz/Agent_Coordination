import java.util.List;
import java.util.Random;
import Howl;

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

    public boolean createHowl(List<int[]> preysSight, List<Howl> howlsHeard) {
        // Get strongest howl
        // if strongest howl has a howlStrength

        return null;
    }

    // howlsHeard position will be relative to wolf
    public int[] getStrongestHowl(List<Howl> howlsHeard) {
        Howl strongestHowl = null;
        int strongestHowlStrength = 0;
        for(i=0; i<howlsHeard.size(); i++) {
            int newHowlStrength = getHowlStrength(howlsHeard.get(i));
            if (strongestHowl==null || newHowlStrength > strongestHowlStrength) {
                strongestHowlStrength = newHowlStrength;
                strongestHowl = howlsHeard.get(i);
            }
        }
        return strongestHowl;
    }

    public int getHowlStrength(Howl howlHeard) {
        coordDist = howlHeard.getPosition();
        xDist = abs(coordDist[0]);
        yDist = abs(coordDist[1]);
        dist = max(xDist, yDist);
        strength = howlHeard.getLoudness() - dist;
        if(strength < 0) {
            strength = 0;
        }
        return strength;
    }
}
