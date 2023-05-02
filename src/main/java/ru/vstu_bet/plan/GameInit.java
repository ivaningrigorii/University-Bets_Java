package ru.vstu_bet.plan;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameInit implements ServletContextListener {
    private ScheduledExecutorService scheduler;
    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        Runnable command = new MethodGameInit(event.getServletContext());
        long initialDelay = 30;
        long period = 10;
        TimeUnit unit = TimeUnit.SECONDS;

        scheduler.scheduleAtFixedRate(command, initialDelay, period, unit);
    }
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }
}
