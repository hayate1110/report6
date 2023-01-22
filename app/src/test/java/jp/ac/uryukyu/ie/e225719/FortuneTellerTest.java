package jp.ac.uryukyu.ie.e225719;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class FortuneTellerTest {
    StandardInputSnatcher in = PlayerTest.in;
    StandardOutputSnatcher out = PlayerTest.out;

    @BeforeEach
    public void before() {
        System.setOut(out);
        System.setIn(in);
    }

    @AfterEach
    public void after() {
        System.setOut(null);
        System.setIn(null);
    }

    @Test
    void divineTest() {
    /**
     * 占いメソッドが正しく機能しているか検証。
     * 検証手順
     * (1) プレイヤーリストを作る。中身は、プレイヤーのインスタンス3つ、人狼のインスタンス2つ、占い師のインスタンス1つ、騎士のインスタンス1つとする。
     * (2) 占い師が保存された変数fのdivineメソッドを呼び出す。
     * (3) 標準入力には3と1を入力する。すなわち、占う対象のプレイヤーにプレイヤーリストの四番目のプレイヤー（人狼）を指定する。
     * (4) 占い師のdivineメソッドの出力結果をdivine_resultフィールドに保存しているため、その値を取得する。
     * (5) 今回、占う対象は人狼を指定しているため、divineメソッドの出力が、"w0さんの役職は人狼です。"になっていることを期待する。
     */
    
        ArrayList<Player> players = new ArrayList<>();
        Player.executionList.clear();
        WereWolf.raidList.clear();
        Knight.protectedPlayers.clear();

        for (int i = 0; i < 3; i++) {
            Player c = new Citizen();
            c.setName("c" + i);
            players.add(c);
        }
        for (int i = 0; i < 2; i++) {
            Player w = new WereWolf();
            w.setName("w" + i);
            players.add(w);
        }
        FortuneTeller f = new FortuneTeller();
        f.setName("f");
        players.add(f);

        Knight k = new Knight();
        k.setName("k");
        players.add(k);

        in.inputln(3);
        in.inputln(1);
        f.divine(players);
        
        String actual_msg = f.divine_result;
        String expect_msg = players.get(3).getName() + "さんの役職は" + players.get(3).getRole() + "です。";

       assertEquals(expect_msg, actual_msg);
    }
}