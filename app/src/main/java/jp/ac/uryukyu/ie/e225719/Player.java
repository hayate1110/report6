package jp.ac.uryukyu.ie.e225719;

import java.util.ArrayList;
import java.util.Map;

import java.util.HashMap;

public abstract class Player {
    private String name;
    private boolean dead = false;
    public static Map<Player, Integer> executionList = new HashMap<>();
    
    public abstract void act(ArrayList<Player> players);

    public String getRole() {
        return "プレイヤー";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void vote(ArrayList<Player> players) {
        for(int i=0; i<players.size(); i++) {
            System.out.println(i + ":" + players.get(i).getName());
        }
        int number = GameMaster.inputInt("投票したいプレイヤーの番号を入力してください：");
        int value = executionList.getOrDefault(players.get(number), 0);
        executionList.put(players.get(number), value + 1);
    }
}