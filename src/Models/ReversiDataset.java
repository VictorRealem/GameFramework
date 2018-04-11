package Models;

public class ReversiDataset extends  Dataset {
    public ReversiDataset()
    {
        gameData = new int[64];
        gameType = GameType.Reversi;

        gameData[27] = 2;
        gameData[28] = 1;
        gameData[35] = 1;
        gameData[36] = 2;

    }
}

