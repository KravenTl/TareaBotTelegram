package umg.programacionII;

import Bot.TareaBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            TareaBot bot = new TareaBot();
            botsApi.registerBot(bot);
            System.out.println("El bot esta funcionando");

        }catch (Exception ex){
            System.out.println("error"+ex.getMessage());
        }
    }
}