import javax.swing.*;
import java.util.*;

public class Wolves {

    private int numWolves;
    private int numPreys;
    private int visibility;
    private int minCaptured;
    private int min_surround;
    public int[][] grid;
    private int rows, cols;
    private int[] wolfX = new int[numWolves];
    private int[] wolfY = new int[numWolves];
    private int[] preyX = new int[numPreys];
    private int[] preyY = new int[numPreys];
    private Wolf[] wolves = new Wolf[numWolves];
    private int[] howlLoudness;
    private List<Integer> capturedList = new ArrayList<>();
    private Random r = new Random();
    private WolvesUI visuals;
    private long tickcounter = 0;

    private static List<Howl> howls = new ArrayList<>();
    private static List<Howl> newHowls = new ArrayList<>();

    public Wolves(int rows, int cols, int numWolves, int numPreys, int visibility, int minCaptured, int min_surround) {
        this.rows = rows;
        this.cols = cols;
        this.numWolves = numWolves;
        this.numPreys = numPreys;
        this.visibility = visibility;
        this.minCaptured = minCaptured;
        this.min_surround = min_surround;
        grid = new int[rows][cols];
        howlLoudness = new int[numWolves];

        wolfX = new int[numWolves];
        wolfY = new int[numWolves];
        preyX = new int[numPreys];
        preyY = new int[numPreys];
        wolves = new Wolf[numWolves];
        howlLoudness = new int[numWolves];

        for (int i = 0; i < numWolves; i++) {
            do {
                wolfX[i] = r.nextInt(rows);
                wolfY[i] = r.nextInt(cols);
            } while (!empty(wolfX[i], wolfY[i]));
            grid[wolfX[i]][wolfY[i]] = i * 2 + 1;

            howlLoudness[i] = r.nextInt(10,21);
        }
        for (int i = 0; i < numPreys; i++) {

            int preyR;
            int preyC;
            do {
                preyR = r.nextInt(rows);
                preyC = r.nextInt(cols);
            } while (!empty(preyR, preyC)
                    || captured(preyR, preyC));
            preyX[i] = preyR;
            preyY[i] = preyC;
            grid[preyR][preyC] = i * 2 + 2;
        }
        initWolves();
    }

    private boolean empty(int row, int col) {
        return (grid[row][col] == 0);
    }

    private void initWolves() {
        // You should put your own wolves in the array here!!
        Wolf[] wolvesPool = new Wolf[5];
        double followPreyChance = 0.5;
        wolvesPool[0] = new LoudWolf(followPreyChance);
        wolvesPool[1] = new LoudWolf(followPreyChance);
        wolvesPool[2] = new LoudWolf(followPreyChance);
        wolvesPool[3] = new LoudWolf(followPreyChance);
        wolvesPool[4] = new LoudWolf(followPreyChance);

        // Below code will select three random wolves from the pool.
        // Make the pool as large as you want, but not < numWolves
        Set<Integer> generated = new LinkedHashSet<Integer>();
        while (generated.size() < numWolves) {
            Integer next = r.nextInt(wolvesPool.length);
            generated.add(next);
        }

        int i = 0;
        for (Integer index : generated) {
            wolves[i++] = wolvesPool[index];
        }
    }

    public void tick() {
        int[][] moves = new int[numWolves][2];

        int cntr = 0;
        for (int i = 0; i < numPreys; i++) {
            int rowMove, colMove;
            do {
                rowMove = r.nextInt(3) - 1;
                colMove = r.nextInt(3) - 1;
                cntr++;
            } while (!empty(rowWrap(preyX[i], rowMove), colWrap(preyY[i], colMove))
                    || (cntr < 100 && captured(rowWrap(preyX[i], rowMove), colWrap(preyY[i], colMove))));
            grid[preyX[i]][preyY[i]] = 0;
            preyX[i] = rowWrap(preyX[i], rowMove);
            preyY[i] = colWrap(preyY[i], colMove);
            grid[preyX[i]][preyY[i]] = i * 2 + 2;

        }

        // here we ask for the wolves moves; to change the movement style, change the limitMovement variable
        boolean limitMovement = false;

        int[][] safetyGrid;
        if (!limitMovement) {
            // Wolves can move diagonally
            for (int i = 0; i<numWolves; i++) {
                safetyGrid = new int[grid.length][grid[0].length];
                for (int r=0; r<grid.length; r++)
                    for (int s=0; s<grid[0].length; s++)
                        safetyGrid[r][s] = grid[r][s];
                
                // Wolf can move and howl
                int[] wolfPosition = new int[]{wolfX[i], wolfY[i]};
                WolfAction wolfAction = wolves[i].moveAll(getWolfViewW(i), getWolfViewP(i), makeRelativeHowls(wolfPosition, howls));
                moves[i] = wolfAction.move;
                
                // Add howl at the wolf's position
                if(wolfAction.createdHowl){
                    int[] howlPosition = {wolfPosition[0] + wolfAction.relativeHowlPosition[0], wolfPosition[1] + wolfAction.relativeHowlPosition[1]};
                    newHowls.add(new Howl(howlPosition, howlLoudness[i]));
                }
            }
            howls = newHowls;
            newHowls = new ArrayList<>();
        } else {
            // Wolves can not move diagonally
            for (int i = 0; i < numWolves; i++) {
                safetyGrid = new int[grid.length][grid[0].length];
                for (int r = 0; r < grid.length; r++)
                    for (int s = 0; s < grid[0].length; s++)
                        safetyGrid[r][s] = grid[r][s];

                int dir = wolves[i].moveLim(getWolfViewW(i), getWolfViewP(i));

                switch (dir) {
                    case 0:
                        moves[i][0] = 0;
                        moves[i][1] = 0;
                        break;
                    case 1:
                        // left 
                        moves[i][0] = -1;
                        moves[i][1] = 0;
                        break;
                    case 2:
                        // down
                        moves[i][0] = 0;
                        moves[i][1] = 1;
                        break;
                    case 3:
                        // right
                        moves[i][0] = 1;
                        moves[i][1] = 0;
                        break;
                    case 4:
                        // up
                        moves[i][0] = 0;
                        moves[i][1] = -1;
                        break;
                }
            }
        }

        // and here we move everybody
        for (int i = 0; i < numWolves; i++) {
            if (empty(rowWrap(wolfX[i], moves[i][0]), colWrap(wolfY[i], moves[i][1]))) {
                grid[wolfX[i]][wolfY[i]] = 0;
                wolfX[i] = rowWrap(wolfX[i], moves[i][0]);
                wolfY[i] = colWrap(wolfY[i], moves[i][1]);
                grid[wolfX[i]][wolfY[i]] = i * 2 + 1;
            }
        }

        visuals.update();
        tickcounter++;

        for (int i = 0; i < numPreys; i++) { //add new captured to list
            if (capturedList.contains(i))
                continue;
            if (captured(preyX[i], preyY[i]))
                capturedList.add(i);
        }

        //check whether enough preys have been captured
        if (capturedList.size() >= minCaptured) {
            JOptionPane.showMessageDialog(null, "Wolves won in " + tickcounter + " steps!!");
            System.out.println("Winners");
            System.exit(0);
        }
    }

