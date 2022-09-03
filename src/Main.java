import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {
        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            System.out.println("Elpriser\n" +
                    "========\n" +
                    "1. Inmatning\n" +
                    "2. Min, Max och Medel\n" +
                    "3. Sortera\n" +
                    "4. Bästa Laddningstid (4h)\n" +
                    "e. Avsluta\n");
            choice = sc.nextLine();
            choice = choice.toLowerCase();
            switch (choice) {
                case "1" -> input();
                case "2" -> minMaxMedel();
                case "3" -> sort();
                case "4" -> bestCharchingTime();
                case "e" -> quit();
                default -> System.out.println("Please choose one of the options below:");
            }
        } while(!choice.equals("1") ^ !choice.equals("2") ^ !choice.equals("3") ^ !choice.equals("4") ^ !choice.equals("e"));
    }



    private static void input() {
        Scanner sc = new Scanner(System.in);

        System.out.println("För hur många timmar vill du skriva in elpriser?\n" +
                "Obs. Timmarna måste vara sammanhängande");
        int numOfHours = sc.nextInt();
        int[] prices = new int[numOfHours];

        System.out.println("Skriv in priserna i hela ören och tryck på \"Enter\" mellan inmatningarna");
        for (int i = 0; i < prices.length; i++) {
            prices[i] = sc.nextInt();
        }
        System.out.println(prices[4]);
    }

    private static void minMaxMedel() {
    return;
    }

    private static void sort() {
    return;
    }

    private static void bestCharchingTime() {
    return;
    }

    private static void quit() {
    return;
    }
}