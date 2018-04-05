package Models;

public class ReversiDataset extends  Dataset {
    public ReversiDataset()
    {
        gameData = new int[64];
        gameType = GameType.Reversi;

        gameData[27] = 1;
        gameData[28] = 2;
        gameData[35] = 2;
        gameData[36] = 1;

    }
}
