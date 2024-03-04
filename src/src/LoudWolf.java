import java.util.List;
import java.util.Random;

public class LoudWolf implements Wolf {

    enum Behavior {
        MOVE_RANDOMLY,
        FOLLOW_PREY,
        FOLLOW_HOWL
    }

    private Random r = new Random();
    private int howlLoudness;
    private Behavior behavior = Behavior.MOVE_RANDOMLY;
    /**
     * Keeps previous behavior for a few turns
     */
    private int keepPreviousBehavior = 0;

    public LoudWolf(int visibility) {
        howlLoudness = r.nextInt(visibility * 3);
    }

    @Override
    public WolfAction moveAll(List<int[]> wolvesSight, List<int[]> preysSight, List<Howl> howls) {
        // By default, moves randomly
        boolean createdHowl = false;

        int[] closestPreyPos = findClosestPrey(preysSight);
        boolean seesPrey = closestPreyPos != null;
        boolean hearsHowl = false; // To do
        // If sees prey, create howl
        if (seesPrey) {
            createdHowl = true;
        }

        behavior = selectBehavior(seesPrey, hearsHowl);
        // Act on the selected behavior

        int[] myMove = makeMovement(wolvesSight, preysSight);
        return new WolfAction(myMove, createdHowl);
    }

    private Behavior selectBehavior(boolean seesPrey, boolean hearsHowl) {
        // If it is still executing previous behavior, do not modify anything
        if (keepPreviousBehavior == 0) {
            if (seesPrey) {
                if (hearsHowl) {
                    // If sees prey and hears howl, can decide to follow either
                    if (r.nextInt(100) > 50) {
                        keepPreviousBehavior = 5;
                        return Behavior.FOLLOW_HOWL;
                    } else {
                        keepPreviousBehavior = 5;
                        return Behavior.FOLLOW_PREY;
                    }
                } else {
                    // If only sees prey but doesn't hear any howls, follow prey and keep howling as
                    // before
                    return Behavior.FOLLOW_PREY;
                }
            } else {
                // If does not see prey and hears howl, set mode to FollowingHowl
                if (hearsHowl) {
                    keepPreviousBehavior = 5;
                    return Behavior.FOLLOW_HOWL;
                } else {
                    // If does not hear any howls nor sees prey, just keep moving randomly
                    return Behavior.MOVE_RANDOMLY;
                }
            }
        }
        return behavior;
    }

    private int[] makeMovement(List<int[]> wolvesSight, List<int[]> preysSight){
        return null;
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
    }

    private int chebyshevDistance(int[] deltaPos) {
        return Math.max(deltaPos[0], deltaPos[1]);
    }

    public int[] closestHowl(List<int[]> howlsHeard) {
        // Exclude own howl
        // Calculate closest based on number of moves, not actual distance
        // So if relative distance = (4, -7), total distance = 7 (since can move
        // diagonal)
        // So max of absolute relative distance
        return new int[0];
    }
}
