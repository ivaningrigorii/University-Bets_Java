package ru.vstu_bet.models.beans.other.bets;

import lombok.Data;
import ru.vstu_bet.models.beans.db.Bets;
import ru.vstu_bet.models.beans.db.Game;
import ru.vstu_bet.models.beans.db.Team;

import java.util.Date;
import java.util.List;

@Data
public class BGBeand {
    private int id_game;
    private int id_bet;
    private int exp_res;
    private int real_res;
    private double money;
    private String date;

    private String t1;
    private String t2;

    public BGBeand() {
    }

    public BGBeand(Game g, Bets b, List<Team> t) {
        this.id_game = g.getId();
        this.id_bet = b.getId();
        this.real_res = g.getGame_result();
        this.exp_res = b.getExpected_result();
        this.money = b.getMoney();
        this.date =
                String.valueOf(g.getDate_game().getDate()) + "/" +
                String.valueOf(g.getDate_game().getMonth()) + "/" +
                String.valueOf(g.getDate_game().getYear()%100);
        t1 = t.get(0).getName_team();
        t2 = t.get(1).getName_team();
    }
}
