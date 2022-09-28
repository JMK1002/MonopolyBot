package Monopoly;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import io.github.cdimascio.dotenv.Dotenv;

public class Bot {
    // phase 0: before the game starts adding / removing players
    public static int phase = 0;
    // phase 0: roll die (the persons turn is playerNames.get(turn)
    public static int rollingPhase = -1;
    public static int turn = -1;
    public static JDA bot;

    public static void main(String[] args) throws LoginException {
        Dotenv dotenv = Dotenv.configure().filename("\\.idea\\key.env").load();
        String Token = dotenv.get("TOKEN");
        BoardData.InstantiateData();
        bot = (JDA) JDABuilder.createDefault(Token)
                .setEnabledIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                .setActivity(Activity.playing("Monopoly"))
                .addEventListeners(new Commands())
                .build();
    }


}
