package jp.ac.uryukyu.ie.e225719;

import java.util.ArrayList;
import java.util.Map;

import java.util.HashMap;

/**
 * プレイヤーのクラス。
 * 全役職が共通して持っている属性と操作を定義する。
 * 全役職はこのクラスを継承して作られる。
 * このクラスを継承する場合、getRoleメソッドとactメソッドは継承した役職に応じたOverrideを行う。
 */
public abstract class Player {
    /** プレイヤー名 */
    private String name;
    /** プレイヤーが死んでいるか */
    private boolean dead = false;

    /** 投票結果を保存 Key: プレイヤー　Value: Keyのプレイヤーの得票数 */
    public static Map<Player, Integer> executionList = new HashMap<>();
    
    /** 
     * 自身の役職に応じた行動を行う。
     * 
     * @param players: 参加プレイヤーのインスタンスのリスト
     */
    public abstract void act(ArrayList<Player> players);

    /**
     * 自身の役職名を返す。
     * 
     * @return "プレイヤー"
     */
    public abstract String getRole();

    /**
     * 自身の名前を返す。
     * 
     * @return 名前
     */
    public String getName() {
        return name;
    }

    /**
     * 自身の名前を引数の名前に変更する。
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 自身のインスタンスが死んでいたらTrue、生きていたらFalseを返す。
     * 
     * @return dead: 生死状態
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * 自身の生死状態を引数の状態に変更する。
     * 
     * @param dead: 生死状態
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * 処刑投票を行うメソッド。
     * プレイヤーから投票したいプレイヤーの番号の入力を受け付けて、executionListの、キーに、指定されたプレイヤーのインスタンスを、バリューに、元の値に1加算したものを設定する。
     * 
     * @param players: 参加プレイヤーのインスタンスのリスト
     */
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