package Helpers;

import Controllers.DataController;
import Controllers.ReversiController;
import Controllers.TicTacToeController;
import Models.GameType;

import java.util.HashMap;

public class EventHandler {

    private DataController dataController;
    private GameType gameType;

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
            System.out.println(command);
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
                    //System.out.println("A move has been set by a player.");
                    //this.MoveHandler();
                    break;
                case "CHALLENGE" :
                    System.out.println("You have been challeged.");
                    break;
                case "WIN" :
                    System.out.println("You have won!");
                    break;
                case "LOSS" :
                    System.out.println("better luck next time.");
                    break;
                case "DRAW" :
                    System.out.println("it's a draw.");
                    break;
                default:
                    break;
            }
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
        int move = 0;
        int player = 0;

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
        this.gameType = dataController.getGameType();

        if(this.gameType == GameType.Reversi){
            ReversiController contr = new ReversiController();
            contr.update(move, player);
        }

        if(this.gameType == GameType.Tictactoe){
            TicTacToeController contr = new TicTacToeController();
            contr.update(move, player);
        }
    }

    private HashMap<String, String> parameterConvert(String parameters)
    {
        parameters.trim();
        parameters.substring(1, parameters.length() -1);
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
