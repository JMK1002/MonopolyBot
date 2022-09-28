package Monopoly;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.*;

public class Bot {
    // phase 0: before the game starts adding / removing players
    public static int phase = 0;
    // phase 0: roll die (the persons turn is playerNames.get(turn)
    public static int rollingPhase = -1;
    public static int turn = -1;
    public static JDA bot;

    public static void main(String[] args) throws LoginException {
        BoardData.InstantiateData();
        String Token = "MTAxMDk4NzcyOTQzMTA0NDIwOA.G3kBFb.LDxWI18grYtZ2ycOp5_pWAjKd5uFxcjJ-2z0js";
        bot = (JDA) JDABuilder.createDefault(Token)
                .setEnabledIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                .setActivity(Activity.playing("Monopoly"))
                .addEventListeners(new Commands())
                .build();
    }


}
