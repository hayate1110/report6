package jp.ac.uryukyu.ie.e225719;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerTest {
    /**
     * 投票メソッドが正しく機能しているか検証。
     * 検証手順
     * (1) プレイヤーリストを作る。中身は、プレイヤーのインスタンス3つ、人狼のインスタンス2つ、占い師のインスタンス1つ、騎士のインスタンス1つとする。
     * (2) プレイヤーリストの、最初のプレイヤーの、投票メソッドを呼び出す。
     * (3) 標準入力には0を入力する。すなわち、プレイヤーリストの0番目のプレイヤーに投票する。
     * (4) 投票リストから、0番目のプレイヤーの得票数を取得する。
     * (5) 投票回数は1回のため得票数が1になっていることを期待する。
     */
    static StandardInputSnatcher in = new StandardInputSnatcher();
    static StandardOutputSnatcher out = new StandardOutputSnatcher();

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
    void voteTest() {
        ArrayList<Player> players = new ArrayList<>();
        Player.executionList.clear();
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

        Player player = players.get(0);

        in.inputln(0);
        player.vote(players);
        

        int actual_score = Player.executionList.get(players.get(0));
        int expect_score = 1;

        assertEquals(expect_score, actual_score);
    }
}
