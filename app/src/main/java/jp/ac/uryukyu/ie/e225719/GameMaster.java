package jp.ac.uryukyu.ie.e225719;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class GameMaster {
    private ArrayList<Player> players;
    private int dayTime;
    private Random rand = new Random();

    public static Scanner sc = new Scanner(System.in);

    public GameMaster() {
        players = new ArrayList<>();
        dayTime = 180;
    }

    public void setUp() {
        int numPlayers = inputInt("参加人数を入力してください:");
        int numWolfs = inputInt("人狼の数を入力してください:");
        int numFortuneTellers = inputInt("占い師の数を入力してください:");
        int numKnights = inputInt("騎士の数を入力してください:");
        int numCitizens = numPlayers - (numWolfs + numFortuneTellers + numKnights);

        while(true) {
            numWolfs = inputInt("人狼の数を入力してください:");
            if(numWolfs >= numPlayers) {
                outputString("人狼の数は、市民側の数未満にしてください。");
                continue;
            }
            numFortuneTellers = inputInt("占い師の数を入力してください:");
            numKnights = inputInt("騎士の数を入力してください:");
            numCitizens = numPlayers - (numWolfs + numFortuneTellers + numKnights);
            if(numCitizens <= 0) {
                outputString("役職の数が多すぎます。");
                outputString("役職の数:" + (numWolfs + numFortuneTellers + numKnights));
                outputString("参加人数:" + numPlayers);
                continue;
            }
            break;
        }
        dayTime = inputInt("話し合いの時間を入力してください:");
        
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
            sc.nextLine();

            String name = inputString((i+1) + "番目のプレイヤーの名前を入力してください：");
            players.get(i).setName(name);
            outputString("あなたの役職は"+players.get(i).getRole()+"です。");

            int btn = 0;
            while(btn != 1) {
                btn = inputInt("確認したら、1を入力してください:");
            }
            clearConsoleScreen();
        }
    }

    public void run() {
        while(true) {
            outputString("昼になりました。話し合いを行ってください。（" + dayTime + "秒）");
            try {
                TimeUnit.SECONDS.sleep(dayTime);
            }catch(InterruptedException e) {
            }

            outputString("夜になりました。各プレイヤーは行動を行ってください。");
            actToPlayers();
            execution();
            if(judge()) break;
            raid();
            if(judge()) break;
        }
    }

    public void actToPlayers() {
        for(Player p: players) {
            if(!p.isDead()) {
                outputString(p.getName()+"さん、操作してください。");
                p.act(players);
                clearConsoleScreen();
            }
        }
    }

    public void execution() {
        Player candidPlayer = players.get(0);
        for(Map.Entry<Player,Integer> map: Player.executionList.entrySet()) {
            int valueCandid = Player.executionList.getOrDefault(candidPlayer, 0);
            if(valueCandid < map.getValue()) {
                candidPlayer = map.getKey();
            } else if(valueCandid == map.getValue()) {
                int r = rand.nextInt(2);
                if(r == 0) {
                    candidPlayer = map.getKey();
                }
            }
        }

        candidPlayer.setDead(true);
        outputString("投票の結果、" + candidPlayer.getName() + "さんが処刑されました。");

        Player.executionList.clear();
    }

    public void raid() {
        Player candidPlayer = players.get(0);

        for(Map.Entry<Player,Integer> map: WereWolf.raidList.entrySet()) {
            int valueCandid = WereWolf.raidList.getOrDefault(candidPlayer, 0);
            if(valueCandid < map.getValue()) {
                candidPlayer = map.getKey();
            } else if(valueCandid == map.getValue()) {
                int r = rand.nextInt(2);
                if(r == 0) {
                    candidPlayer = map.getKey();
                }   
            }
        }

        candidPlayer.setDead(true);
        outputString(candidPlayer.getName() + "さんが襲撃されました。");

        WereWolf.raidList.clear();
    }

    public boolean judge() {
        ArrayList<Player> LivingWolfs = new ArrayList<>();
        ArrayList<Player> LivingPlayers = new ArrayList<>();

        for(Player p: players) {
            if(p instanceof WereWolf && !p.isDead()) {
                LivingWolfs.add(p);
            } else if(!p.isDead()) {
                LivingPlayers.add(p);
            }
        }

        if(LivingWolfs.size() == 0) {
            outputString("人狼の数が0になりました。市民側の勝利です。");
            return true;
        } else if(LivingPlayers.size() == LivingWolfs.size()) {
            outputString("市民側のプレイヤー数と、人狼側のプレイヤー数が同数になりました。人狼側の勝利です。");
            return true;
        } else {
            return false;
        }
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

    public static void clearConsoleScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
