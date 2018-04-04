package Models;

public class TicTacToeDataset extends Dataset {
    public TicTacToeDataset()
    {
        gameData = new int[9];
        gameType = GameType.Tictactoe;
    }
}
