package Controllers;

import Models.*;

/***
 *  SingleTon class for handling all dataObjects for the application.
 */
public class DataController {

    /***
     *  SingleTon instancie for the data controller
     */
    private static DataController ourInstance = new DataController();

    /***
     * Static method for getting the instance
     * @return DataController
     */
    public static DataController getInstance() {
        return ourInstance;
    }

    /***
     *  generic Game datasets
     */
    private Dataset dataset;
    private PossibleMovesDataset pmDataset;

    /***
     * Initializing the class
     */
    private DataController() {
        pmDataset = new PossibleMovesDataset();
    }

    /***
     * Sets the DatasetType and creates the corresponding dataset.
     * This method is required to start a game!
     * @param type gametype
     */
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

        this.dataset.YourTurn = false;
    }

    /***
     * Saves the data of the game.
     * @param boardData game data
     */
    public void setData(int[] boardData)
    {
        this.dataset.gameData = boardData;
    }

    /***
     * Saves the option for using the AI
     * @param AI
     */
    public void setAI(boolean AI)
    {
        this.dataset.AI = AI;
    }

    /***
     * Saves the possible moves after these are calculated.
     * @param boardData
     */
    public void setPossibleMoves(int[] boardData)
    {
        this.pmDataset.Dataset = boardData;
    }

    /***
     * Saves yourturn boolean.
     * @param turn
     */
    public void setYourTurn(boolean turn)
    {
        this.dataset.YourTurn = turn;
    }

    public void setPlayerOne(boolean playerOne)
    {
        this.dataset.PlayerOne = playerOne;
    }

    public void setPlayerName(String name)
    {
        this.dataset.PlayerName = name;
    }

    public void setOpponentName(String name)
    {
        this.dataset.OpponentName = name;
    }

    /***
     * Returns the game data
     * @return Game data
     */
    public int[] getData()
    {
        return this.dataset.gameData;
    }

    /***
     * Returns the game type
     * @return GameType
     */
    public GameType getGameType()
    {
        return this.dataset.gameType;
    }

    /***
     * Returns AI option
     * @return AI
     */
    public boolean getAI()
    {
        return this.dataset.AI;
    }

    /***
     * Returns the possible moves dataset
     * @return Possible moves
     */
    public int[] getPossibleMoves()
    {
        return this.pmDataset.Dataset;
    }

    /***
     * Return Yourturn boolean
     * @return Yourturn
     */
    public boolean getYourTurn()
    {
        return this.dataset.YourTurn;
    }

    public boolean getPlayerOne()
    {
        return this.dataset.PlayerOne;
    }

    public String getPlayerName()
    {
        return this.dataset.PlayerName;
    }

    public String getOpponentName()
    {
        return this.dataset.OpponentName;
    }
}
