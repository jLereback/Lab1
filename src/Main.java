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
        Scanner scIn = new Scanner();

        int[] priceOfHour;
        System.out.println("Skriv in elpriserna för dygnets alla timmar");
        priceOfHour = new int[24];
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