package Controllers;

import Models.*;

public class DataController {
    private static DataController ourInstance = new DataController();

    public static DataController getInstance() {
        return ourInstance;
    }

    private Dataset dataset;
    private PossibleMovesDataset pmDataset;

    private DataController() {
        pmDataset = new PossibleMovesDataset();
    }

    public void setDatasetType(GameType type)
    {
        if(type == GameType.Reversi) {
            this.dataset = new ReversiDataset();
            this.dataset.gameType = type;
        }

        if(type == GameType.Tictactoe) {
            this.dataset = new TicTacToeDataset();
            this.dataset.gameType = type;
        }
    }

    public void setData(int[] boardData)
    {
        this.dataset.gameData = boardData;
    }

    public void setAI(boolean AI)
    {
        this.dataset.AI = AI;
    }

    public void setPossibleMoves(int[] boardData)
    {
        this.pmDataset.Dataset = boardData;
    }

    public int[] getData()
    {
        return this.dataset.gameData;
    }

    public GameType getGameType()
    {
        return this.dataset.gameType;
    }

    public boolean getAI()
    {
        return this.dataset.AI;
    }

    public int[] getPossibleMoves()
    {
        return this.pmDataset.Dataset;
    }

}
