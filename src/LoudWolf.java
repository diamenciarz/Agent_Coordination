import java.util.List;
import java.util.Random;

public class LoudWolf implements Wolf {

    enum Behavior {
        MOVE_RANDOMLY,
        FOLLOW_PREY,
        FOLLOW_HOWL,
        MOVE_TO_WOLF
    }

    private Random r = new Random();
    private Behavior behavior = Behavior.MOVE_RANDOMLY;
    /**
     * Keeps previous behavior for a few turns
     */
    private int keepPreviousBehavior = 0;
    private int keepRandomDirection = 0;
    private int[] randomDirection = new int[2];
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

        behavior = selectBehavior(seesPrey, hearsHowl, wolvesSight);
        if (keepPreviousBehavior > 0) {
            keepPreviousBehavior--;
        }
        if (keepRandomDirection > 0) {
            keepRandomDirection--;
        }

        int[] myMove = doMove(closestPreyPos, closestHowl, wolvesSight);

        boolean createHowl = behavior == Behavior.FOLLOW_PREY;
        return new WolfAction(myMove, createHowl, closestPreyPos);
    }

    private int[] doMove(int[] closestPreyPos, Howl closestHowl, List<int[]> wolvesSight) {
        int[] myMove = null;
        if (behavior == Behavior.FOLLOW_PREY) {
            if (closestPreyPos == null) {
                System.err.println("This is an error, closestPreyPos should not be null here!");
            }
            myMove = moveToCoordinates(closestPreyPos);
        } else if (behavior == Behavior.FOLLOW_HOWL) {
            myMove = moveToCoordinates(closestHowl.getPosition());
        } else if (behavior == Behavior.MOVE_RANDOMLY) {
            myMove = moveRandom();
        } else if (behavior == Behavior.MOVE_TO_WOLF) {
            myMove = moveToClosestWolf(wolvesSight);
        }
        return myMove;
    }

    private int[] moveToClosestWolf(List<int[]> wolvesSight) {
        int[] closestWolfPos = findClosestWolf(wolvesSight);
        return moveToCoordinates(closestWolfPos);
    }

    public int[] findClosestWolf(List<int[]> wolvesSight) {
        int[] closestDeltaPosition = null;
        int closestDistance = 1000000;

        for (int[] wolfPos : wolvesSight) {
            int distanceToWolf = chebyshevDistance(wolfPos);
            // Skip myself
            if (distanceToWolf==0) {
                continue;
            }
            if (distanceToWolf < closestDistance) {
                closestDeltaPosition = wolfPos;
                closestDistance = distanceToWolf;
            }
        }
        return closestDeltaPosition;
    }

    private int[] moveToCoordinates(int[] coordinates) {
        if (coordinates == null) {
            return moveRandom();
        }

        int[] myMove = { 0, 0 };
        if (coordinates[0] < 0) {
            myMove[0] = -1;
        } else if (coordinates[0] > 0) {
            myMove[0] = 1;
        }
        if (coordinates[1] < 0) {
            myMove[1] = -1;
        } else if (coordinates[1] > 0) {
            myMove[1] = 1;
        }
        return myMove;
    }

    public int[] moveRandom() {
        if (keepRandomDirection <= 0) {
            randomDirection = new int[] { 0, 0 };
            // Ensure that the random movement is not standing in place
            while (randomDirection[0] == 0 && randomDirection[1] == 0) {
                randomDirection = new int[2];
                // Random value from -1 to 1
                randomDirection[0] = r.nextInt(3) - 1;
                randomDirection[1] = r.nextInt(3) - 1;
            }
            keepRandomDirection = r.nextInt(10, 20);
        }
        keepRandomDirection--;
        return randomDirection;
    }

    private Behavior selectBehavior(boolean seesPrey, boolean hearsHowl, List<int[]> wolvesSight) {
        // If was following
        if (behavior == Behavior.FOLLOW_HOWL && !hearsHowl) {
            keepPreviousBehavior = 0;
            keepRandomDirection = 0;
        }
        if (behavior == Behavior.FOLLOW_PREY && !seesPrey) {
            keepPreviousBehavior = 0;
            keepRandomDirection = 0;
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
                    // Sometimes, one wolf will choose to come closer to another wolf
                    if (r.nextDouble(1) < 0.5) {
                        keepPreviousBehavior = 10;
                        return Behavior.MOVE_RANDOMLY;
                    } else {
                        int[] closestWolfPos = findClosestWolf(wolvesSight);
                        keepPreviousBehavior = manhattanDistance(closestWolfPos);
                        return Behavior.MOVE_TO_WOLF;
                    }
                    // If does not hear any howls nor sees prey, just keep moving randomly
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

    private int manhattanDistance(int[] deltaPos) {
        return deltaPos[0] + deltaPos[1];
    }

    // howlsHeard position will be relative to wolf
    public Howl getStrongestHowl(List<Howl> howlsHeard) {
        Howl strongestHowl = null;
        // With this, the wolves have unlimited hearing range.
        int strongestHowlStrength = -100;
        for (int i = 0; i < howlsHeard.size(); i++) {
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
        if (strength < 0) {
            strength = 0;
        }
        return strength;
    }
}
