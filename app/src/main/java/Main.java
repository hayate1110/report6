import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import jp.ac.uryukyu.ie.e225719.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Player> players = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        for (int i=0;i<5;i++) {
            String name = "player" + i;
            players.add(new Citizen(name));
        }
        players.add(new WereWolf("wolf"));
        
        Player p1 = players.get(0);
        Player p2 = players.get(5);

        players.get(1).setDead(true);

        p1.vote(players, sc);

        for (Player p: Player.executionList.keySet()) {
            System.out.println(p.getName() + ":" + Player.executionList.get(p));
        }

        for (Player p: Player.executionList.keySet()) {
            System.out.println(p.getName() + ":" + Player.executionList.get(p));
        }

        if(p2 instanceof WereWolf) {
            WereWolf wolf = (WereWolf)p2;
            wolf.raid(players,sc);
        }

        for (Player p: WereWolf.raidList.keySet()) {
            System.out.println(p.getName() + "is" + WereWolf.raidList.get(p));
        }
    }
}
