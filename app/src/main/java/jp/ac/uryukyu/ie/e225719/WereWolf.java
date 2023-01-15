package jp.ac.uryukyu.ie.e225719;

import java.util.ArrayList;

public class WereWolf extends Player {
    @Override
    public void act(ArrayList<Player> players) {
        vote(players);
    }
}
