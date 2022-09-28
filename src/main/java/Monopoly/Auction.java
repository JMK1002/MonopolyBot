package Monopoly;

import java.util.ArrayList;
import java.util.List;

public class Auction {
    int property;
    int topBidder;
    int amount;
    boolean running;
    List<Integer> quitBidders;


    public Auction(int topBidder, int property) {
        Commands.event.getChannel().sendMessage("Bid started with a starting bid of $1 from " + Player.playerNames.get(topBidder)).queue();
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

    public void Bid (int bidder, int amount) {
        if (quitBidders.contains(bidder) || Player.playerObjects.get(bidder).getMoney() < amount) {
            CantBid();
            return;
        }

        if (!running) {
            return;
        }

        if (amount > this.amount) {
            topBidder = bidder;
            this.amount = amount;
            Announce();

            for (int i = 0; i < Player.players.size(); i++) {
                if (Player.playerObjects.get(i).getMoney() <= amount) {
                    QuitBid(i);
                }
            }
            return;
        }

        CantBid();
    }

    public void QuitBid(int player) {
        if (!running) {
            return;
        }

        if (!quitBidders.contains(player)) {
            quitBidders.add(player);
            Commands.event.getChannel().sendMessage(Player.playerNames.get(player) + " Has Quit the Bid!").queue();
            if (quitBidders.size() == Player.playerObjects.size() - 1) {
                FinishAuction();
            }
        }
    }

    private void FinishAuction() {
        running = false;
        Commands.event.getChannel().sendMessage(Player.playerNames.get(topBidder) + " Won the bid for $" + amount).queue();
        Player.playerObjects.get(topBidder).subtractMoney(amount);
        Player.playerObjects.get(topBidder).addProperty(property);
        System.out.println(Player.playerObjects.get(topBidder).getMoney());
        Commands.EndTurn();
    }

    private void Announce() {
        Commands.event.getChannel().sendMessage(Player.playerNames.get(topBidder) + " Bid $" + amount + "\nOriginal Price: $" + BoardData.propertyData.get(property)[0]).queue();
    }

    private void CantBid() {
        Commands.event.getChannel().sendMessage("Could not bid on this property!").queue();
    }
}