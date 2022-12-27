package jp.ac.uryukyu.ie.e225719;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameMaster {
    public static ArrayList<Player> players;
    private int dayTime;

    public static Scanner sc = new Scanner(System.in);

    public GameMaster() {
        players = new ArrayList<>();
        dayTime = 180;
    }

    public void setUp() {

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

    }

    public void execution() {

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
