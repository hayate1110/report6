package jp.ac.uryukyu.ie.e225719;

import java.util.ArrayList;

public class Knight extends Citizen {
    public static ArrayList<Player> protectedPlayers = new ArrayList<>();

    @Override
    public void act(ArrayList<Player> players) {
        super.act(players);
        guard(players);
    }

    @Override
    public String getRole() {
        return "騎士";
    }

    public void guard(ArrayList<Player> players) {
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
                int index = GameMaster.inputInt("守りたいプレイヤーの番号を入力してください:");
                Player p = livingPlayers.get(index);
                protectedPlayers.add(p);
            } catch(IndexOutOfBoundsException e) {
                GameMaster.outputString("そのようなプレイヤーは存在しません。");
            }
        }
    }
}
