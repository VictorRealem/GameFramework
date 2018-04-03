package Models;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    public UserData(){
        this.gamelist = new ArrayList<>();
        this.playerlist = new ArrayList<>();
    }

    public String PlayerName;
    public String OpponentName;
    public List<String> gamelist;
    public List<String> playerlist;
}
