public class ExperimentSettings {
    public int numWolves = 3;
    public int numPreys = 20;
    public int visibility = 10;
    public int minCaptured = 5;
    public int min_surround = 2;
    public int defaultLoudness = 10;
    public double followPreyChance = 0.5;
    public double followOtherWolvesChance = 0.5;

    public ExperimentSettings(){

    }
    public ExperimentSettings(int loudness, int wolfNr, double followPreyChance){
        defaultLoudness = loudness;
        numWolves = wolfNr;
        this.followPreyChance = followPreyChance;
    }

    @Override
    public String toString() {
        return String.format("Settings(defaultLoudness=%d, followPreyChance=%f, followOtherWolvesChance=%f, numWolves=%d, numPreys=%d, visibility=%d, minCaptured=%d, min_surround=%d)"
        , defaultLoudness, followPreyChance, followOtherWolvesChance, numWolves, numPreys, visibility, minCaptured, min_surround);
    }
}
