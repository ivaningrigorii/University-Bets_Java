package ru.vstu_bet.models.handlers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import ru.vstu_bet.models.PhotoPathGenerator;
import ru.vstu_bet.models.beans.db.Sport;
import ru.vstu_bet.models.beans.db.User;

import java.io.File;

public class UserHandler {

    public User init(String log, String pas) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        User user = null;
        try  {
            user = (User) em.createQuery(
                    "select u from User u where u.login = :l and u.password = :p"
            ).setParameter("l", log).setParameter("p", pas).getSingleResult();
        } finally {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            if (emf.isOpen()) emf.close();
        }
        return user;
    }

    public User update(User user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            user = em.merge(user);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }

        return user;
    }

    public void create(User user) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }
    }

    public User updatePhoto(String fileName, User user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        String lastName = user.getPhoto();
        try {
            if (lastName!=null) {
                File file = new File(PhotoPathGenerator.getPath() + "\\users" + "\\" +
                        lastName);
                file.delete();
            }

            user.setPhoto(fileName);
            user = update(user);
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }

        return user;
    }

    public User get(int id_user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        User user = new User();
        try {
            em.getTransaction().begin();
            user = em.find(User.class, id_user);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }
        return user;
    }



}
