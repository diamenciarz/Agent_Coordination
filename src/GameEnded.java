public class GameEnded extends Exception {
    public int turns;
    public GameEnded(int turns){
        this.turns = turns;
    }
}
