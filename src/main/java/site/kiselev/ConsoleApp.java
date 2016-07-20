package site.kiselev;

import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.kiselev.usersession.Result;
import site.kiselev.usersession.UserSession;
import site.kiselev.usersession.UserSessionFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Console version. For debug
 */
public class ConsoleApp {
    private static final String USERNAME = "pavel_kiselev";
    private final Logger logger = LoggerFactory.getLogger(ConsoleApp.class);
    private final UserSessionFactory userSessionFactory;

    @SuppressWarnings("FieldCanBeLocal")
    private boolean isExit = false;

    private ConsoleApp() {
        logger.debug("Creating new BugTrackerBot");
        userSessionFactory = new UserSessionFactory();
    }

    private void loop() {
        logger.debug("Starting loop");
        while (!isExit) {
            Scanner scanner = new Scanner(System.in);
            System.out.print(">>> ");
            String input = scanner.nextLine();
            processUpdate(input);
        }
        logger.debug("Stopping loop");
    }

    private void processUpdate(String update) {
        logger.trace("Processing update: {}", update);

        try {
            UserSession us = userSessionFactory.getUserSession(USERNAME);

            Result result = us.process(update);
            if (result == null) {
                throw new NullPointerException("Result is null");
            }
            List<String> out = result.getOut();
            String[][] keyboard = result.getKeyboard();

            String textToSend = Joiner.on("\n").join(out);
            System.out.println(textToSend);

            System.out.println("==========================================");
            if (keyboard.length > 0) {
                for (String[] aKeyboard : keyboard) {
                    for (String anAKeyboard : aKeyboard) {
                        System.out.printf("%s\t", anAKeyboard);
                    }
                    System.out.println();
                }
            } else {
                System.out.println("");
            }
            System.out.println("==========================================");

//            SendResponse sendResponse = bot.execute(sm);

//            logger.trace("SendResponse: {}", sendResponse);
        } catch (Exception e) {
            logger.error("Error in processing user {}\nmessage {}\n{}", USERNAME, update, e);
            logger.trace(Arrays.toString(e.getStackTrace()));
        }

        // Done! Update was processed
    }

    public static void main(String[] args) {
        new ConsoleApp().loop();
    }

}
