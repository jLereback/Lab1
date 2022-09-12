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
                    5. Horisontellt Histogram (graph)
                    6. histogramInProgress
                    e. Avsluta
                                        
                    """);
            choice = sc.nextLine();
            choice = choice.toLowerCase();

            switch (choice) {
                case "1" -> input(prices);
                case "2" -> minMaxAvg(prices);
                case "3" -> sort(prices);
                case "4" -> bestChargingTime(prices);
                case "5" -> graph(prices);
                case "6" -> histogramInProgress(prices);
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
                prices[i] = (int)(Math.random() * 400) +1;
            } catch (NumberFormatException e) {
                System.out.println("Endast inmatning med heltal är accepterat");
                i--;
            }
            //Integer.parseInt(sc.nextLine());
        }
    }

    private static void minMaxAvg(int[] prices) {

        System.out.println("Lägsta priset är " + getMinPrice(prices) + " öre klockan " + formatHourOutput(getMinHour(prices)));
        System.out.println("Högsta priset är " + getMaxPrice(prices) + " öre klockan " + formatHourOutput(getMaxHour(prices)));
        System.out.printf("Medelpriset för hela dygnet är %.2f öre\n", getAvgPrice(prices));
    }

    private static int getMinPrice(int[] prices) {

        int min = prices[0];

        for (int j : prices) {
            if (j < min) {
                min = j;
            }
        }
        return min;
    }

    private static int getMinHour(int[] prices) {

        int min = prices[0];
        int minHour = 0;

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

        for (int j : prices) {
            if (j > max) {
                max = j;
            }
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
        int sum = 0;

        for (int j : prices) {
            sum = sum + j;
        }
        return (double) sum / prices.length;
    }

    public static void sort(int[] prices) {
        int[] pricesClone = prices.clone();
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
        for (int i = 0; i < prices.length; i++) {
            System.out.println(formatHourOutput(index[i]) + "  ->  " + pricesClone[i] + " öre");
        }
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
            if (sum < lowPrice) {
                lowPrice = setBest4Hour(bestHour, i, sum);
            }
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

        for (int i = 0; i < prices.length; i++) {
            indexOfTime[i] = i;
        }
        return indexOfTime;
    }

    public static String formatHourOutput(int hour) {
        int toNextHour = hour + 1;
        if (hour < 9) {
            return "0" + hour + "-0" + toNextHour;
        } else if (hour == 9) {
            return "0" + hour + "-" + toNextHour;
        } else {
            return hour + "-" + toNextHour;
        }
    }

    private static String formatHourRange(int hour) {
        if (hour < 9) {
            return "0" + hour;
        } else if (hour == 9) {
            return "0" + hour;
        } else {
            return String.valueOf(hour);
        }
    }


////////////////////////////////////////


    private static void graph(int[] prices) {
        int[] timeInHour = getTime(prices);
        String maxNum = Integer.toString(getMaxPrice(prices));
        String minNum = Integer.toString(getMinPrice(prices));
        int numOfRows = 8;
        for (int priceRow = 0; priceRow < numOfRows; priceRow++) {
            System.out.println();
            for (int timeColumn = 0; timeColumn < timeInHour.length + 3; timeColumn++) {
                if (timeColumn == 1)
                    System.out.print("|");
                else if (priceRow == numOfRows - 2 && timeColumn > 1)
                    System.out.print("͟͟");
                else if (timeColumn == 0 && priceRow == 0)
                    System.out.print(getMaxPrice(prices));
                else if (timeColumn == 0 && priceRow == numOfRows - 3) {
                    System.out.print(getMinPrice(prices));
                    for (int j = 0; j < maxNum.length() - minNum.length(); j++) {
                        System.out.print(" ");
                    }
                } else if (timeColumn == 0) {
                    for (int i = 0; i < maxNum.length(); i++) {
                        System.out.print(" ");
                    }
                } else
                    System.out.print(" ");
            }
        }
    }


////////////////////////////////////////

/*

    private static void histogramInProgress(int[] prices) {


        int numRow = 8;
        int numColumn = 27;
        String[][] histogram = new String[numRow][numColumn];

        for (int h = 0; h < numRow; h++) {
            System.out.println();
            for (int w = 0; w < numColumn; w++) {
                addMinMaxPrice(h, w, numRow, prices);
                addAxis(h, w, numRow);
                addHour(h, w, numRow);
                addPrice(h, w, numRow);
            }
        }
    }

    private static void addMinMaxPrice(int h, int w, int numRow, int[] prices) {

        String maxNum = Integer.toString(getMaxPrice(prices));
        String minNum = Integer.toString(getMinPrice(prices));
        if (w == 0 && h == 0) {
            System.out.print(maxNum);
        }
        else if (w == 0 && h == numRow - 3) {
            System.out.print(minNum);
            for (w = 0; w < maxNum.length() - minNum.length(); w++) {
                System.out.print(" ");
            }
        } else if (w == 0) {
            for (int i = 0; i < maxNum.length(); i++) {
                System.out.print(" ");
            }
        }
    }
    private static void addAxis(int h, int w, int numRow) {
        if (w == 1)
            System.out.print("|");
        else if (h == numRow - 2 && w > 1)
            System.out.print("͟͟͟");
    }
    private static void addHour(int h, int w, int numRow) {
        if (h == numRow - 1 && w > 1)
            System.out.print(formatHourRange(w - 2) + " ");
    }
    private static void addPrice(int h, int w, int numRow) {


    }
*/


//////////////////////////////////////////////


    private static void histogramInProgress(int[] prices) {


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
                printHistogram(h, w, numRow, histogram);
            }
        }


    }

    private static void addMinMaxPrice(int h, int w, int numRow, int[] prices, String[][] histogram) {

        String maxNum = Integer.toString(getMaxPrice(prices));
        String minNum = Integer.toString(getMinPrice(prices));
        if (w == 0 && h == 0) {
            histogram[h][w] = maxNum;
        } else if (w == 0 && h == numRow - 3) {
            histogram[h][w] = minNum;
            for (w = 0; w < maxNum.length() - minNum.length(); w++) {
                System.out.print(" ");
            }
        } else if (w == 0) {
            histogram[h][w] = " ";
            for (int i = 0; i < maxNum.length() - 1; i++) {
                System.out.print(" ");
            }
        }

    }

    private static void addAxis(int h, int w, int numRow, String[][] histogram) {
        if (w == 1)
            histogram[h][w] = "|";
        else if (h == numRow - 2 && w > 1)
            histogram[h][w] = "͟͟͟";
        else if (h == numRow - 3 && w > 1)
            histogram[h][w] = " * ";
    }

    private static void addHour(int h, int w, int numRow, String[][] histogram) {
        if (h == numRow - 1 && w > 1)
            histogram[h][w] = formatHourRange(w - 2) + " ";
    }

    private static void addPrice(int numRow, int numColumn, String[][] histogram, int[] prices) {
        int maxNum = getMaxPrice(prices);
        int minNum = getMinPrice(prices);


        for (int h = 0; h < numRow - 3; h++) {
            for (int w = 2; w < numColumn; w++) {
                histogram[h][w] = " 0 ";
                if(prices[w-2] >= maxNum / 5 * (5 - h))
                    histogram[h][w] = " * ";
                else
                    histogram[h][w] = "   ";
            }

        }



    }


    private static void printHistogram(int h, int w, int numRow, String[][] histogram) {
        System.out.print(histogram[h][w]);
    }


    private static void quit() {
        System.out.println("Programmet avslutas, välkommen åter");
    }
}