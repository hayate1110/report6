package jp.ac.uryukyu.ie.e225719;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 人狼ゲーム自体の管理を行うクラス
 * 設定、ゲーム進行、終了判定、プレイヤーの死亡管理を行う。
 */
public class GameMaster {
    /** ゲームに参加しているプレイヤーのインスタンスを保存する。 */
    private ArrayList<Player> players;
    /** 話し合いの秒数を保存する。 */
    private int dayTime;
    /** 乱数生成用。 */
    private Random rand;

    /** プレイヤーからの標準入力用 */
    public static Scanner sc = new Scanner(System.in);

    public GameMaster() {
        players = new ArrayList<>();
        dayTime = 180;
        rand = new Random();
    }

    /**
     * 参加人数、役職数、話し合い時間をプレイヤーからの入力によって設定し、
     * ゲームで使用する各キャラクターのインスタンスを生成する。
     * また、プレイヤーごとの名前設定と役職開示を行う。
     */
    public void setUp() {
        int numPlayers = inputInt("参加人数を入力してください:");
        int numWolfs;
        int numFortuneTellers;
        int numKnights;
        int numCitizens;

        while(true) {
            numWolfs = inputInt("人狼の数を入力してください:");
            if(numWolfs >= (numPlayers - numWolfs)) {
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

    /**
     * ゲーム自体を動作させるメソッド。
     * while文で、次の処理を繰り返す。
     * 1. dayTimeに設定された秒数分プログラムを待機（話し合い時間）
     * 2. actToPlayersメソッド（各プレイヤーのスキル発動）
     * 3. executionメソッド（投票結果からプレイヤーを一人処刑）
     * 4. judgeメソッド（終了判定を行う）
     * 5. raidメソッド（人狼により襲撃を行う。）
     * 6. judgeメソッド
     * judgeメソッドから、Trueが返された時、while文を抜け、メソッドを修了する。
     */
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

    /**
     * playersの中の生存プレイヤーの行動を実行する。
     * playersリストの中のdead=falseのプレイヤーインスタンスのactメソッドを呼び出す。
     */
    public void actToPlayers() {
        for(Player p: players) {
            if(!p.isDead()) {
                outputString(p.getName()+"さん、操作してください。");
                p.act(players);
                clearConsoleScreen();
            }
        }
    }

    /**
     * Player.executionListから最も得票数が多いプレイヤーを処刑する。
     * 得票数が同率となった場合は、同率のプレイヤーから一人ランダムに処刑する。
     */
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

    /**
     * WereWolf.raidListから、最も襲撃優先度が高いプレイヤーを襲撃する。
     * もし、襲撃優先度が同率のプレイヤーが複数いる場合、同率のプレイヤーからランダムに一人襲撃する。
     */
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
        if(Knight.protectedPlayers.contains(candidPlayer)) {
            outputString(candidPlayer.getName() + "さんが襲撃されましたが、騎士によって守られました。");
        } else {
            candidPlayer.setDead(true);
            outputString(candidPlayer.getName() + "さんが襲撃されました。");
        }

        WereWolf.raidList.clear();
        Knight.protectedPlayers.clear();
    }

    /**
     * 人狼側の人数と市民側の人数が同数である場合、"人狼の数が0になりました。市民側の勝利です。"という文字列を出力し、Trueを返す。
     * 人狼側の人数が0になった場合、"人狼の数が0になりました。市民側の勝利です。"という文字列を出力してTrueを返す。
     * 上記以外の場合はFalseを返す。
     * @return True: 判定可能/False: 判定不能
     */
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

    /**
     * 引数に渡された文字列を標準出力し、プレイヤーからの文字列の標準入力を返す。
     * Scannerクラスを利用する。
     * プレイヤーからの入力が空の場合には、標準入力を繰り返す。
     * 
     * @param message
     * @return result: 標準入力の文字列
     */
    public static String inputString(String message) {
        String result = "";
        while(result == "") {
            System.out.print(message);
            result = sc.nextLine();
        }
        return result;
    }

    /**
     * 引数に渡された文字列を標準出力し、プレイヤーからの整数の標準入力を返す。
     * Scannerクラスを利用する。
     * プレイヤーからの入力が整数以外の場合には、標準入力を繰り返す。
     * 
     * @param message
     * @return result: 標準入力の整数
     */
    public static int inputInt(String message) {
        int result;
        while(true) {
            try {
                System.out.print(message);
                result  = sc.nextInt();
                break;
            }catch(InputMismatchException e) {
                System.out.println("整数を入力してください。");
                sc.nextLine();
            }
        }
        return result;
    }

    /**
     * 引数に渡された文字列を標準出力する。
     * 
     * @param message
     */
    public static void outputString(String message) {
        System.out.println(message);
    }

    /**
     * コンソール画面のログを消去する。
     */
    public static void clearConsoleScreen() {
        System.out.print("Everything on the console will cleared");
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
