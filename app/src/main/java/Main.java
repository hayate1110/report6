import java.util.ArrayList;
import java.util.Map;

import jp.ac.uryukyu.ie.e225719.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Player> players = new ArrayList<>();
        for (int i=0;i<5;i++) {
            String name = "player" + i;
            players.add(new Player(name));
        }
        
        Player p1 = players.get(0);
        p1.vote(players);
    
        for (Player key : Player.executionList.keySet()) {
            System.out.println(key + ":" + Player.executionList.get(key));
        }
    }
}
