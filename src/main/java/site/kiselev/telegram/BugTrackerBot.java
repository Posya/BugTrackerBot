package site.kiselev.telegram;

import com.google.common.base.Joiner;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.kiselev.Config;
import site.kiselev.usersession.Result;
import site.kiselev.usersession.UserSession;
import site.kiselev.usersession.UserSessionFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Class for telegram bot
 */
@SuppressWarnings("unused")
public final class BugTrackerBot {

    private final TelegramBot bot;
    private int lastUpdateId = 0;
    private boolean isExit = false;

    private final Logger logger = LoggerFactory.getLogger(BugTrackerBot.class);
    private final UserSessionFactory userSessionFactory;


    public BugTrackerBot() {
        logger.debug("Creating new BugTrackerBot");
        bot = TelegramBotAdapter.build(Config.TELEGRAM_BOT_API_TOKEN);
        userSessionFactory = new UserSessionFactory();
    }

    public void loop() {
        logger.debug("Starting loop");
        while (!isExit) {
            GetUpdatesResponse updatesResponse = bot.execute(
                    new GetUpdates().offset(lastUpdateId));
            updatesResponse.updates().forEach(this::processUpdate);
        }
        logger.debug("Stopping loop");
    }

    private void processUpdate(Update update) {
        logger.trace("Processing update: {}", update);
        Message message = update.message();

        User user = message.from();

        try {
            UserSession us = userSessionFactory.getUserSession(user.username());

            Result result = us.process(message.text());
            if (result == null) {
                throw new NullPointerException("Result is null");
            }
            List<String> out = result.getOut();
            String[][] keyboard = result.getKeyboard();

            String textToSend = Joiner.on("\n").join(out);
            SendMessage sm = new SendMessage(message.chat().id(), textToSend);
            sm.parseMode(ParseMode.Markdown);

            if (keyboard.length > 0) {
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboard, true, true, false);
                sm.replyMarkup(keyboardMarkup);
            } else {

            }

            SendResponse sendResponse = bot.execute(sm);

            logger.trace("SendResponse: {}", sendResponse);
            if (sendResponse.message() == null) {
                logger.error("Can't send message: {}", sm);
            }
        } catch (Exception e) {
            logger.error("Error in processing user {}\nmessage {}\n{}", user, message, e);
            logger.trace(Arrays.toString(e.getStackTrace()));
        }

        // Done! Update was processed
        lastUpdateId = update.updateId() + 1;
    }

    public void exit() {
        isExit = true;
    }


}

