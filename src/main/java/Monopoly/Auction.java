package Monopoly;

import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.List;

public class Auction {
    int property;
    int topBidder;
    int amount;
    boolean running;
    List<Integer> quitBidders;


    public Auction(int topBidder, int property) {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(Button.danger("quit", "Quit Auction"));

        Discord.append("Bid started with a starting bid of $1 from " + Player.playerNames.get(topBidder));
        Discord.addButtons(buttons);
        Discord.reply();
        this.topBidder = topBidder;
        this.property = property;
        quitBidders = new ArrayList<>();
        running = true;
        amount = 1;
    }

    public Auction() {
        this.topBidder = -1;
        this.property = -1;
        quitBidders = new ArrayList<>();
        running = false;
        amount = -1;
    }

    public void bid(int bidder, int amount) {
        if (quitBidders.contains(bidder) || Player.playerObjects.get(bidder).getMoney() < amount) {
            cantBid();
            return;
        }

        if (!running) {
            return;
        }

        if (amount > this.amount) {
            topBidder = bidder;
            this.amount = amount;
            announce();

//            for (int i = 0; i < Player.players.size(); i++) {
//                if (Player.playerObjects.get(i).getMoney() <= amount) {
//                    QuitBid(i, true);
//                }
//            }
            return;
        }

        cantBid();
    }

    public void quitBid(int player, boolean forced) {
        if (!running) {
            return;
        }

        if (topBidder == player) {
            Discord.append("Cant Quit As the Top Bidder! :)");
            Discord.reply();
            return;
        }

        if (!quitBidders.contains(player)) {
            quitBidders.add(player);
            Discord.append(Player.playerNames.get(player) + " Has Quit the Bid!");
            if (quitBidders.size() == Player.playerObjects.size() - 1) {
                finishAuction(forced);
            }
            else {
                Discord.reply();
            }
        }
    }

    private void finishAuction(boolean forced) {
        running = false;
        Discord.append(Player.playerNames.get(topBidder) + " Won the bid for $" + amount);
        Player.playerObjects.get(topBidder).subtractMoney(amount);
        Player.playerObjects.get(topBidder).addProperty(property);
        BoardData.propertyData.get(property).setOwner(topBidder);
        System.out.println(Player.playerObjects.get(topBidder).getMoney());
        Monopoly.endTurn();
    }

    private void announce() {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(Button.danger("quit", "Quit Auction"));

        Discord.append(Player.playerNames.get(topBidder) + " Bid $" + amount + "\nOriginal Price: $" + BoardData.propertyData.get(property).getCost());
        Discord.addButtons(buttons);
        Discord.reply();
    }

    private void cantBid() {
        Discord.append("Could not bid on this property!");
        Discord.reply();
    }
}