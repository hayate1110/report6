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
        ArrayList<Player> livingPlayers = new ArrayList<>();

        for(Player p: players) {
            if(!p.isDead()) {
                livingPlayers.add(p);
            }
        }

        for(int i=0; i<livingPlayers.size(); i++) {
            System.out.println(i + ":" + livingPlayers.get(i).getName());
        }

        while(true) {
            try {
                int index = GameMaster.inputInt("投票したいプレイヤーの番号を入力してください：");
                int value = executionList.getOrDefault(livingPlayers.get(index), 0);
                executionList.put(livingPlayers.get(index), value + 1);
                break;
            } catch(IndexOutOfBoundsException e) {
                GameMaster.outputString("そのようなプレイヤーは存在しません。");
            }
        }
        
    }
}