package jp.ac.uryukyu.ie.e225719;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GameMaster {
    public static ArrayList<Player> players;
    private int dayTime;

    public GameMaster() {
        players = new ArrayList<>();
        dayTime = 180;
    }

    public void setUp() {

    }

    public void run() {
        while(true) {
            try {
                TimeUnit.SECONDS.sleep(dayTime);
            }catch(InterruptedException e) {
            }
            actToPlayers();
            execution();
            if(judge()) break;
            raid();
            if(judge()) break;
        }
    }

    public void actToPlayers() {

    }

    public void execution() {

    }

    public void raid() {

    }

    public boolean judge() {
        return true;
    }

    public static String inputString(String message) {
        return "";
    }

    public static int inoutInt(String message) {
        return 0;
    }

    public static void outoutString(String message) {

    }
}
