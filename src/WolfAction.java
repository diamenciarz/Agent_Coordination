public class WolfAction {
    public int[] move;
    public int[] relativeHowlPosition;
    public boolean createdHowl;

    public WolfAction(int[] move, boolean createdHowl, int[] relativeHowlPosition){
        this.move = move;
        this.createdHowl = createdHowl;
        this.relativeHowlPosition = relativeHowlPosition;
    }
}

