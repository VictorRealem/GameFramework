package Helpers;

public class EventHandler {
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
            case "HELP" :
                System.out.println("Server gave help information:");
                System.out.println(command);
                break;
            case "GAME" :
                this.GameHandler(response);
                break;
            case "MATCH" :
                System.out.println("Match started.");
                break;
            case "YOURTURN" :
                System.out.println("Your turn.");
                break;
            case "MOVE" :
                System.out.println("A move has been set by a player.");
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

    private void GameHandler(String response)
    {

    }
}
