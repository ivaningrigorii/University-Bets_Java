package ru.vstu_bet.controllers.players;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vstu_bet.models.beans.db.Player;
import ru.vstu_bet.models.beans.db.RandomNamePlayer;
import ru.vstu_bet.models.beans.db.RandomPhotoPlayer;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.beans.other.FullPlayerBean;
import ru.vstu_bet.models.handlers.PlayerHandler;
import ru.vstu_bet.models.handlers.RndNameHandler;
import ru.vstu_bet.models.handlers.RndPhotoHandler;
import ru.vstu_bet.models.handlers.UserHandler;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@WebServlet("/players/add-players")
public class AddPlayerServlet extends HttpServlet {
    private int[] params = {
        25, 50, 100
    };
    private double[] price = {
            200, 500, 800
    };

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Random rnd = new Random();
        int finder = Integer.parseInt(req.getParameter("finder"));
        User user = (User) req.getSession().getAttribute("user");
        findPlayer: if ((user != null) && (user.getLogin() !=null)) {
            if (((user.getScore()-price[finder])<0)){
                req.setAttribute("exp", "exp");
                break findPlayer;
            }
            List<RandomNamePlayer> idNames = new RndNameHandler().getIds();
            List<RandomPhotoPlayer> idPhotos = new RndPhotoHandler().getIds();

            Player player = new Player();
            int rnd_id_name = rnd.nextInt(idNames.size());
            int rnd_id_photo = rnd.nextInt(idPhotos.size());

            player.setEndurance(rnd.nextInt(params[finder]+1));
            player.setStren(rnd.nextInt(params[finder]+1));
            player.setStren_mind(rnd.nextInt(params[finder]+1));

            player.setFk_name(idNames.get(rnd_id_name).getId());
            player.setFk_photo(idPhotos.get(rnd_id_photo).getId());
            player.setFk_user(user.getId());

            new PlayerHandler().add(player);
            user.setScore(user.getScore()-price[finder]);
            new UserHandler().update(user);
            req.getSession().setAttribute("user", user);

            String name = idNames.get(rnd_id_name).getName();
            String photo = idPhotos.get(rnd_id_photo).getPhoto();
            req.setAttribute("player", new FullPlayerBean(player, name, photo));
        }
        req.getRequestDispatcher("/views/players/add-players.jsp")
                .forward(req, resp);
    }
}
