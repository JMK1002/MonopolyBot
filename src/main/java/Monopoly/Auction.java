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
        Commands.Say("Bid started with a starting bid of $1 from " + Player.playerNames.get(topBidder));
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

        if (topBidder == player) {
            Commands.Say("Cant Quit As the Top Bidder! :)");
            return;
        }

        if (!quitBidders.contains(player)) {
            quitBidders.add(player);
            Commands.Say(Player.playerNames.get(player) + " Has Quit the Bid!");
            if (quitBidders.size() == Player.playerObjects.size() - 1) {
                FinishAuction();
            }
        }
    }

    private void FinishAuction() {
        running = false;
        Commands.Say(Player.playerNames.get(topBidder) + " Won the bid for $" + amount);
        Player.playerObjects.get(topBidder).subtractMoney(amount);
        Player.playerObjects.get(topBidder).addProperty(property);
        System.out.println(Player.playerObjects.get(topBidder).getMoney());
        Commands.EndTurn();
    }

    private void Announce() {
        Commands.Say(Player.playerNames.get(topBidder) + " Bid $" + amount + "\nOriginal Price: $" + BoardData.propertyData.get(property).getCost());
    }

    private void CantBid() { Commands.Say("Could not bid on this property!"); }
}