package site.kiselev;

import site.kiselev.telegram.BugTrackerBot;

/**
 * Main class for BugTracker Telegram Bot project.
 */
public class App {
    public static void main(String[] args) {

        new BugTrackerBot().loop();

    }
}
