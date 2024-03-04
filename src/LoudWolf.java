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

    public int[] findClosestPrey(List<int[]> preysSight) {
        int[] closestDeltaPosition = null;
        int closestDistance = 1000000;

        for (int[] preyPos : preysSight) {
            int distanceToPrey = chebyshevDistance(preyPos);
            if (distanceToPrey < closestDistance) {
                closestDeltaPosition = preyPos;
                closestDistance = distanceToPrey;
            }
        }
        return closestDeltaPosition;


    public boolean createHowl(List<int[]> preysSight, List<Howl> howlsHeard) {
        boolean hearHowl = False;
        boolean seePrey = False;

        Howl strongestHowl = getStrongestHowl(howlsHeard);
        if(strongestHowl != null) {
            hearHowl = True;
        }

        int[] closestPrey = findClosestPrey(preysSight);
        if(closestPrey != null) {
            seePrey = True;
        }


        /*
        - if wolf hears howl and sees prey
            - howl and go to prey with x chance for y moves
            - go to howl with 1-x chance for y moves

        - if hears howls
            - go to loudest howl
            - if equal, go to first howl in list

        - if wolf sees prey
            - howl
            - move towards prey
         */


        return null;
    }



    // howlsHeard position will be relative to wolf
    public Howl getStrongestHowl(List<Howl> howlsHeard) {
        Howl strongestHowl = null;
        int strongestHowlStrength = 0;
        for(i=0; i<howlsHeard.size(); i++) {
            int newHowlStrength = getHowlStrength(howlsHeard.get(i));
            if (newHowlStrength > strongestHowlStrength) {
                strongestHowlStrength = newHowlStrength;
                strongestHowl = howlsHeard.get(i);
            }
        }
        return strongestHowl;
    }

    public int getHowlStrength(Howl howlHeard) {
        int[] coordDist = howlHeard.getPosition();
        int xDist = abs(coordDist[0]);
        int yDist = abs(coordDist[1]);
        int dist = max(xDist, yDist);
        int strength = howlHeard.getLoudness() - dist;
        if(strength < 0) {
            strength = 0;
        }
        return strength;
    }
}