    private List<Howl> makeRelativeHowls(int[] wolfPosition, List<Howl> howls){
        List<Howl> relativeHowls = new ArrayList<>();
        for (Howl howl : howls) {
            int deltaX = wolfPosition[0]-howl.getPosition()[0];
            int deltaY = wolfPosition[1]-howl.getPosition()[1];
            int[] relativePosition = new int[]{deltaX, deltaY};
            relativeHowls.add(new Howl(relativePosition, howl.getLoudness()));
        }
        return relativeHowls;
    }

    public boolean captured(int r, int c) {
        int count = 0;
        if (grid[rowWrap(r, -1)][colWrap(c, -1)] % 2 == 1) count++;
        if (grid[rowWrap(r, -1)][colWrap(c, 0)] % 2 == 1) count++;
        if (grid[rowWrap(r, -1)][colWrap(c, 1)] % 2 == 1) count++;
        if (grid[rowWrap(r, 0)][colWrap(c, -1)] % 2 == 1) count++;
        if (grid[rowWrap(r, 0)][colWrap(c, 1)] % 2 == 1) count++;
        if (grid[rowWrap(r, 1)][colWrap(c, -1)] % 2 == 1) count++;
        if (grid[rowWrap(r, 1)][colWrap(c, 0)] % 2 == 1) count++;
        if (grid[rowWrap(r, 1)][colWrap(c, 1)] % 2 == 1) count++;
        return (count >= min_surround);
    }

    public int rowWrap(int x, int inc) {
        int tmp = x + inc;
        if (tmp < 0) tmp += rows;
        if (tmp >= rows) tmp -= rows;
        return tmp;
    }

    public int colWrap(int x, int inc) {
        int tmp = x + inc;
        if (tmp < 0) tmp += cols;
        if (tmp >= cols) tmp -= cols;
        return tmp;
    }

    public int getNumbCols() {
        return cols;
    }

    public int getNumbRows() {
        return rows;
    }

    public boolean isWolf(int i, int j) { //Odd numbers are wolves
        return grid[i][j] % 2 == 1;
    }

    public boolean isPrey(int i, int j) { //Even numbers are sheeps
        return grid[i][j] != 0 & grid[i][j] % 2 == 0;
    }

    public void attach(WolvesUI wolvesUI) {
        visuals = wolvesUI;
    }

    public int manhattanDistance(int x0, int y0, int x1, int y1) {
        return Math.abs(x1 - x0) + Math.abs(y1 - y0);
    }

    public List<int[]> getWolfViewW(int wolf) {
        List<int[]> wolves = new ArrayList<>();
        for (int i = 0; i < numWolves; i++) {
            int relX = wolfX[wolf] - wolfX[i];
            int relY = wolfY[wolf] - wolfY[i];

            int[] agent = new int[]{relX, relY};
            wolves.add(agent);
        }

        return wolves;
    }

    public List<int[]> getWolfViewP(int wolf) {
        List<int[]> preys = new ArrayList<>();
        for (int i = 0; i < numPreys; i++) {
            if (manhattanDistance(wolfX[wolf], wolfY[wolf], preyX[i], preyY[i]) > visibility) {
                continue;
            }

            int relX = wolfX[wolf] - preyX[i];
            int relY = wolfY[wolf] - preyY[i];
            int[] agent = new int[]{relX, relY};
            preys.add(agent);
        }

        return preys;
    }

}
