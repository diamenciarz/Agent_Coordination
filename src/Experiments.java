import java.io.FileWriter;

public class Experiments {
    public static void main(String[] args) {
        ExperimentSettings settings = new ExperimentSettings();
        int[] loudnesses = new int[]{10, 20, 30};
        int[] nrOfWolves = new int[]{3, 5, 10};
        double[] followPreyChances = new double[]{1, 0.5, 0.2, 0};

        for (double chance : followPreyChances) {
            for (int loudness : loudnesses) {
                for (int wolfCount : nrOfWolves) {
<<<<<<< HEAD
                    if (chance==1 && loudness==10 && wolfCount == 10) {
                        settings = new ExperimentSettings(loudness, wolfCount, chance);
                        
                        String name = String.format("results/%d_%d_%d_results.txt", loudness, wolfCount, chanceToInt(chance));
                        experimentWithSettings(settings, 2,name);
                    }
=======
                    settings = new ExperimentSettings(loudness, wolfCount, chance);

                    String name = String.format("results/%d_%d_%d_results.txt", loudness, wolfCount, chanceToInt(chance));
                    experimentWithSettings(settings, 30,name);
                    System.out.println("\nDone with wolf batch");
>>>>>>> afdb38fb01195d82e067448922d3fc2e29fa9fd9
                }   
            }
        }
    }
    
    private static int chanceToInt(double chance){
        if (chance==1) return 1;
        if (chance==0.5) return 05;
        if (chance==0.2) return 02;
        if (chance==0) return 0;
        return 100;

    }

    private static void experimentWithSettings(ExperimentSettings settings, int gameCount, String filename) {
        int width = 50;
        int height = 50;
        int squaresize = 15;
        int delay = 100;

        try {
            WolvesApp wolfApp;
            FileWriter resultWriter = new FileWriter(filename);
            
            resultWriter.append(settings.toString());
            resultWriter.append("\n");
            resultWriter.append("Game ticks:\n");
            resultWriter.flush();
            
            for (int i = 0; i < gameCount; i++) {
                wolfApp = new WolvesApp("Hungry Hungry Wolves", height, width, squaresize, settings);
                int turns = wolfApp.runGoL(delay);
                wolfApp.dispose();

                resultWriter.append(String.valueOf(turns));
                if (i != gameCount - 1) {
                    resultWriter.append(",");
                }
                resultWriter.flush();
                System.out.print(i);
                System.out.print(", ");
            }

            resultWriter.close();

        } catch (Exception e) {
            System.err.println("File writer exception");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
