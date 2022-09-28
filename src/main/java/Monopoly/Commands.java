package Monopoly;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Commands extends ListenerAdapter {
    public static MessageReceivedEvent event;
    public static Auction auction = new Auction();

    @Override
    public void onMessageReceived (@NotNull MessageReceivedEvent event) {
        this.event = event;
        String[] args = event.getMessage().getContentRaw().toLowerCase().split(" ");

        if (args[0].startsWith("-")) { // is command?
            if (Bot.phase == 0) {
                if (args[0].equals("-clearplayers")) {
                    Player.players.clear();
                    event.getChannel().sendMessage("Cleared Players").queue();
                }
                if (args[0].equals("-addplayer") && (args[1].charAt(0) == '<' && args[1].charAt(1) == '@' && args[1].charAt(args[1].length() - 1) == '>')) {
                    if (Player.players.contains(args[1])) {
                        return;
                    }
                    String name = Bot.bot.retrieveUserById(args[1].substring(2, args[1].length() - 1)).complete().getName();
                    Player.players.add(args[1]);
                    Player.playerNames.add(name);
                    event.getChannel().sendMessage("Added Player").queue();
                }

                else if (args[0].equals("-removeplayer") &&
                        (args[1].charAt(0) == '<' && args[1].charAt(1) == '@' && args[1].charAt(args[1].length() - 1) == '>')) {
                    String name = Bot.bot.retrieveUserById(args[1].substring(2, args[1].length() - 1)).complete().getName();
                    Player.players.remove(args[1]);
                    Player.playerNames.remove(name);
                    event.getChannel().sendMessage("Removed Player").queue();
                }

                else if (args[0].equals("-displayplayers")) {
                    String output = "```diff\n";
                    for (int s = 0; s < Player.players.size(); s++) {
                        output += "- " + Player.playerNames.get(s) + "\n";
                    }
                    output += "\n```";
                    event.getChannel().sendMessage(output).queue();
                }

                else if (args[0].equals("-start")) {
                    Bot.phase = 1;
                    Bot.rollingPhase = 0;
                    Bot.turn = 0;

                    List<Player> players = new ArrayList<>();
                    for (int i = 0; i < Player.players.size(); i++) {
                        players.add(new Player(Player.playerNames.get(i), Player.players.get(i)));
                    }
                    Player.playerObjects = players;

                    event.getChannel().sendMessage("Game Started").queue();
                    event.getChannel().sendMessage(Player.playerNames.get(Bot.turn) + "'s turn").queue();
                }
            }

            else if (args[0].equals("-money")) {
                String name = event.getAuthor().getName();
                event.getChannel().sendMessage(name + " has $" + Player.playerObjects.get(Player.playerNames.indexOf(name)).getMoney()).queue();
            }

            else if (Bot.phase == 1) { // Game is playing
                if (Bot.rollingPhase == 0) {
                    if (args[0].equals("-roll")) {
                        if (event.getAuthor().getName().equals(Player.playerNames.get(Bot.turn))) {
                            int die1 = (int) (Math.random() * 5 + 1);
                            int die2 = (int) (Math.random() * 5 + 1);
                            Player.playerObjects.get(Bot.turn).addBoardPos(die1 + die2);
                            event.getChannel().sendMessage(GetDieEmote(die1) + GetDieEmote(die2)).queue();

                            Player player = Player.playerObjects.get(Bot.turn);
                            int boardPos = player.getBoardPos();


                            if (BoardData.tileIDs[boardPos] == 0) {
                                int owner = PropertyOwned(boardPos);

                                if (owner == -1) { // option to buy / auction
                                    Bot.rollingPhase = 1;
                                    DisplayTile(event, "You Landed Here:\nCost: $" + BoardData.propertyData.get(boardPos)[0].toString(), boardPos);
                                } else { // make them pay
                                    int money = BoardData.propertyData.get(boardPos)[1 + player.getHouses(boardPos)];
                                    player.subtractMoney(money);
                                    player.addMoney(money);
                                    event.getChannel().sendMessage("You Landed On " + Player.playerNames.get(owner) + "'s Property.\nYou Paid $" + money);
                                    EndTurn();
                                }
                            } else {
                                EndTurn();
                            }
                        }
                    }
                }
                // buying / auctioning property
                else if (Bot.rollingPhase == 1) {
                    if (event.getAuthor().getName().equals(Player.playerNames.get(Bot.turn))) {
                        if (args[0].equals("-buy")) {
                            Player player = Player.playerObjects.get(Bot.turn);
                            int tile = player.getBoardPos();
                            player.subtractMoney(BoardData.propertyData.get(tile)[0]);
                            player.addProperty(tile);
                            EndTurn();
                        }

                        if (args[0].equals("-auction")) {
                            Bot.rollingPhase = 2;
                            auction = new Auction(Bot.turn, Player.playerObjects.get(Bot.turn).getBoardPos());
                            event.getChannel().sendMessage("The Auction has Started!").queue();
                        }
                    }
                }

                // inside of an auction
                else if (Bot.rollingPhase == 2) {
                    if (args[0].equals("-bid")) {
                        // finding whos the bidder
                        int bidder = Player.playerNames.indexOf(event.getAuthor().getName());
                        auction.Bid(bidder, Integer.parseInt(args[1]));
                    }

                    if (args[0].equals("-quit")) {
                        int bidder = Player.playerNames.indexOf(event.getAuthor().getName());
                        auction.QuitBid(bidder);
                    }
                }
            }
        }
    }

    @Override
    public void onGuildReady (@NotNull GuildReadyEvent event) {
        event.getGuild().upsertCommand("roll", "rolls the two dice").queue();
        event.getGuild().upsertCommand("addplayer", "adds a player before the game starts").queue();
        event.getGuild().upsertCommand("removeplayer", "removes a player before the game starts").queue();
        event.getGuild().upsertCommand("clearplayers", "clears the list of people playing the game before it starts").queue();
        event.getGuild().upsertCommand("displayplayers", "shows whos playing").queue();
        event.getGuild().upsertCommand("start", "starts the game").queue();
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
        event.getChannel().sendMessage(Player.playerNames.get(Bot.turn) + "'s turn").queue();
    }

    public static void DisplayTile(MessageReceivedEvent event, String message, int tile) {
        File f = new File(Paths.get("Commands.java").toAbsolutePath().getParent().toString() + "\\src\\main\\java\\Images\\" + tile + ".JPG");


        if (Objects.nonNull(f)) {
            event.getChannel().sendMessage(message).addFile(f).queue();
        } else {
            event.getChannel().sendMessage("Image Not Found").queue();
        }
    }
}
