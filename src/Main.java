import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        meny();
    }

    public static void meny() {
        Scanner sc = new Scanner(System.in);

        int[] price = new int[5];

        String choice;
        do {
            System.out.println("""
                                        
                                        
                    Elpriser
                    ========
                    1. Inmatning
                    2. Min, Max och Medel
                    3. Sortera
                    4. Bästa Laddningstid (4h)
                    e. Avsluta
                                        
                    """);
            choice = sc.nextLine();
            choice = choice.toLowerCase();

            switch (choice) {
                case "1" -> input(price);
                case "2" -> minMaxAvg(price);
                case "3" -> sort(price);
                case "4" -> bestChargingTime(price);
                case "e" -> quit();
                default -> System.out.println("Vänligen välj ett av alternativen nedan:");
            }
        } while (!choice.equals("e"));
    }

    public static void input(int[] price) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Skriv in priserna i hela ören och tryck på \"Enter\" mellan inmatningarna");

        for (int i = 0; i < price.length; i++) {
            try {
                System.out.println("Skriv in priset för klockan " + formatHour(i));
                price[i] = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Endast inmatning med heltal är accepterat");
                i--;
            }
        }
        System.out.println("Nu har du matat in alla priser");
    }

    private static void minMaxAvg(int[] price) {
        int min = price[0];
        int minHour = 0;
        getMinPrice(price, min, minHour);
        getMaxPrice(price);
        getAvgPrice(price);
    }

    private static void getMinPrice(int[] price, int min, int minHour) {

        for (int i = 0; i < price.length; i++) {
            if (price[i] < min) {
                min = price[i];
                minHour = i;
            }
        }
        printMinPrice(min, minHour);
    }

    private static void printMinPrice(int min, int minHour) {
        System.out.println("Lägsta priset är " + min + " öre klockan " + formatHour(minHour));
    }

    private static void getMaxPrice(int[] price) {
        int max = price[0];
        int maxHour = 0;

        for (int i = 0; i < price.length; i++) {
            if (price[i] > max) {
                max = price[i];
                maxHour = i;
            }
        }
        printMaxPrice(max, maxHour);
    }

    private static void printMaxPrice(int max, int maxHour) {
        System.out.println("Högsta priset är " + max + " öre klockan " + formatHour(maxHour));
    }

    private static void getAvgPrice(int[] price) {
        int sum = 0;

        for (int i = 0; i < price.length; i++) {
            sum = sum + price[i];
        }
        double avg = (double) sum / price.length;
        printAvgPrice(avg);
    }

    private static void printAvgPrice(double avg) {
        System.out.printf("Medelpriset för hela dygnet är %.2f öre\n", avg);
    }

    public static void sort(int[] price) {
        int[] priceClone = price.clone();
        int[] indexOfTime = new int[price.length];

        for (int i = 0; i < price.length; i++) {
            indexOfTime[i] = i;
        }

        boolean bubbleSort = true;
        while (bubbleSort) {
            bubbleSort = false;
            for (int i = 0; i <= priceClone.length - 2; i++) {
                if (priceClone[i] > priceClone[i + 1]) {
                    sortWithBubble(priceClone, indexOfTime, i);
                    bubbleSort = true;
                }
            }
        }

        printSortedCode(price, priceClone, indexOfTime);
    }

    private static void sortWithBubble(int[] priceClone, int[] indexOfTime, int i) {
        int temp = priceClone[i + 1];
        priceClone[i + 1] = priceClone[i];
        priceClone[i] = temp;

        int indexTemp = indexOfTime[i + 1];
        indexOfTime[i + 1] = indexOfTime[i];
        indexOfTime[i] = indexTemp;
    }

    private static void printSortedCode(int[] price, int[] priceClone, int[] index) {
        for (int i = 0; i < price.length; i++) {
                System.out.println(formatHour(index[i]) + "  ->  " + priceClone[i] + " öre");
        }
    }

    public static void bestChargingTime(int[] price) {
        int lowPrice = Integer.MAX_VALUE;
        int[] bestHour = new int[2];

        getBest4Hour(price, lowPrice, bestHour);
        System.out.println("Det är bäst att ladda bilen från klockan " + formatRange(bestHour[0]) + " till " + formatRange(bestHour[1]));

    }

    private static void getBest4Hour(int[] price, int lowPrice, int[] bestHour) {
        for (int i = 0; i < price.length; i++) {
            if (i == price.length - 3)
                break;
            int sum = price[i] + price[i + 1] + price[i + 2] + price[i + 3];
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
        int hour = i + 1;
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

    private static void quit() {
        System.out.println("Programmet avslutas, välkommen åter");
    }
}