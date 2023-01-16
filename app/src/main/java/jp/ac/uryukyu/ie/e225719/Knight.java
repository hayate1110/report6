package jp.ac.uryukyu.ie.e225719;

import java.util.ArrayList;

/**
 * 市民を継承した騎士のクラス。
 * 投票と、防護を行うことができる。
 */
public class Knight extends Citizen {
    /** 騎士クラスが防護対象としているプレイヤーのリスト */
    public static ArrayList<Player> protectedPlayers = new ArrayList<>();

    /**
     * 騎士の行動を行うメソッド。
     * 投票と防護を行う。
     */
    @Override
    public void act(ArrayList<Player> players) {
        super.act(players);
        guard(players);
    }

    /**
     * 自身の役職名"騎士"を返すメソッド。
     */
    @Override
    public String getRole() {
        return "騎士";
    }

    /**
     * 防護を行うメソッド。
     * プレイヤーから防護対象のプレイヤーの番号を受けつけ、指定されたプレイヤーをprotectedPlayersリストへ追加する。
     * 
     * @param players: 参加プレイヤーのインスタンスのリスト
     */
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
                break;
            } catch(IndexOutOfBoundsException e) {
                GameMaster.outputString("そのようなプレイヤーは存在しません。");
            }
        }
    }
}
