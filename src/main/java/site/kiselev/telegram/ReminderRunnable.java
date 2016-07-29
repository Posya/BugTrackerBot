package site.kiselev.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardHide;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.LoggerFactory;
import site.kiselev.datastore.Datastore;

import java.util.*;
import java.util.stream.Collectors;

import static site.kiselev.telegram.Reminder.REMINDERS_KEY;

/**
 * Runnable thread for reminders
 */
class ReminderRunnable implements Runnable {
    private final TelegramBot bot;
    private final Datastore datastore;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(ReminderRunnable.class);


    ReminderRunnable(TelegramBot bot, Datastore datastore) {
        this.bot = bot;
        this.datastore = datastore;
    }

    @Override
    public void run() {
        logger.trace("Checking for new reminders");

        long timestamp = new Date().getTime();
        logger.trace("Current timestamp: {}", timestamp);

        Set<String> reminderStrings = datastore.zRangeLessThen(REMINDERS_KEY, timestamp);

        List<Reminder> reminders = reminderStrings.stream()
                .map(Reminder::fromJSON)
                .collect(Collectors.toList());

        reminders.stream()
                .filter(r -> send(r.getUserName(), r.getMessage()))
                .forEach(r -> datastore.zRem(REMINDERS_KEY, r.toJSON()));


    }

    private boolean send(String username, String message) {
        logger.trace("Sending reminder to username = {}: {}", username, message);
        try {
            SendMessage sm = new SendMessage("@" + username, message);
            sm.parseMode(ParseMode.Markdown);

            Keyboard replyKeyboardHide = new ReplyKeyboardHide();
            sm.replyMarkup(replyKeyboardHide);

            SendResponse sendResponse = bot.execute(sm);

            logger.trace("SendResponse: {}", sendResponse);
            if (sendResponse.message() == null) {
                logger.error("Can't send reminder: {}", sm);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error in sending reminder with username = {}; message = {}; {}", username, message, e);
            logger.trace(Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }
}
