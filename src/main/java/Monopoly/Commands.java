package Monopoly;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

public class Commands extends ListenerAdapter {
    private static MessageReceivedEvent event;
    private static Auction auction = new Auction();

    @Override
    public void onMessageReceived (@NotNull MessageReceivedEvent event) {
        Commands.event = event;
        String[] args = event.getMessage().getContentRaw().toLowerCase().split(" ");
        String paramater = (args.length <= 1) ? "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" : args[1];
        boolean isMention = paramater.charAt(0) == '<' && paramater.charAt(1) == '@' && paramater.charAt(paramater.length() - 1) == '>'; // if a string is a mention


        // command, Bot phase, rolling phase, extra bool statements (with and)
        Map<String, Boolean[]> commandMatrix = new HashMap<>();
        commandMatrix.put("-clearplayers", new Boolean[]{Bot.phase == 0, true, true});
        commandMatrix.put("-addplayer", new Boolean[]{Bot.phase == 0, true, isMention});
        commandMatrix.put("-removeplayer", new Boolean[]{Bot.phase == 0, true, isMention});
        commandMatrix.put("-displayplayers", new Boolean[]{Bot.phase == 0, true, true});
        commandMatrix.put("-start", new Boolean[]{Bot.phase == 0, true, true});
        commandMatrix.put("-money", new Boolean[]{true, true, true});
        commandMatrix.put("-roll", new Boolean[]{Bot.phase == 1, Bot.rollingPhase == 0, true});
        commandMatrix.put("-buy", new Boolean[]{Bot.phase == 1, Bot.rollingPhase == 1, true});
        commandMatrix.put("-auction", new Boolean[]{Bot.phase == 1, Bot.rollingPhase == 1, true});
        commandMatrix.put("-bid", new Boolean[]{Bot.phase == 1, Bot.rollingPhase == 2, true});
        commandMatrix.put("-quit", new Boolean[]{Bot.phase == 1, Bot.rollingPhase == 2, true});
        commandMatrix.put("-boardpos", new Boolean[]{true, true, true});

        if (args[0].startsWith("-")) { // is command?
            Boolean[] preset = commandMatrix.get(args[0]);
            if (preset[0] && preset[1] && preset[2]) {
                CallFunction(args[0], args);
            }
        }
    }

    public static String GetDieEmote(int num) {
        switch (num) {
            case 1:
                return "<:dieface1:1011094692588429382>";
            case 2:
                return "<:dieface2:1011094691816689734>";
            case 3:
                return "<:dieface3:1011094690445152346>";
            case 4:
                return "<:dieface4:1011094689111363596>";
            case 5:
                return "<:dieface5:1011094688545116160>";
            case 6:
                return "<:dieface6:1011094687433637939>";
            default:
                return null;
        }
    }

    public static int PropertyOwned(int tile) {
        for (int i = 0; i < Player.playerObjects.size(); i++) {
            Player p = Player.playerObjects.get(i);
            if (p.getProperties().contains(tile)) {return i;}
        }
        return -1;
    }

    public static void EndTurn() {
        Bot.turn += 1; // ending turn
        Bot.turn %= Player.players.size();
        Bot.rollingPhase = 0;
        Say(Player.playerNames.get(Bot.turn) + "'s turn");
    }

    public static void DisplayTile(MessageReceivedEvent event, String message, int tile) {
        File f = new File(Paths.get("Commands.java").toAbsolutePath().getParent().toString() + "\\src\\main\\java\\Images\\" + tile + ".JPG");


        if (Objects.nonNull(f)) {
            event.getChannel().sendMessage(message).addFile(f).queue();
        } else {
            Say("Image Not Found");
        }
    }

    private static void ClearPlayers () {
        Player.players.clear();
        Say("Cleared Players");
    }

    private static void AddPlayer(String[] args) {
        if (Player.players.contains(args[1])) {
            return;
        }
        String name = Bot.bot.retrieveUserById(args[1].substring(2, args[1].length() - 1)).complete().getName();
        Player.players.add(args[1]);
        Player.playerNames.add(name);
        Say("Added Player");
    }

