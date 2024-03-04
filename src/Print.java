import java.util.List;

public class Print {
    public static void main(String[] args) {
        // List<int[]> sight = new ArrayList<>();
        // sight.add(new int[] { 2, -2 });
        // sight.add(new int[] { 3, 1 });
        // printPreySight(sight);
        
    }

    private static String getPosString(int[] pos) {
        int x = pos[0];
        int y = pos[1];
        if (x > 0 && y > 0) {
            return String.format("(+%x,+%x)", x, y);
        } else if (x > 0 && y < 0) {
            return String.format("(+%x,%x)", x, y);
        } else if (x < 0 && y > 0) {
            return String.format("(%x,+%x)", x, y);
        } else {
            return String.format("(%x,%x)", x, y);
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
