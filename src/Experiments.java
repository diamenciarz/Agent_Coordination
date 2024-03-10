import java.io.FileWriter;

public class Experiments {
    public static void main(String[] args) {
        ExperimentSettings settings = new ExperimentSettings();
        experimentWithSettings(settings, 2);
    }

    private static void experimentWithSettings(ExperimentSettings settings, int gameCount) {
        int width = 50;
        int height = 50;
        int squaresize = 15;
        int delay = 100;

        try {
            WolvesApp wolfApp;
            FileWriter resultWriter = new FileWriter("results.txt");

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
            }

            resultWriter.close();

        } catch (Exception e) {
            System.err.println("File writer exception");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
