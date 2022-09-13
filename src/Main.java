import java.util.Arrays;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hur många timmar vill du använda?");

        int[] prices = new int[sc.nextInt()];

        String choice;
        do {
            System.out.println("""
                    
                    Elpriser
                    ========
                    1. Inmatning
                    2. Min, Max och Medel
                    3. Sortera
                    4. Bästa Laddningstid (4h)
                    5. Visa Horisontellt Histogram
                    e. Avsluta
                    
                    """);
            choice = sc.nextLine();
            choice = choice.toLowerCase();

            switch (choice) {
                case "1" -> input(prices);
                case "2" -> minMaxAvg(prices);
                case "3" -> sort(prices);
                case "4" -> bestChargingTime(prices);
                case "5" -> horizontalHistogram(prices);
                case "e" -> quit();
                default -> System.out.println("Vänligen välj ett av alternativen nedan:");
            }
        } while (!choice.equals("e"));
    }

    public static void input(int[] prices) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Skriv in priserna i hela ören och tryck på \"Enter\" mellan inmatningarna");

        inputPrice(prices, sc);
        System.out.println("Nu har du matat in alla priser");
    }

    private static void inputPrice(int[] prices, Scanner sc) {
        for (int i = 0; i < prices.length; i++) {
            try {
                System.out.println("Skriv in priset för klockan " + formatHourOutput(i));
                prices[i] = (int) (Math.random() * 400) + 1;
                //prices[i] = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Endast inmatning med heltal är accepterat");
                i--;
            }
        }
    }

    private static void minMaxAvg(int[] prices) {
        System.out.println("Lägsta priset är " + getMinPrice(prices) + " öre klockan " + formatHourOutput(getMinHour(prices)));
        System.out.println("Högsta priset är " + getMaxPrice(prices) + " öre klockan " + formatHourOutput(getMaxHour(prices)));
        System.out.printf("Medelpriset för hela dygnet är %.2f öre\n", getAvgPrice(prices));
    }

    private static int getMinPrice(int[] prices) {
        int min = prices[0];
        min = setMinPrice(prices, min);
        return min;
    }

    private static int setMinPrice(int[] prices, int min) {
        for (int j : prices) {
            if (j < min)
                min = j;
        }
        return min;
    }

    private static int getMinHour(int[] prices) {

        int min = prices[0];
        int minHour = 0;

        minHour = setMinHour(prices, min, minHour);
        return minHour;
    }

    private static int setMinHour(int[] prices, int min, int minHour) {
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < min) {
                min = prices[i];
                minHour = i;
            }
        }
        return minHour;
    }

    private static int getMaxPrice(int[] prices) {
        int max = prices[0];
        max = setMaxPrice(prices, max);
        return max;
    }

    private static int setMaxPrice(int[] prices, int max) {
        for (int j : prices) {
            if (j > max)
                max = j;
        }
        return max;
    }

    private static int getMaxHour(int[] prices) {
        int max = prices[0];
        int maxHour = 0;

        for (int i = 0; i < prices.length; i++) {
            if (prices[i] > max) {
                max = prices[i];
                maxHour = i;
            }
        }
        return maxHour;
    }

    private static double getAvgPrice(int[] prices) {
        double sum = 0;
        for (int j : prices)
            sum = sum + j;
        return sum / prices.length;
    }

    public static void sort(int[] prices) {
        int[] pricesClone = Arrays.copyOf(prices, prices.length);

        int[] indexOfTime = getTime(prices);

        boolean isSwapped = true;
        while (isSwapped) {
            isSwapped = false;
            for (int i = 0; i <= pricesClone.length - 2; i++) {
                if (pricesClone[i] > pricesClone[i + 1]) {
                    swapWithBubble(pricesClone, indexOfTime, i);
                    isSwapped = true;
                }
            }
        }
        printSortedCode(prices, pricesClone, indexOfTime);
    }

    private static void swapWithBubble(int[] pricesClone, int[] indexOfTime, int i) {
        int temp = pricesClone[i + 1];
        pricesClone[i + 1] = pricesClone[i];
        pricesClone[i] = temp;

        int indexTemp = indexOfTime[i + 1];
        indexOfTime[i + 1] = indexOfTime[i];
        indexOfTime[i] = indexTemp;
    }

    private static void printSortedCode(int[] prices, int[] pricesClone, int[] index) {
        for (int i = 0; i < prices.length; i++)
            System.out.println(formatHourOutput(index[i]) + "  ->  " + pricesClone[i] + " öre");
    }

    public static void bestChargingTime(int[] prices) {
        int lowPrice = Integer.MAX_VALUE;
        int[] bestHour = new int[2];

        getBest4Hour(prices, lowPrice, bestHour);
        System.out.println("Det är bäst att ladda bilen från klockan " + formatHourRange(bestHour[0]) + " till " + formatHourRange(bestHour[1]));
    }

    private static void getBest4Hour(int[] prices, int lowPrice, int[] bestHour) {
        for (int i = 0; i < prices.length - 3; i++) {
            int sum = prices[i] + prices[i + 1] + prices[i + 2] + prices[i + 3];
            if (sum < lowPrice)
                lowPrice = setBest4Hour(bestHour, i, sum);
        }
    }

    private static int setBest4Hour(int[] bestHour, int i, int sum) {
        int lowPrice;
        lowPrice = sum;
        bestHour[0] = i;
        bestHour[1] = i + 4;
        return lowPrice;
    }

    private static int[] getTime(int[] prices) {
        int[] indexOfTime = new int[prices.length];

        for (int i = 0; i < prices.length; i++)
            indexOfTime[i] = i;
        return indexOfTime;
    }

    public static String formatHourOutput(int hour) {
        int toNextHour = hour + 1;
        if (hour < 9)
            return "0" + hour + "-0" + toNextHour;
        else if (hour == 9)
            return "0" + hour + "-" + toNextHour;
        else
            return hour + "-" + toNextHour;
    }

    private static String formatHourRange(int hour) {
        if (hour < 9)
            return "0" + hour;
        else if (hour == 9)
            return "0" + hour;
        else
            return String.valueOf(hour);
    }

    private static void horizontalHistogram(int[] prices) {
        int numRow = 8;
        int numColumn = 26;
        String[][] histogram = new String[numRow][numColumn];

        addPrice(numRow, numColumn, histogram, prices);

        for (int h = 0; h < numRow; h++) {
            System.out.println();
            for (int w = 0; w < numColumn; w++) {
                addMinMaxPrice(h, w, numRow, prices, histogram);
                addAxis(h, w, numRow, histogram);
                addHour(h, w, numRow, histogram);
                printHistogram(h, w, histogram);
            }
        }
    }

    private static void addMinMaxPrice(int h, int w, int numRow, int[] prices, String[][] histogram) {
        String maxNum = getMaxToAxis(prices);
        String minNum = getMinToAxis(prices);

        if (w == 0 && h == 0)
            addMaxPrice(w, maxNum, histogram[h]);
        else if (w == 0 && h == numRow - 3)
            addMinPrice(w, maxNum, minNum, histogram[h]);
        else if (w == 0)
            lineUpAxis(w, maxNum, histogram[h]);
    }

    private static void addMaxPrice(int w, String maxNum, String[] histogram) {
        histogram[w] = maxNum;
    }

    private static void addMinPrice(int w, String maxNum, String minNum, String[] histogram) {
        histogram[w] = minNum;
        lineUpMinPrice(maxNum, minNum);
    }

    private static void lineUpMinPrice(String maxNum, String minNum) {
        int w;
        for (w = 0; w < maxNum.length() - minNum.length(); w++)
            System.out.print(" ");
    }

    private static void lineUpAxis(int w, String maxNum, String[] histogram) {
        histogram[w] = " ";
        for (int i = 0; i < maxNum.length() - 1; i++)
            System.out.print(" ");
    }

    private static String getMaxToAxis(int[] prices) {
        return Integer.toString(getMaxPrice(prices));
    }

    private static String getMinToAxis(int[] prices) {
        return Integer.toString(getMinPrice(prices));
    }

    private static void addAxis(int h, int w, int numRow, String[][] histogram) {
        if (w == 1)
            addVerticalAxis(w, histogram[h]);
        else if (h == numRow - 2 && w > 1)
            addHorizontalAxis(w, histogram[h]);
    }

    private static void addHorizontalAxis(int w, String[] histogram) {
        histogram[w] = "͟͟͟";
    }

    private static void addVerticalAxis(int w, String[] histogram) {
        histogram[w] = "|";
    }

    private static void addHour(int h, int w, int numRow, String[][] histogram) {
        if (h == numRow - 1 && w > 1)
            histogram[h][w] = formatHourRange(w - 2) + " ";
    }

    private static void addPrice(int numRow, int numColumn, String[][] histogram, int[] prices) {
        for (int h = 0; h < numRow - 2; h++) {
            for (int w = 2; w < numColumn; w++)
                addMarkersToHistogram(numRow, histogram, prices, h, w);
        }
    }

    private static void addMarkersToHistogram(int numRow, String[][] histogram, int[] prices, int h, int w) {
        if (prices[w - 2] >= getMaxPrice(prices) / 5 * (5 - h) || h == numRow - 3)
            addPriceMark(w, histogram[h]);
        else
            lineUpMarkers(w, histogram[h]);
    }

    private static void lineUpMarkers(int w, String[] histogram) {
        histogram[w] = "   ";
    }

    private static void addPriceMark(int w, String[] histogram) {
        histogram[w] = " * ";
    }

    private static void printHistogram(int h, int w, String[][] histogram) {
        System.out.print(histogram[h][w]);
    }

    private static void quit() {
        System.out.println("Programmet avslutas, välkommen åter");
    }
}