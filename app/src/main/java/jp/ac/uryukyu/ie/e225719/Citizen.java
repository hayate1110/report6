package jp.ac.uryukyu.ie.e225719;

import java.util.ArrayList;

public class Citizen extends Player {
    @Override
    public void act(ArrayList<Player> players) {
        vote(players);
    }

    @Override
    public String getRole() {
        return "市民";
    }
}
