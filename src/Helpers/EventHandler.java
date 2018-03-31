package Helpers;

import Controllers.DataController;
import Controllers.ReversiController;
import Controllers.TicTacToeController;
import Models.GameType;

import java.util.HashMap;

public class EventHandler {

    private DataController dataController;

    public EventHandler()
    {
        this.dataController = DataController.getInstance();
    }

    public void HandleCommand(String response)
    {

        String command = response.split(" ")[0];
        switch(command)
        {
            case "OK" :
                System.out.println("Server accepted command.");
                break;
            case "ERR" :
                System.out.println("Server denied command!");
                break;
            case "SVR" :
                this.SVRHandler(response.substring(4));
                break;
            default:
                break;
        }
    }

    private void SVRHandler(String response)
    {
        String command = response.split(" ")[0];
        if("HELP".equals(command))
        {
            System.out.println("Server gave help information:");
            System.out.println(response);
        }
        if("PLAYERLIST".equals(command))
        {
            this.PlayerlistHandler(response.substring(11));
        }
        if("GAMELIST".equals(command))
        {
            this.GamelistHandler(response.substring(9));
        }
        if("GAME".equals(command))
        {
            response = response.substring(5);
            command = response.split(" ")[0];
            switch(command)
            {
                case "MATCH" :
                    this.MatchHandler(response.substring(6));
                    System.out.println("Match started.");
                    break;
                case "YOURTURN" :
                    this.TurnHandler(response.substring(9));
                    break;
                case "MOVE" :
                    this.MoveHandler(response.substring(5));
                    break;
                case "CHALLENGE" :
                    this.ChallengeHandler(response.substring(10));
                    break;
                case "WIN" :
                    this.EndGameHandler(response.substring(4), "WIN");
                    break;
                case "LOSS" :
                    this.EndGameHandler(response.substring(5), "LOSS");
                    break;
                case "DRAW" :
                    this.EndGameHandler(response.substring(5), "DRAW");
                    break;
                default:
                    break;
            }
        }

    }
    private void PlayerlistHandler(String response)
    {
        response = response.replaceAll(" ", "");
        response = response.substring(1, response.length() - 1);
        dataController.clearPlayerlist();
        for(String name : response.split(","))
        {
            name = name.substring(1, name.length() - 1);
            dataController.addPlayerlistItem(name);
        }

    }

    private void GamelistHandler(String response) {
        response = response.replaceAll(" ", "");
        response = response.substring(1, response.length() - 1);
        dataController.clearGamelist();
        for(String game : response.split(","))
        {
            game = game.substring(1,game.length() - 1);
            dataController.addGamelistItem(game);
        }
    }

    private void TurnHandler(String response)
    {
        HashMap<String, String> parameters = this.parameterConvert(response);
        dataController.setYourTurn(true);

        GameType type = dataController.getGameType();
        if(type == GameType.Tictactoe)
        {
            TicTacToeController contr = new TicTacToeController();
            contr.turn();
        }
        if(type == GameType.Reversi)
        {
            ReversiController contr = new ReversiController();
            contr.turn();
        }

    }

    private void MatchHandler(String response)
    {
        HashMap<String, String> parameters = this.parameterConvert(response);
        String playertostart = parameters.get("PLAYERTOMOVE");
        String opponent = parameters.get("OPPONENT");
        boolean Yourturn = true;
        boolean PlayerOne = true;
        if(playertostart.equals(opponent))
        {
            Yourturn = false;
            PlayerOne = false;
        }

        dataController.setYourTurn(Yourturn);
        dataController.setPlayerOne(PlayerOne);
        dataController.setOpponentName(opponent);
    }

    private void MoveHandler(String response)
    {
        int move, player;

        HashMap<String, String> parameters = this.parameterConvert(response);
        String PlayerName = parameters.get("PLAYER");
        String OpponentName = dataController.getOpponentName();
        boolean PlayerOne = dataController.getPlayerOne();

        if(PlayerName.equals(OpponentName))
        {
            player = 1;
            if(PlayerOne)
            {
                player = 2;
            }
        }
        else
        {
            player = 2;
            if(PlayerOne)
            {
                player = 1;
            }
        }

        move = Integer.parseInt(parameters.get("MOVE"));
        GameType gameType = dataController.getGameType();

        if(gameType == GameType.Reversi){
            ReversiController contr = new ReversiController();
            contr.update(move, player);
        }

        if(gameType == GameType.Tictactoe){
            TicTacToeController contr = new TicTacToeController();
            contr.update(move, player);
        }
    }

    private void ChallengeHandler(String response)
    {

    }

    private void EndGameHandler(String response, String state)
    {

    }

    private HashMap<String, String> parameterConvert(String parameters)
    {
        parameters = parameters.replaceAll(" ", "");
        parameters = parameters.substring(1, parameters.length() -1);
        String[] params = parameters.split(",");

        HashMap<String, String> parameterValues = new HashMap<>();

        for(String param : params)
        {
            String[] keyvaluepair = param.split(":");
            String key = keyvaluepair[0];
            String value = keyvaluepair[1].substring(1, keyvaluepair[1].length() - 1);
            parameterValues.put(key, value);
        }

        return parameterValues;

    }
}
