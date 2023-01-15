package jp.ac.uryukyu.ie.e225719;

import java.util.ArrayList;

public class FortuneTeller extends Citizen {
    @Override
    public void act(ArrayList<Player> players) {
        super.act(players);
        divine(players);
    }

    @Override
    public String getRole() {
        return "占い師";
    }

    public void divine(ArrayList<Player> players) {
        for(int i=0;i<players.size();i++) {
            GameMaster.outputString(i + ":" + players.get(i).getName());
        }
        
        while(true) {
            try {
                int index = GameMaster.inputInt("占いたいプレイヤーの番号を入力してください:");
                Player p = players.get(index);
                GameMaster.outputString(p.getName() + "さんの役職は" +p.getRole() + "です。");
                break;
            } catch(IndexOutOfBoundsException e) {
                GameMaster.outputString("そのようなプレイヤーは存在しません。");
            }
        }

        int btn = 0;
        while(btn != 1) {
            btn = GameMaster.inputInt("確認したら、1を入力してください:");
        }
    }
}