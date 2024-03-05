public class Howl {
    public int[] position;
    public int loudness;

    public Howl(int[] newPos, int newLoudness) {
        position = newPos;
        loudness = newLoudness;
    }

    public int[] getPosition() {
        return position;
    }

    public int getLoudness() {
        return loudness;
    }

    @Override
    public String toString(){
        return String.format("Howl(%d,%d):%d", position[0], position[1], loudness);
    }

}
