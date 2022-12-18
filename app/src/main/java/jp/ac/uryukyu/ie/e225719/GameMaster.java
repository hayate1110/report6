package jp.ac.uryukyu.ie.e225719;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameMaster {
    private ArrayList<Player> players;
    private int dayTime;

    public GameMaster() {
        players = new ArrayList<>();
        dayTime = 60;
    }

    public void setUp(Scanner sc) {
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

                break;
            } catch(InputMismatchException e) {
                sc.nextLine();
                System.out.println("整数を入力してください。");
            }
        }
    }

    public void play() {
    }

    public Player execution() {
        return new Player("dummy");
    }

    public Player raid() {
        return new Player("dummy");
    }
}
