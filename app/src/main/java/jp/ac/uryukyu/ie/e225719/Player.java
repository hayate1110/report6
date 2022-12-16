package jp.ac.uryukyu.ie.e225719;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Player {
    private String name;
    private boolean isDead;
    public static Map<Player,Integer> executionList = new HashMap<>();

    public Player(String name) {
        this.name = name;
        this.isDead = false;
    }

    public void vote(ArrayList<Player> players) {
        System.out.println(players);
        System.out.println("処刑したい人の名前を入力してください。");
        for (int i=0;i<players.size();i++) {
            System.out.println(players.get(i).getName());
        }
        Scanner scanner = new Scanner(System.in);

        loop: while (true) {
            String line = scanner.next();
            System.out.println(line);
            for (Player p: players) {
                if (p.getName().equals(line) && !p.isDead()) {
                    int v = executionList.getOrDefault(p,0);
                    v += 1;
                    executionList.put(p,v);
                    break loop;
                }
            }
            System.out.println("そのプレイヤーは死亡しているか存在しません。");
        }
    }

    public String getName() {
        return name;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }
}
