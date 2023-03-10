package jp.ac.uryukyu.ie.e225719;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class WereWolfTest {
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
    void voteRaidTest() {
    /**
     * 襲撃投票メソッドが正しく機能しているか検証。
     * 検証手順
     * (1) プレイヤーリストを作る。中身は、プレイヤーのインスタンス3つ、人狼のインスタンス2つ、占い師のインスタンス1つ、騎士のインスタンス1つとする。
     * (2) プレイヤーリストの、四番目の人狼インスタンスの、襲撃投票メソッドを呼び出す。
     * (3) 標準入力には0と3を入力する。すなわち、プレイヤーリストの0番目のプレイヤーに襲撃優先度3を追加する。
     * (4) 襲撃投票リストから、襲撃投票されたプレイヤーの合計襲撃優先度を取得する。
     * (5) 今回、襲撃優先度度3を指定しているため、合計襲撃優先度が3になっていることを期待する。
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

        WereWolf w = (WereWolf)players.get(4);

        in.inputln(0);
        in.inputln(3);
        

        w.voteRaid(players);

        Player votedPlayer = players.get(0);
        
        int actual_score = WereWolf.raidList.get(votedPlayer);
        int expect_score = 3;

        assertEquals(expect_score, actual_score);
    }
}
