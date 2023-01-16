package jp.ac.uryukyu.ie.e225719;

import java.util.ArrayList;

/**
 * 市民のクラス。
 * 投票を行うことができる。
 * @see Player
 */
public class Citizen extends Player {
    
    /** 投票を行うメソッド。 */
    @Override
    public void act(ArrayList<Player> players) {
        vote(players);
    }

    /** 
     * 自身の役職名、"市民"を返す。
     */
    @Override
    public String getRole() {
        return "市民";
    }
}
