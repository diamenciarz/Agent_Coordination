import java.util.ArrayList;
import java.util.List;

public class Print {
    public static void main(String[] args) {
        List<Howl> howls = new ArrayList<>();
        howls.add(new Howl(new int[]{2,5}, 10));
        howls.add(new Howl(new int[]{5,10}, 9));
        System.out.println(howls.getFirst());
        System.out.println(howls.get(1));

        List<Howl> relativeHowls = makeRelativeHowls(new int[]{5,5}, howls);
        System.out.println(relativeHowls.get(0));
        System.out.println(relativeHowls.get(1));
        
    }

    private static List<Howl> makeRelativeHowls(int[] wolfPosition, List<Howl> howls){
        List<Howl> relativeHowls = new ArrayList<>();
        for (Howl howl : howls) {
            int deltaX = howl.getPosition()[0]-wolfPosition[0];
            int deltaY = howl.getPosition()[1]-wolfPosition[1];
            int[] relativePosition = new int[]{deltaX, deltaY};
            relativeHowls.add(new Howl(relativePosition, howl.getLoudness()));
        }
        return relativeHowls;
    }

    private static String getPosString(int[] pos) {
        int x = pos[0];
        int y = pos[1];
        if (x > 0 && y > 0) {
            return String.format("(+%d,+%d)", x, y);
        } else if (x > 0 && y < 0) {
            return String.format("(+%d,%d)", x, y);
        } else if (x < 0 && y > 0) {
            return String.format("(%d,+%d)", x, y);
        } else {
            return String.format("(%d,%d)", x, y);
        }
    }

    public static void printWolfSight(List<int[]> sight) {
        System.out.print("Wolf sight: ");
        for (int[] s : sight) {
            System.out.print(getPosString(s) + " ");
        }
        System.out.println("");
    }

    public static void printPreySight(List<int[]> sight) {
        System.out.print("Prey sight: ");
        for (int[] s : sight) {
            System.out.print(getPosString(s) + " ");
        }
        System.out.println("");
    }
}
