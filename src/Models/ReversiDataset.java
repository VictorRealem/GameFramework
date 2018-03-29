package Models;

public class ReversiDataset extends  Dataset {
    public ReversiDataset()
    {
        gameData = new int[64];
        gameType = GameType.Reversi;
        aI = false;
    }
}