    private static void RemovePlayer(String[] args) {
        String name = Bot.bot.retrieveUserById(args[1].substring(2, args[1].length() - 1)).complete().getName();
        Player.players.remove(args[1]);
        Player.playerNames.remove(name);
        Say("Removed Player");
    }

    private static void DisplayPlayers() {
        String output = "```diff\n";
        for (int s = 0; s < Player.players.size(); s++) {
            output += "- " + Player.playerNames.get(s) + "\n";
        }
        output += "\n```";
        Say(output);
    }

    private static void Start() {
        Bot.phase = 1;
        Bot.rollingPhase = 0;
        Bot.turn = 0;

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < Player.players.size(); i++) {
            players.add(new Player(Player.playerNames.get(i), Player.players.get(i)));
        }
        Player.playerObjects = players;

        Say("Game Started");
        Say(Player.playerNames.get(Bot.turn) + "'s turn");
    }

    private static void DisplayMoney() {
        String name = event.getAuthor().getName();
        Say(name + " has $" + Player.playerObjects.get(Player.playerNames.indexOf(name)).getMoney());
    }

    private static void Roll() {
        if (event.getAuthor().getName().equals(Player.playerNames.get(Bot.turn))) {
            int die1 = (int) (Math.random() * 5 + 1);
            int die2 = (int) (Math.random() * 5 + 1);
            Player.playerObjects.get(Bot.turn).addBoardPos(die1 + die2);
            Say(GetDieEmote(die1) + GetDieEmote(die2));

            Player player = Player.playerObjects.get(Bot.turn);
            int boardPos = player.getBoardPos();


            if (BoardData.tileIDs[boardPos] == 0) {
                int owner = PropertyOwned(boardPos);

                if (owner == -1) { // option to buy / auction
                    Bot.rollingPhase = 1;
                    DisplayTile(event, "You Landed Here:\nCost: $" + BoardData.propertyData.get(boardPos)[0].toString(), boardPos);
                } else { // make them pay
                    if (Bot.turn != owner) {
                        int money = BoardData.propertyData.get(boardPos)[1 + Player.playerObjects.get(owner).getHouses(boardPos)];
                        player.subtractMoney(money);
                        player.addMoney(money);
                        Say("You Landed On " + Player.playerNames.get(owner) + "'s Property.\nYou Paid $" + money);
                    }
                    EndTurn();
                }
            } else {
                EndTurn();
            }
        }
    }

    private static void BuyProperty() {
        Player player = Player.playerObjects.get(Bot.turn);
        int tile = player.getBoardPos();
        player.subtractMoney(BoardData.propertyData.get(tile)[0]);
        player.addProperty(tile);
        EndTurn();
    }

    private static void AuctionProperty() {
        Bot.rollingPhase = 2;
        auction = new Auction(Bot.turn, Player.playerObjects.get(Bot.turn).getBoardPos());
        Say("The Auction has Started!");
    }

    private static void BidOnAuction(String[] args) {
        // finding whos the bidder
        int bidder = Player.playerNames.indexOf(event.getAuthor().getName());
        auction.Bid(bidder, Integer.parseInt(args[1]));
    }

    private static void QuitBid() {
        int bidder = Player.playerNames.indexOf(event.getAuthor().getName());
        auction.QuitBid(bidder);
    }

    private static void BoardPos() {
        Say(Player.playerNames.get(Bot.turn));
    }

    private static void CallFunction(String command, String[] args) {
        switch (command) {
            case "-clearplayers":
                ClearPlayers();
                break;
            case "-addplayer":
                AddPlayer(args);
                break;
            case "-removeplayer":
                RemovePlayer(args);
                break;
            case "-displayplayers":
                DisplayPlayers();
                break;
            case "-start":
                Start();
                break;
            case "-money":
                DisplayMoney();
                break;
            case "-roll":
                Roll();
                break;
            case "-buy":
                BuyProperty();
                break;
            case "-auction":
                AuctionProperty();
                break;
            case "-bid":
                BidOnAuction(args);
                break;
            case "-quit":
                QuitBid();
                break;
            case "-boardpos":
                BoardPos();
                break;
            default:
        }
    }

    public static void Say (String msg) {
        event.getChannel().sendMessage(msg).queue();
    }
}