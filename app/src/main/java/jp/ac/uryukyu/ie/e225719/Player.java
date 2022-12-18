package jp.ac.uryukyu.ie.e225719;
import java.util.Map;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
    private String name;
    private boolean isDead;
    public static Map<Player,Integer> executionList = new HashMap<>();

    public Player(String name) {
        this.name = name;
        this.isDead = false;
    }

    public void vote(ArrayList<Player> players, Scanner sc) {
        for(int i=0;i<players.size();i++) {
            Player p = players.get(i);
            if(!p.isDead) {
                System.out.println(i + ":" + p.getName());
            }
        }

        System.out.println();
        while(true) {
            try {
                System.out.print("処刑したいプレイヤーの番号を入力してください:");
                int num = sc.nextInt();
                Player p = players.get(num);
                
                if(!p.isDead) {
                    int value = executionList.getOrDefault(p, 0);
                    value += 1;
                    executionList.put(p, value);
                    break;
                }
            } catch(InputMismatchException e) {
                sc.nextLine();
                System.out.println("整数を入力してください。");
            } catch(IndexOutOfBoundsException e) {
                System.out.println("その番号のプレイヤーは存在しません。");
            }
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
