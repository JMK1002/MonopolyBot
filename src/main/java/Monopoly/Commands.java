package Monopoly;

import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

        // command, Main phase, rolling phase, extra bool statements (with and)
        Map<String, Boolean[]> commandMatrix = new HashMap<>();
        commandMatrix.put("-clearplayers", new Boolean[]{Main.phase == 0, true, true});
        commandMatrix.put("-addplayer", new Boolean[]{Main.phase == 0, true, isMention});
        commandMatrix.put("-removeplayer", new Boolean[]{Main.phase == 0, true, isMention});
        commandMatrix.put("-displayplayers", new Boolean[]{Main.phase == 0, true, true});
        commandMatrix.put("-start", new Boolean[]{Main.phase == 0, true, true});
        commandMatrix.put("-roll", new Boolean[]{Main.phase == 1, Main.rollingPhase == 0, true});
        commandMatrix.put("-buy", new Boolean[]{Main.phase == 1, Main.rollingPhase == 1, true});
        commandMatrix.put("-auction", new Boolean[]{Main.phase == 1, Main.rollingPhase == 1, true});
        commandMatrix.put("-bid", new Boolean[]{Main.phase == 1, Main.rollingPhase == 2, true});
        commandMatrix.put("-quit", new Boolean[]{Main.phase == 1, Main.rollingPhase == 2, true});
        commandMatrix.put("-money", new Boolean[]{true, true, true});
        commandMatrix.put("-boardpos", new Boolean[]{true, true, true});
        commandMatrix.put("-properties", new Boolean[]{true, true, true});

        if (args[0].startsWith("-")) { // is command?
            Boolean[] preset = commandMatrix.get(args[0]);
            if (preset[0] && preset[1] && preset[2]) {
                try {
                    CallFunction(args[0], args);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void CallFunction(String command, String[] args) throws IOException {
        switch (command) {
            case "-clearplayers" -> ClearPlayers();
            case "-addplayer" -> AddPlayer(args);
            case "-removeplayer" -> RemovePlayer(args);
            case "-displayplayers" -> DisplayPlayers();
            case "-start" -> Start();
            case "-money" -> DisplayMoney();
            case "-roll" -> Roll();
            case "-buy" -> BuyProperty();
            case "-auction" -> AuctionProperty();
            case "-bid" -> BidOnAuction(args);
            case "-quit" -> QuitBid();
            case "-boardpos" -> BoardPos();
//          case "-properties" -> DisplayProperties(Main.turn);
        }
    }

//    private static void DisplayProperties(MessageReceivedEvent event, int player) throws IOException {
//        String message = "Properties in their respective order: ";
//        ArrayList<Integer> tiles = Player.playerObjects.get(player).getProperties();
//        ImageCombine combiner = new ImageCombine();
//        BufferedImage rootImage = ImageIO.read(new File(Paths.get("Commands.java").toAbsolutePath().getParent().toString() + "\\src\\main\\java\\Images\\" + tiles.get(0) + ".JPG"));
//
//        for (int i = 0; i < tiles.size(); i++) {
//            message += tiles.get(i) + ", ";
//        }
//
//        for (int i = 1; i < tiles.size(); i++) {
//            File f = new File(Paths.get("Commands.java").toAbsolutePath().getParent().toString() + "\\src\\main\\java\\Images\\" + tiles.get(0) + ".JPG");
//            if (Objects.nonNull(f)) {
//                rootImage = combiner.combine(rootImage, ImageIO.read(f), 200);
//            } else {
//                Say("Image Not Found");
//            }
//            // memory leak?
//        }

    private static void DisplayProperties(int player) {
        String message = "Properties in their respective order: ";
        ArrayList<Integer> tiles = Player.playerObjects.get(player).getProperties();
        int numOfMessages = (int) Math.ceil(tiles.size() / 5);

        for (int i = 0; i < tiles.size(); i++) {
            message += tiles.get(i) + ", ";
        }
        Say(message);

        for (int j = 0; j < numOfMessages; j++) {
            message = "";
            for (int i = 0; i < Math.min(5, tiles.size() - 5 * j); i++) {
                message += BoardData.imageLinks.get(tiles.get(i)) +  " ";
            }
            Say(message);
        }
    }

    private static String GetDieEmote(int num) {
        return switch (num) {
            case 1 -> "<:dieface1:1011094692588429382>";
            case 2 -> "<:dieface2:1011094691816689734>";
            case 3 -> "<:dieface3:1011094690445152346>";
            case 4 -> "<:dieface4:1011094689111363596>";
            case 5 -> "<:dieface5:1011094688545116160>";
            case 6 -> "<:dieface6:1011094687433637939>";
            default -> null;
        };
    }

    private static int PropertyOwned(int tile) {
        for (int i = 0; i < Player.playerObjects.size(); i++) {
            Player p = Player.playerObjects.get(i);
            if (p.getProperties().contains(tile)) {return i;}
        }
        return -1;
    }

    public static void EndTurn() {
        Main.turn += 1; // ending turn
        Main.turn %= Player.players.size();
        Main.rollingPhase = 0;
        Say(Player.playerNames.get(Main.turn) + "'s turn");
    }

    public static void EndTurn(boolean doubles) {
        if (doubles) {
            Main.rollingPhase = 0;
            Say(Player.playerNames.get(Main.turn) + " Rolled Doubles, So They Can Go Again!");
            Player.playerObjects.get(Main.turn).setRolledDoubles(false);
        }
        else {
            EndTurn();
        }
    }

    private static void DisplayTile(MessageReceivedEvent event, String message, int tile) {
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
        String name = Main.bot.retrieveUserById(args[1].substring(2, args[1].length() - 1)).complete().getName();
        Player.players.add(args[1]);
        Player.playerNames.add(name);
        Say("Added Player");
    }

    private static void RemovePlayer(String[] args) {
        String name = Main.bot.retrieveUserById(args[1].substring(2, args[1].length() - 1)).complete().getName();
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
        Main.phase = 1;
        Main.rollingPhase = 0;
        Main.turn = 0;

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < Player.players.size(); i++) {
            players.add(new Player(Player.playerNames.get(i), Player.players.get(i)));
        }
        Player.playerObjects = players;

        Say("Game Started");
        Say(Player.playerNames.get(Main.turn) + "'s turn");
    }

    private static void DisplayMoney() {
        String name = event.getAuthor().getName();
        Say(name + " has $" + Player.playerObjects.get(Player.playerNames.indexOf(name)).getMoney());
    }

    private static void Roll() {
        if (event.getAuthor().getName().equals(Player.playerNames.get(Main.turn))) {
            Dice dice = new Dice();
            Player player = Player.playerObjects.get(Main.turn);
            int dice1 = dice.getDie1();
            int dice2 = dice.getDie2();
            int sum = dice.getSum();
            int boardPos = player.getBoardPos();
            boolean doubles = dice.rolledDoubles();


            // checking for doubles
            if (doubles) {
                player.setRolledDoubles(true);
            }

            player.addBoardPos(sum);
            Say(GetDieEmote(dice1) + GetDieEmote(dice2));

            Property property = BoardData.propertyData.get(boardPos);
            if (property != null && (property.getTileType() == 0 || property.getTileType() == 1)) {
                int owner = property.getOwner();

                if (!property.hasOwner()) { // option to buy / auction
                    Main.rollingPhase = 1;
                    DisplayTile(event, "You Landed Here:\nCost: $" + BoardData.propertyData.get(boardPos).getCost(), boardPos);
                } else { // make them pay
                    if (Main.turn != owner) {
                        Player.PayProperty(boardPos, owner, player);
                    }
                    else {
                        Say("Landed on your own property!");
                    }
                    EndTurn(player.getRolledDoubles());
                }
            }
            else {
                EndTurn(player.getRolledDoubles());
            }
        }
    }

    private static void BuyProperty() {
        Player player = Player.playerObjects.get(Main.turn);
        int tile = player.getBoardPos();
        int amount = BoardData.propertyData.get(tile).getCost();
        Say("Bought for $" + amount);
        player.subtractMoney(amount);
        player.addProperty(tile);
        EndTurn(player.getRolledDoubles());
    }

    private static void AuctionProperty() {
        Main.rollingPhase = 2;
        auction = new Auction(Main.turn, Player.playerObjects.get(Main.turn).getBoardPos());
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
        String author = event.getAuthor().getName();
        Say(author + " is on tile " + Player.playerObjects.get(Player.playerNames.indexOf(author)).getBoardPos());
    }

//    private static void Imprison(int player) {
//        Player playerObject = Player.playerObjects.get(player);
//
//        playerObject.setBoardPos(10);
//        playerObject.setJailTime(3);
//    }

    public static void Say (String msg) {
        event.getChannel().sendMessage(msg).queue();
    }
}