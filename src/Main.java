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
                    7. graphics
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
                case "7" -> graphics(prices);
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
                prices[i] = Integer.parseInt(sc.nextLine());
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


    private static void histogramInProgress(int[] prices) {


        int priceRow = 8;
        int timeColumn = 27;
        String[][] graphicMap = new String[priceRow][timeColumn];
        addPrices(priceRow, timeColumn, prices, graphicMap);
        printHistogram(priceRow, timeColumn, graphicMap);
    }

    private static void addPrices(int priceRow, int timeColumn, int[] prices, String[][] graphicMap) {
        for (int i = 0; i < priceRow; i++) {
            System.out.println();
            for (int j = 0; j < timeColumn; j++) {
                String maxNum = Integer.toString(getMaxPrice(prices));
                String minNum = Integer.toString(getMinPrice(prices));
                if (j == 0 && i == 0)
                    System.out.print(maxNum);
                else if (j == 0 && i == priceRow - 3) {
                    for (j = 0; j < maxNum.length() - minNum.length(); j++) {
                        System.out.print(" ");
                    }
                    System.out.print(minNum);
                }
            }
        }
    }


            private static void printHistogram ( int priceRow, int timeColumn, String[][] graphicMap){
                for (int i = 0; i < priceRow; i++) {
                    for (int j = 0; j < timeColumn; j++) {
                        System.out.print(graphicMap[i][j]);
                    }
                    System.out.println();
                }
            }


//////////////////////////////////////////////

            public static void graphics ( int[] prices){
                String[][] map = new String[8][26];
                createGraphBody(map, prices);
                printVisualGraph(map);
            }

            private static void createGraphBody (String[][]graph,int[] prices){

                int numSpace = getMaxPrice(prices);
                int spaceHelp = getMinPrice(prices);


                addHighestAndLowestPriceYAxis(graph, prices);
                addYBorder(graph, numSpace, spaceHelp, prices);
                addXBorder(graph);
                addHoursToXAxis(graph);
                addPriceRepresentationToGraph(graph, prices);
            }

            private static void addHighestAndLowestPriceYAxis (String[][]graph,int[] prices){
                graph[0][0] = String.valueOf(getMaxPrice(prices));
                graph[6][0] = String.valueOf(getMinPrice(prices));
            }

            private static void addYBorder (String[][]graph,int numSpace, int spaceHelp, int[] prices){
                addVerticalWhiteSpaceAndNumberToGraph(graph, numSpace, spaceHelp, prices);
                addPipeToVerticalBorder(graph);
            }

            private static void addPipeToVerticalBorder (String[][]graph){
                for (int i = 0; i < 8; i++) {
                    graph[i][1] = "|";
                }
            }

            private static void addVerticalWhiteSpaceAndNumberToGraph (String[][]graph,int numSpace, int spaceHelp,
            int[] prices){
                for (int i = 1; i < 8; i++) {
                    graph[i][0] = printWhiteSpaces(numSpace);
                    if (i == 5)
                        graph[i][0] = printWhiteSpaces(numSpace - spaceHelp) + getMinPrice(prices);
                }
            }

            private static String printWhiteSpaces ( int numSpace){
                return " ".repeat(Math.max(0, numSpace));
            }

            private static void addXBorder (String[][]graph){
                for (int j = 2; j < 26; j++) {
                    graph[6][j] = "---";
                }
            }

            private static void addHoursToXAxis (String[][]graph){
                int k = 2;
                for (int i = 0; i < 24; i++) {
                    graph[7][k] = formatHourRange(i) + " ";
                    k++;
                }
            }

            private static void addPriceRepresentationToGraph (String[][]graph,int[] prices){
                double priceCheck = (double) (getMaxPrice(prices)) / 6;
                int divisionHelper = 6;

                for (int i = 0; i < 6; i++) {
                    for (int j = 2; j < 26; j++) {
                        addPrice(graph, priceCheck, divisionHelper, i, j, prices);
                        addLastPrice(graph, i, j, prices);
                    }
                    divisionHelper--;
                }
            }

            private static void addPrice (String[][]graph,double priceCheck, int divisionHelper, int i, int j,
            int[] prices){
                if (prices[j - 2] >= (priceCheck * divisionHelper)) {
                    graph[i][j] = " * ";
                } else {
                    graph[i][j] = "   ";
                }
            }

            private static void addLastPrice (String[][]graph,int i, int j, int[] prices){
                if (i == 5) {
                    if (prices[j - 2] >= getMinPrice(prices)) {
                        graph[i][j] = " * ";
                    } else {
                        graph[i][j] = "   ";
                    }
                }
            }

            private static void printVisualGraph (String[][]graph){
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 26; j++) {
                        System.out.print(graph[i][j]);
                    }
                    System.out.println("");
                }
            }


/////////////////////////////////////////////////////


            private static void quit () {
                System.out.println("Programmet avslutas, välkommen åter");
            }
        }