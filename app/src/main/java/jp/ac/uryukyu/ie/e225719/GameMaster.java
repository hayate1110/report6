package jp.ac.uryukyu.ie.e225719;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ExecutionList;

public class GameMaster {
    private ArrayList<Player> players;
    private int dayTime;

    public static Scanner sc = new Scanner(System.in);

    public GameMaster() {
        players = new ArrayList<>();
        dayTime = 180;
    }

    public void setUp() {
        int numPlayers = inputInt("参加人数を入力してください。");
        int numWolfs = inputInt("人狼の数を入力してください。");
        int numFortuneTellers = inputInt("占い師の数を入力してください。");
        int numKnights = inputInt("騎士の数を入力してください。");
        int numCitizens = numPlayers - (numWolfs + numFortuneTellers + numKnights);
        dayTime = inputInt("話し合いの時間を入力してください。");
        
        for(int i=0;i<numCitizens;i++) {
            players.add(new Citizen());
        }

        for(int i=0;i<numWolfs;i++) {
            players.add(new WereWolf());
        }

        for(int i=0;i<numFortuneTellers;i++) {
            players.add(new FortuneTeller());
        }

        for(int i=0;i<numKnights;i++) {
            players.add(new Knight());
        }

        Collections.shuffle(players);

        for(int i=0;i<players.size();i++) {
            String name = inputString(i+"番目のプレイヤーの名前を入力してください：");
            players.get(i).setName(name);
            System.out.println("あなたの役職は"+players.get(i).getRole()+"です。");
        }
    }

    public void run() {
        while(true) {
            try {
                TimeUnit.SECONDS.sleep(dayTime);
            }catch(InterruptedException e) {
            }
            actToPlayers();
            execution();
            if(judge()) break;
            raid();
            if(judge()) break;
        }
    }

    public void actToPlayers() {
        for(Player p: players) {
            p.act(players);
        }
    }

    public void execution() {
        Player max = players.get(0);
        for(Map.Entry<Player,Integer> map: Player.executionList.entrySet()) {
            if(Player.executionList.getOrDefault(max, 0) <= map.getValue()) {
                max = map.getKey();
            }
        }
        
        outputString("投票の結果、" + max.getName() + "さんが処刑されました。");
        max.setDead(true);
    }

    public void raid() {
        
    }

    public boolean judge() {
        return true;
    }

    public static String inputString(String message) {
        String result = "";
        while(result == "") {
            System.out.print(message);
            result = sc.nextLine();
        }
        return result;
    }

    public static int inputInt(String message) {
        int result;
        while(true) {
            try {
                System.out.print(message);
                result = sc.nextInt();
                break;
            }catch(InputMismatchException e) {
                System.out.println("整数を入力してください。");
                sc.nextLine();
            }
        }
        return result;
    }

    public static void outputString(String message) {
        System.out.println(message);
    }
}
