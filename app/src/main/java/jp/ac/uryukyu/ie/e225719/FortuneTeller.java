package jp.ac.uryukyu.ie.e225719;

import java.util.ArrayList;

/**
 * 市民を継承した占い師のクラス。
 * 投票、占いを行うことができる。
 */
public class FortuneTeller extends Citizen {
    /** FortuneTellerTest用 */
    String divine_result;

    /** 
     * 占い師の行動を行うメソッド。
     * 投票と占いを行う。
     */
    @Override
    public void act(ArrayList<Player> players) {
        super.act(players);
        divine(players);
    }

    /**
     * 自身の役職名"占い師"を返すメソッド。
     */
    @Override
    public String getRole() {
        return "占い師";
    }

    /**
     * 占いを行うメソッド。
     * プレイヤーから占いたいプレイヤーの番号を受けつけ、指定されたプレイヤーの役職名を出力する。
     * 
     * @param players: 参加プレイヤーのインスタンスのリスト
     */
    public void divine(ArrayList<Player> players) {
        for(int i=0;i<players.size();i++) {
            GameMaster.outputString(i + ":" + players.get(i).getName());
        }
        
        while(true) {
            try {
                int index = GameMaster.inputInt("占いたいプレイヤーの番号を入力してください:");
                Player p = players.get(index);
                GameMaster.outputString(p.getName() + "さんの役職は" +p.getRole() + "です。");
                divine_result = p.getName() + "さんの役職は" +p.getRole() + "です。";
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