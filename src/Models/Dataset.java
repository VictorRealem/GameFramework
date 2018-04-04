package Models;


public class Dataset {
    public Dataset()
    {
        System.out.println("Creating dataset");
    }
    public int[] gameData;
    public GameType gameType;
    public boolean AI;
    public boolean yourTurn;
    private boolean playerOne;
    public boolean getP1() {
        System.out.println("Getting p1 in dataset" + playerOne);
        return playerOne;
    }

    public void setP1(boolean p1) {
        System.out.println("Setting p1 in dataset");
        playerOne = p1;
    }
}
