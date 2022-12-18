package jp.ac.uryukyu.ie.e225719;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WereWolf extends Player {
    public static Map<Player,Integer> raidList = new HashMap<>();

    public WereWolf(String name) {
        super(name);
    }

    public void raid(ArrayList<Player> players, Scanner sc) {
        ArrayList<Citizen> citizens = new ArrayList<>();
        for (Player p: players) {
            if (p instanceof Citizen && !p.isDead()) {
                Citizen c = (Citizen)p;
                citizens.add(c);
            }
        }

        for(int i=0;i <citizens.size();i++) {
            Citizen c = citizens.get(i);
            System.out.println(i + ":" +c.getName());
        }

        while (true) {
            try {
                System.out.print("襲撃したいプレイヤーの番号を入力してください:");
                int numPlayer = sc.nextInt();
                Citizen c = citizens.get(numPlayer);

                System.out.println("殺害優先度の合計がもっとも高いプレイヤーが襲撃されます。");
                System.out.print("1~3の殺害優先度を入力してください:");
                int point = sc.nextInt();

                if(point>0 && point<4) {
                    int v = raidList.getOrDefault(c, 0);
                    v += 1;
                    raidList.put(c, v);
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

}
