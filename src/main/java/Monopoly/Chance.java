package Monopoly;

import java.util.ArrayList;
import java.util.Random;

public class Chance extends Tile {
    final private static String[] events = new String[]{
            "Advance to Boardwalk",
            "Advance to Go (Collect $200)",
            "Advance to Illinois Avenue. If you pass Go, collect $200",
            "Advance to St. Charles Place. If you pass Go, collect $200",
            "Advance to the nearest Railroad",
            "Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times amount thrown.",
            "Bank pays you dividend of $50",
            "Get Out of Jail Free",
            "Go Back 3 Spaces",
            "Go to Jail. Go directly to Jail, do not pass Go, do not collect $200",
            "Make general repairs on all your property. For each house pay $25. For each hotel pay $100",
            "Speeding fine $15",
            "Take a trip to Reading Railroad. If you pass Go, collect $200",
            "You have been elected Chairman of the Board. Pay each player $50",
            "Your building loan matures. Collect $150"
    };
    public Chance(int boardPos) {
        super(boardPos, 4);
    }

    // Notes on what to make to other snippets of code
    // Set boardpos to what the player one is set in Monopoly.roll()
    public static void pickChance(Player player) {
        Random random = new Random();
        int num = random.nextInt(0, 15);
        Discord.append("Landed on Chance Card");
        Discord.append(events[num]);
        switch(num) {
            case 0: // Advance to Boardwalk
                player.setBoardPos(39);
                break;
            case 1: // Advance to Go (Collect $200)
                player.addBoardPos(40 - player.getBoardPos());
                break;
            case 2: // Advance to Illinois Avenue. If you pass Go, collect $200
                player.setBoardPos(24);
                if (player.getBoardPos() > 24) player.addMoney(200);
                break;
            case 3: // Advance to St. Charles Place. If you pass Go, collect $200
                player.setBoardPos(11);
                if (player.getBoardPos() > 11) player.addMoney(200);
                break;
            case 4: // Advance to the nearest Railroad.
                if (player.getBoardPos() > 35) player.setBoardPos(5);
                else if (player.getBoardPos() > 25) player.setBoardPos(35);
                else if (player.getBoardPos() > 15) player.setBoardPos(25);
                else if (player.getBoardPos() > 5) player.setBoardPos(15);
                else player.setBoardPos(5);
                break;
            case 5: // Advance token to nearest Utility.
                if (player.getBoardPos() > 28) player.setBoardPos(12);
                else if (player.getBoardPos() > 12) player.setBoardPos(28);
                else player.setBoardPos(12);
                break;
            case 6: // Bank pays you dividend of $50
                player.addMoney(50);
                break;
            case 7: // Get Out of Jail Free
                player.addGetOutOfJailFree();
                break;
            case 8: // Go Back 3 Spaces
                player.setBoardPos((player.getBoardPos() + 37) % 40);
                break;
            case 9: // Go to Jail. Go directly to Jail, do not pass Go, do not collect $200
                Monopoly.getJail().imprison(Monopoly.turn);
                player.setRolledDoubles(false);
                break;
            case 10: // Make general repairs on all your property. For each house pay $25. For each hotel pay $100
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
                player.subtractMoney(25 * houses + 100 * hotels);
                break;
            case 11: // Speeding fine $15
                player.subtractMoney(15);
                break;
            case 12: // Take a trip to Reading Railroad. If you pass Go, collect $200
                player.setBoardPos(5);
                if (player.getBoardPos() > 5) player.addMoney(200);
                break;
            case 13: // You have been elected Chairman of the Board. Pay each player $50
                for (Player p : Player.playerObjects) {
                    p.addMoney(50);
                }
                player.subtractMoney(Player.playerObjects.size() * 50);
                break;
            case 14: // Your building loan matures. Collect $150
                player.addMoney(150);
                break;
        }
    }

    public static void pickChance(Player player, int num) {
        Discord.append("Landed on Chance Card");
        Discord.append(events[num]);
        switch(num) {
            case 0: // Advance to Boardwalk
                player.setBoardPos(39);
                break;
            case 1: // Advance to Go (Collect $200)
                player.addBoardPos(40 - player.getBoardPos());
                break;
            case 2: // Advance to Illinois Avenue. If you pass Go, collect $200
                player.setBoardPos(24);
                if (player.getBoardPos() > 24) player.addMoney(200);
                break;
            case 3: // Advance to St. Charles Place. If you pass Go, collect $200
                player.setBoardPos(11);
                if (player.getBoardPos() > 11) player.addMoney(200);
                break;
            case 4: // Advance to the nearest Railroad.
                if (player.getBoardPos() > 35) player.setBoardPos(5);
                else if (player.getBoardPos() > 25) player.setBoardPos(35);
                else if (player.getBoardPos() > 15) player.setBoardPos(25);
                else if (player.getBoardPos() > 5) player.setBoardPos(15);
                else player.setBoardPos(5);
                break;
            case 5: // Advance token to nearest Utility.
                if (player.getBoardPos() > 28) player.setBoardPos(12);
                else if (player.getBoardPos() > 12) player.setBoardPos(28);
                else player.setBoardPos(12);
                break;
            case 6: // Bank pays you dividend of $50
                player.addMoney(50);
                break;
            case 7: // Get Out of Jail Free
                player.addGetOutOfJailFree();
                break;
            case 8: // Go Back 3 Spaces
                player.setBoardPos((player.getBoardPos() + 37) % 40);
                break;
            case 9: // Go to Jail. Go directly to Jail, do not pass Go, do not collect $200
                Monopoly.getJail().imprison(Monopoly.turn);
                break;
            case 10: // Make general repairs on all your property. For each house pay $25. For each hotel pay $100
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
                player.subtractMoney(25 * houses + 100 * hotels);
                break;
            case 11: // Speeding fine $15
                player.subtractMoney(15);
                break;
            case 12: // Take a trip to Reading Railroad. If you pass Go, collect $200
                player.setBoardPos(5);
                if (player.getBoardPos() > 5) player.addMoney(200);
                break;
            case 13: // You have been elected Chairman of the Board. Pay each player $50
                for (Player p : Player.playerObjects) {
                    p.addMoney(50);
                }
                player.subtractMoney(Player.playerObjects.size() * 50);
                break;
            case 14: // Your building loan matures. Collect $150
                player.addMoney(150);
                break;
        }
    }
}
