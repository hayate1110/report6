package jp.ac.uryukyu.ie.e225719;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Random;


public class GameMaster {
    private ArrayList<Player> players;
    private int dayTime;
    private Scanner sc;

    public GameMaster() {
        players = new ArrayList<>();
        sc = new Scanner(System.in);
        dayTime = 60;
    }

    public void setUp() {
        while(true) {
            try {
                System.out.print("参加人数を入力してください:");
                int numPeople = sc.nextInt();
    
                System.out.print("人狼の数を入力してください:");
                int numWolf = sc.nextInt();
                int numCitizen = numPeople - numWolf;
    
                System.out.print("議論の時間の長さを入力してください(秒):");
                int time = sc.nextInt();
                sc.nextLine();
    
                for(int i=0;i<numWolf;i++) {
                    System.out.print("プレイヤーの名前を入力してください:");
                    String name = sc.nextLine();
                    players.add(new WereWolf(name));
                }
    
                for(int i=0;i<numCitizen;i++) {
                    System.out.print("プレイヤーの名前を入力してください:");
                    String name = sc.nextLine();
                    players.add(new Citizen(name));
                }
    
                this.dayTime = time;

                Collections.shuffle(players);
                break;
            } catch(InputMismatchException e) {
                sc.nextLine();
                System.out.println("整数を入力してください。");
            }
        }
    }

    public void play() {
        System.out.println("人狼を開始します。");
        setUp();
        while(true) {
            try {
                System.out.println("朝になりました。話し合いを開始してください(" + dayTime + "秒)。");
                TimeUnit.SECONDS.sleep(dayTime);
                System.out.println();
            } catch(InterruptedException e) {

            }
            System.out.println("夜になりました。");

            vote();
            if(judge()) break;

            raid();
            if(judge()) break;
        }
    }

    public void vote() {
        System.out.println("処刑投票を行います。");
        for(Player p: players) {
            System.out.println(p.getName() + "さん投票を行なってください。");
            if(!p.isDead()) {
                p.vote(players, sc);
                if(p instanceof WereWolf) {
                    System.out.println("襲撃投票を行います。");
                    WereWolf w = (WereWolf)p;
                    w.raid(players, sc);
                }
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }

        Player maxKey = players.get(0);
        int maxValue = 0;

        for(Map.Entry<Player,Integer> entry: Player.executionList.entrySet()) {
            if(entry.getValue() > maxValue) {
                maxKey = entry.getKey();
                maxValue = entry.getValue();
            }
            else if(entry.getValue() == maxValue) {
                Random ran = new Random();
                int r = ran.nextInt(2);
                if(r == 1){
                    maxKey = entry.getKey();
                    maxValue = entry.getValue();
                }
            }
        }
        
        System.out.println("投票の結果" + maxKey.getName() + "さんが処刑されます。");
        maxKey.setDead(true);
        Player.executionList.clear();
    }

    public void raid() {
        Player maxKey = players.get(0);
        int maxValue = 0;
        for(Map.Entry<Player,Integer> entry: WereWolf.raidList.entrySet()) {
            if(entry.getValue() > maxValue) {
                maxKey = entry.getKey();
                maxValue = entry.getValue();
            }
            else if(entry.getValue() == maxValue) {
                Random ran = new Random();
                int r = ran.nextInt(2);
                if(r == 1){
                    maxKey = entry.getKey();
                    maxValue = entry.getValue();
                }
            }
        }

        System.out.println(maxKey.getName() + "さんが襲撃されました。");
        maxKey.setDead(true);
        WereWolf.raidList.clear();
    }

    public boolean judge() {
        int wolfs = 0;
        int citizens = 0;

        for(Player p: players) {
            if(!p.isDead()) {
                if(p instanceof Citizen) {
                    citizens += 1;
                }
                else {
                    wolfs += 1;
                }
            }
        }

        if(wolfs == 0) {
            System.out.println("人狼の数が0人になりました。市民側の勝利です。");
            return true;
        }

        else if(citizens == wolfs) {
            System.out.println("市民と人狼の数が同数になりました。人狼側の勝利です。");
            return true;
        }
        else {
            return false;
        }
    }
}
