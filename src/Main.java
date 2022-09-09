import java.sql.SQLOutput;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);

        int[] prices = new int[5];

        String choice;
        do {
            System.out.println("""
                                        
                                        
                    Elpriser
                    ========
                    1. Inmatning
                    2. Min, Max och Medel
                    3. Sortera
                    4. Bästa Laddningstid (4h)
                    5. Horisontellt Histogram
                    e. Avsluta
                                        
                    """);
            choice = sc.nextLine();
            choice = choice.toLowerCase();

            switch (choice) {
                case "1" -> input(prices);
                case "2" -> minMaxAvg(prices);
                case "3" -> sort(prices);
                case "4" -> bestChargingTime(prices);
//                case "5" -> graph(prices);
                case "5" -> graphInProgress(prices);
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
                System.out.println("Skriv in priset för klockan " + formatHour(i));
                prices[i] = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Endast inmatning med heltal är accepterat");
                i--;
            }
        }
    }

    private static void minMaxAvg(int[] prices) {

        System.out.println("Lägsta priset är " + getMinPrice(prices) + " öre klockan " + formatHour(getMinHour(prices)));
        System.out.println("Högsta priset är " + getMaxPrice(prices) + " öre klockan " + formatHour(getMaxHour(prices)));
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
            System.out.println(formatHour(index[i]) + "  ->  " + pricesClone[i] + " öre");
        }
    }

    public static void bestChargingTime(int[] prices) {
        int lowPrice = Integer.MAX_VALUE;
        int[] bestHour = new int[2];

        getBest4Hour(prices, lowPrice, bestHour);
        System.out.println("Det är bäst att ladda bilen från klockan " + formatRange(bestHour[0]) + " till " + formatRange(bestHour[1]));

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

    public static String formatHour(int i) {
        int hour = i + 1;
        if (i < 9) {
            return "0" + i + "-0" + hour;
        } else if (i == 9) {
            return "0" + i + "-" + hour;
        } else if (i < 23) {
            return i + "-" + hour;
        } else {
            return i + "-24";
        }
    }

    private static String formatRange(int i) {
        if (i < 9) {
            return "0" + i;
        } else if (i == 9) {
            return "0" + i;
        } else if (i < 23) {
            return i + "";
        } else {
            return "24";
        }
    }

    private static void graph(int[] prices) {
        System.out.println("Work in progress");
    }

    private static void graphInProgress(int[] prices) {
        int[] timeInHour = getTime(prices);
        String maxNum = Integer.toString(getMaxPrice(prices));
        String minNum = Integer.toString(getMinPrice(prices));
        int numOfRows = 11;
        for (int pRow = 0; pRow < numOfRows; pRow++) {
            System.out.println();
            for (int tColumn = 0; tColumn < timeInHour.length + 3; tColumn++) {
                if (tColumn == 1)
                    System.out.print("|");
                else if (pRow == numOfRows - 2 && tColumn > 1)
                    System.out.print("͟͟");
                else if (tColumn == 0 && pRow == 0)
                    System.out.print(getMaxPrice(prices));
                else if (tColumn == 0 && pRow == numOfRows - 3) {
                    System.out.print(getMinPrice(prices));
                    for (int j = 0; j < maxNum.length() - minNum.length(); j++) {
                        System.out.print(" ");
                    }
                } else if (tColumn == 0) {
                    for (int i = 0; i < maxNum.length(); i++) {
                        System.out.print(" ");
                    }
                } else
                    System.out.print(" ");
            }
        }
    }

    private static void quit() {
        System.out.println("Programmet avslutas, välkommen åter");
    }
}