package Monopoly;

import java.util.ArrayList;
import java.util.Random;

public class Treasure extends Tile {
    final private static String[] events = new String[]{
            "Advance to Go (Collect $200)",
            "Bank error in your favor. Collect $200",
            "Doctor’s fee. Pay $50",
            "From sale of stock you get $50",
            "Get Out of Jail Free",
            "Go to Jail. Go directly to jail, do not pass Go, do not collect $200",
            "Holiday fund matures. Receive $100",
            "Income tax refund. Collect $20",
            "It is your birthday. Collect $10 from every player",
            "Life insurance matures. Collect $100",
            "Pay hospital fees of $100",
            "Pay school fees of $50",
            "Receive $25 consultancy fee",
            "You are assessed for street repair. $40 per house. $115 per hotel",
            "You have won second prize in a beauty contest. Collect $10",
            "You inherit $100"
    };
    public Treasure(int boardPos) {
        super(boardPos, 4);
    }

    // Notes on what to make to other snippets of code
    // Set boardpos to what the player one is set in Monopoly.roll()
    public static void pickChest(Player player) {
        Random random = new Random();
        int num = random.nextInt(0, 15);
        Discord.append("Landed on Community Treasure");
        Discord.append(events[num]);
        switch(num) {
            case 0:
                player.addBoardPos(40 - player.getBoardPos());
                break;
            case 1:
                player.addMoney(200);
                break;
            case 2:
                player.subtractMoney(50);
                break;
            case 3:
                player.addMoney(50);
                break;
            case 4:
                player.addGetOutOfJailFree();
                break;
            case 5:
                Monopoly.getJail().imprison(Monopoly.turn);
                player.setRolledDoubles(false);
                break;
            case 6:
                player.addMoney(100);
                break;
            case 7:
                player.addMoney(20);
                break;
            case 8:
                for (Player p : Player.playerObjects) {
                    p.subtractMoney(10);
                }
                player.addMoney(Player.playerObjects.size() * 10);
                break;
            case 9:
                player.addMoney(100);
                break;
            case 10:
                player.subtractMoney(100);
                break;
            case 11:
                player.subtractMoney(50);
                break;
            case 12:
                player.addMoney(25);
                break;
            case 13:
                ArrayList<Integer> properties = player.getProperties();
                int houses = 0;
                int hotels = 0;
                for (Integer property : properties) {
                    int houseCount = BoardData.propertyData.get(property).getHouseCount();
                    if (houseCount < 5) {
                        houses += houseCount;
                    }
                    else {
                        hotels++;
                    }
                }
                player.subtractMoney(40 * houses + 115 * hotels);
                break;
            case 14:
                player.addMoney(10);
                break;
            case 15:
                player.addMoney(100);
                break;
        }
    }

    public static void pickChest(Player player, int num) {
        Discord.append("Landed on Community Treasure");
        Discord.append(events[num]);
        switch(num) {
            case 0:
                player.addBoardPos(40 - player.getBoardPos());
                break;
            case 1:
                player.addMoney(200);
                break;
            case 2:
                player.subtractMoney(50);
                break;
            case 3:
                player.addMoney(50);
                break;
            case 4:
                player.addGetOutOfJailFree();
                break;
            case 5:
                Monopoly.getJail().imprison(Monopoly.turn);
                break;
            case 6:
                player.addMoney(100);
                break;
            case 7:
                player.addMoney(20);
                break;
            case 8:
                for (Player p : Player.playerObjects) {
                    p.subtractMoney(10);
                }
                player.addMoney(Player.playerObjects.size() * 10);
                break;
            case 9:
                player.addMoney(100);
                break;
            case 10:
                player.subtractMoney(100);
                break;
            case 11:
                player.subtractMoney(50);
                break;
            case 12:
                player.addMoney(25);
                break;
            case 13:
                ArrayList<Integer> properties = player.getProperties();
                int houses = 0;
                int hotels = 0;
                for (Integer property : properties) {
                    int houseCount = BoardData.propertyData.get(property).getHouseCount();
                    if (houseCount < 5) {
                        houses += houseCount;
                    }
                    else {
                        hotels++;
                    }
                }
                player.subtractMoney(40 * houses + 115 * hotels);
                break;
            case 14:
                player.addMoney(10);
                break;
            case 15:
                player.addMoney(100);
                break;
        }
    }
}
