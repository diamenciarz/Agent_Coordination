import java.util.List;
import java.util.Random;

public class LoudWolf implements Wolf {

    enum Behavior {
        MOVE_RANDOMLY,
        FOLLOW_PREY,
        FOLLOW_HOWL
    }

    private Random r = new Random();
    private Behavior behavior = Behavior.MOVE_RANDOMLY;
    /**
     * Keeps previous behavior for a few turns
     */
    private int keepPreviousBehavior = 0;
    private double followPreyChance;

    public LoudWolf(double initialFollowPreyChance) {
        followPreyChance = initialFollowPreyChance;
    }

    @Override
    public WolfAction moveAll(List<int[]> wolvesSight, List<int[]> preysSight, List<Howl> howls) {
        int[] closestPreyPos = findClosestPrey(preysSight);
        boolean seesPrey = closestPreyPos != null;

        Howl closestHowl = getStrongestHowl(howls);
        boolean hearsHowl = closestHowl != null;

        behavior = selectBehavior(seesPrey, hearsHowl);
        if (keepPreviousBehavior>0) {
            keepPreviousBehavior--;
        }

        boolean createHowl = behavior == Behavior.FOLLOW_PREY;
        int[] myMove = null;
        if(behavior == Behavior.FOLLOW_PREY) {
            if(closestPreyPos==null){
                System.err.println("This is an error, closestPreyPos should not be null here!");
            }
            myMove = moveToCoordinates(closestPreyPos);
        }
        else if(behavior == Behavior.FOLLOW_HOWL) {
            myMove = moveToCoordinates(closestHowl.getPosition());
        }
        else if(behavior == Behavior.MOVE_RANDOMLY) {
            myMove = moveRandom();
        }

        return new WolfAction(myMove, createHowl, closestPreyPos);
    }

    private int[] moveToCoordinates(int[] coordinates) {
        if(coordinates == null) {
            return moveRandom();
        }
        
        int[] myMove = {0, 0};
        if(coordinates[0] < 0){
            myMove[0] = -1;
        } else if(coordinates[0] > 0) {
            myMove[0] = 1;
        }
        if(coordinates[1] < 0){
            myMove[1] = -1;
        } else if(coordinates[1] > 0) {
            myMove[1] = 1;
        }
        return myMove;
    }

    public int[] moveRandom() {
        Random r = new Random();
        int[] myMove = new int[2];
        // Random value from -1 to 1
        myMove[0] = r.nextInt(3)-1;
        myMove[1] = r.nextInt(3)-1;
        return myMove;
    }

    private Behavior selectBehavior(boolean seesPrey, boolean hearsHowl) {
        // If was following 
        if (behavior==Behavior.FOLLOW_HOWL && !hearsHowl) {
            keepPreviousBehavior = 0;
        }
        if (behavior==Behavior.FOLLOW_PREY && !seesPrey) {
            keepPreviousBehavior = 0;
        }
        // If it is still executing previous behavior, do not modify anything
        if (keepPreviousBehavior <= 0) {
            if (seesPrey) {
                if (hearsHowl) {
                    // If sees prey and hears howl, can decide to follow either
                    if (r.nextDouble(1) < followPreyChance) {
                        keepPreviousBehavior = 10;
                        return Behavior.FOLLOW_PREY;
                    } else {
                        keepPreviousBehavior = 10;
                        return Behavior.FOLLOW_HOWL;
                    }
                } else {
                    // If only sees prey but doesn't hear any howls, follow prey and keep howling as
                    // before
                    return Behavior.FOLLOW_PREY;
                }
            } else {
                // If does not see prey and hears howl, set mode to FollowingHowl
                if (hearsHowl) {
                    keepPreviousBehavior = 10;
                    return Behavior.FOLLOW_HOWL;
                } else {
                    // If does not hear any howls nor sees prey, just keep moving randomly
                    return Behavior.MOVE_RANDOMLY;
                }
            }
        }
        return behavior;
    }

    @Override
    public int moveLim(List<int[]> wolvesSight, List<int[]> preysSight) {
        return 0;
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
    }

    private int chebyshevDistance(int[] deltaPos) {
        return Math.max(deltaPos[0], deltaPos[1]);
    }

    // howlsHeard position will be relative to wolf
    public Howl getStrongestHowl(List<Howl> howlsHeard) {
        Howl strongestHowl = null;
        int strongestHowlStrength = 0;
        for(int i=0; i<howlsHeard.size(); i++) {
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
        int xDist = Math.abs(coordDist[0]);
        int yDist = Math.abs(coordDist[1]);
        int dist = Math.max(xDist, yDist);
        int strength = howlHeard.getLoudness() - dist;
        if(strength < 0) {
            strength = 0;
        }
        return strength;
    }
}
