package jp.ac.uryukyu.ie.e225719;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class KnightTest {
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
    void guardTest() {
    /**
     * 防護メソッドが正しく機能しているか検証。
     * 検証手順
     * (1) プレイヤーリストを作る。中身は、プレイヤーのインスタンス3つ、人狼のインスタンス2つ、占い師のインスタンス1つ、騎士のインスタンス1つとする。
     * (2) 変数kに保存されたKnightクラスのインスタンスのguardメソッドを呼び出す。
     * (3) 標準入力には0を入力する。すなわち、プレイヤーリストの0番目のプレイヤーをKnight.protectedPlayersリストに追加する。
     * (4) protectedPlayersリストに最初に保存されているプレイヤーを取得する。
     * (5) 今回、プレイヤーリストの最初のプレイヤーを防護対象に指定しているため、protectedPlayersリストに保存されたプレイヤーも同じインスタンスになっていることを期待する。
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

        in.inputln(0);
        k.guard(players);
        
        Player actual_player = Knight.protectedPlayers.get(0);
        Player expect_player = players.get(0);

        assertEquals(expect_player,actual_player);
    }
}
