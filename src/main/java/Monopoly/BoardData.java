package Monopoly;

import java.util.HashMap;
import java.util.Map;

public class BoardData {
    public static Map<Integer, Integer[]> propertyData;
    public static int[] tileIDs;
    public static int[] monopolyData;

    public static void InstantiateData() {
        propertyData = new HashMap<Integer, Integer[]>();
        // 0 is property, 1 is railroad, 2 is chance, 3 is chest, 4 is electric / water works, 5 is other (taxes, jail, go, etc)
        tileIDs = new int[]{5, 0, 3, 0, 5, 1, 0, 2, 0, 0, 5, 0, 4, 0, 0, 1, 0, 3, 0, 0, 5, 0, 2, 0, 0, 1, 0, 0, 4, 0, 5, 0, 0, 3, 0, 1, 2, 0, 5, 0};
        
        // Brown, Light Blue, Magenta, Orange, Red, Yellow, Green, Dark Blue
        // 1      2           3        4       5    6       7      8     
        monopolyData = new int[]{0, 1, 0, 1, 0, 0, 2, 0, 2, 2, 0, 3, 0, 3, 3, 0, 4, 0, 4, 4, 0, 5, 0, 5, 5, 0, 6, 6, 0, 6, 0, 7, 7, 0, 7, 0, 0, 8, 0, 8};

        // board pos, cost, rent, rent with houses 1, 2, 3, 4, hotel cost, cost for houses
        propertyData.put(1, new Integer[]{60, 2, 10, 30, 90, 160, 250, 50});
        propertyData.put(3, new Integer[]{60, 4, 20, 60, 180, 320, 450, 50});
        propertyData.put(6, new Integer[]{100, 6, 30, 90, 270, 400, 550, 50});
        propertyData.put(8, new Integer[]{100, 6, 30, 90, 270, 400, 550, 50});
        propertyData.put(9, new Integer[]{120, 8, 40, 100, 300, 450, 600, 50});
        propertyData.put(11, new Integer[]{140, 10, 50, 150, 450, 625, 750, 100});
        propertyData.put(13, new Integer[]{140, 10, 50, 150, 450, 625, 750, 100});
        propertyData.put(14, new Integer[]{160, 12, 60, 180, 500, 700, 900, 100});
        propertyData.put(16, new Integer[]{180, 14, 70, 200, 550, 750, 950, 100});
        propertyData.put(18, new Integer[]{180, 14, 70, 200, 550, 750, 950, 100});
        propertyData.put(19, new Integer[]{200, 16, 80, 220, 600, 800, 1000, 100});
        propertyData.put(21, new Integer[]{220, 18, 90, 250, 700, 875, 1050, 150});
        propertyData.put(23, new Integer[]{220, 18, 90, 250, 700, 875, 1050, 150});
        propertyData.put(24, new Integer[]{240, 20, 100, 300, 750, 925, 1100, 150});
        propertyData.put(26, new Integer[]{260, 22, 110, 330, 800, 975, 1150, 150});
        propertyData.put(27, new Integer[]{260, 22, 110, 330, 800, 975, 1150, 150});
        propertyData.put(29, new Integer[]{280, 24, 120, 360, 850, 1025, 1200, 150});
        propertyData.put(31, new Integer[]{300, 26, 130, 390, 900, 1100, 1275, 200});
        propertyData.put(32, new Integer[]{300, 26, 130, 390, 900, 1100, 1275, 200});
        propertyData.put(34, new Integer[]{320, 28, 150, 450, 1000, 1200, 1400, 200});
        propertyData.put(37, new Integer[]{350, 35, 175, 500, 1100, 1300, 1500, 200});
        propertyData.put(39, new Integer[]{400, 50, 200, 600, 1400, 1700, 2000, 200});
    }
}