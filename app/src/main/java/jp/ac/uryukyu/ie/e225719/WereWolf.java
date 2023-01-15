package jp.ac.uryukyu.ie.e225719;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class WereWolf extends Player {
    static public Map<Player, Integer> raidList = new HashMap<>();

    @Override
    public void act(ArrayList<Player> players) {
        vote(players);
        voteRaid(players);
    }

    public void voteRaid(ArrayList<Player> players) {
        ArrayList<Player> livingPlayers = new ArrayList<>();

        for(Player p: players) {
            if(!p.isDead()) {
                livingPlayers.add(p);
            }
        }

        for(int i=0;i<livingPlayers.size();i++) {
            GameMaster.outputString(i + ":" + livingPlayers.get(i).getName());
        }

        while(true) {
            try {
                int index = GameMaster.inputInt("襲撃したいプレイヤーの番号を入力してください:");
                int scoreInput = -1;
                while(scoreInput < 1 || scoreInput > 3) {
                    scoreInput = GameMaster.inputInt("殺害優先度(1~3)を入力してください:");
                }
                
                Player p = livingPlayers.get(index);
                int scoreList = raidList.getOrDefault(p, 0);
                raidList.put(p, scoreList + scoreInput);
                break;
            } catch(IndexOutOfBoundsException e) {
                GameMaster.outputString("そのようなプレイヤーは存在しません。");
            }
        }
    }

    @Override
    public String getRole() {
        return "人狼";
    }
}
