import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        meny();
    }

    public static void meny() {
        Scanner sc = new Scanner(System.in);

        int[] price = new int[24];

        String choice;
        do {
            System.out.println("""
                                        
                                        
                    Elpriser
                    ========
                    1. Inmatning
                    2. Min, Max och Medel
                    3. Sortera
                    4. Bästa Laddningstid (4h)
                    e. Avsluta""");
            choice = sc.nextLine();
            choice = choice.toLowerCase();

            switch (choice) {
                case "1" -> price = input();
                case "2" -> minMaxAvg(price);
                case "3" -> sort(price);
                case "4" -> bestChargingTime(price);
                case "e" -> quit();
                default -> System.out.println("Vänligen välj ett av alternativen nedan:");
            }
        } while (!choice.equals("e"));
    }


    public static int[] input() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Skriv in priserna i hela ören och tryck på \"Enter\" mellan inmatningarna");

        int[] price = new int[24];



        for (int i = 0; i < price.length; i++) {
            try {
                price[i] = Integer.parseInt(sc.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("Endast inmatning med heltal är accepterat");
                input();
            }

        }

        System.out.println("Nu har du matat in alla priser");

/*
        HÄR VILL INTE PROGRAMMET GÅ TILLBAKA TILL STARTMENYN OM DEN HAR FÅNGAT NUMBERFORMATEXEPTION
*/
        return price;
    }

    private static void minMaxAvg(int[] price) {

        //Lägsta priset
        int min = price[0];
        int minHour = 0;

        for (int i = 0; i < price.length; i++) {
            if (price[i] < min) {
                min = price[i];
                minHour = i;
            }
        }

        if (minHour < 9)
            System.out.println("Lägsta priset är " + min + " öre klockan 0" + minHour + "-0" + (minHour + 1));
        else if (minHour == 9)
            System.out.println("Lägsta priset är " + min + " öre klockan 0" + minHour + "-" + (minHour + 1));
        else if (minHour >= 10 && minHour < 23)
            System.out.println("Lägsta priset är " + min + " öre klockan " + minHour + "-" + (minHour + 1));
        else
            System.out.println("Lägsta priset är " + min + " öre klockan " + minHour + "-24");


        //Högsta priset
        int max = price[0];
        int maxHour = 0;

        for (int i = 0; i < price.length; i++) {
            if (price[i] > max) {
                max = price[i];
                maxHour = i;
            }
        }

        if (maxHour < 9)
            System.out.println("Högsta priset är " + max + " öre klockan 0" + maxHour + "-0" + (maxHour + 1));
        else if (maxHour == 9)
            System.out.println("Högsta priset är " + max + " öre klockan 0" + maxHour + "-" + (maxHour + 1));
        else if (maxHour >= 10 && maxHour < 23)
            System.out.println("Högsta priset är " + max + " öre klockan " + maxHour + "-" + (maxHour + 1));
        else
            System.out.println("Högsta priset är " + max + " öre klockan " + maxHour + "-24");


        //Medelvärde
        int sum = 0;

        for (int i = 0; i < price.length; i++) {
            sum = sum + price[i];
        }
        double avg = (double) sum / price.length;
        System.out.printf("Medelpriset för hela dygnet är %.2f öre\n", avg);
    }

    public static void sort(int[] price) {
        int[] priceClone = price.clone();
        int[] index = new int[price.length];

        for (int i = 0; i < price.length; i++) {
            index[i] = i;
        }

        boolean bubbleSort = true;

        while (bubbleSort) {
            bubbleSort = false;
            for (int i = 0; i <= priceClone.length - 2; i++) {
                if (priceClone[i] > priceClone[i + 1]) {
                    //Swap priceClone[i] and priceClone[i+1]
                    int temp = priceClone[i + 1];
                    priceClone[i + 1] = priceClone[i];
                    priceClone[i] = temp;

                    int indexTemp = index[i + 1];
                    index[i + 1] = index[i];
                    index[i] = indexTemp;

                    bubbleSort = true;
                }
            }
        }

        for (int i = 0; i < price.length; i++) {
            if (index[i] < 9)
                System.out.println("0" + index[i] + "-0" + (index[i] + 1) + "  ->  " + priceClone[i] + " öre");
            else if (index[i] == 9)
                System.out.println("0" + index[i] + "-" + (index[i] + 1) + "  ->  " + priceClone[i] + " öre");
            else if (index[i] >= 10 && index[i] < 23)
                System.out.println(index[i] + "-" + (index[i] + 1) + "  ->  " + priceClone[i] + " öre");
            else
                System.out.println(index[i] + "-24" + "  ->  " + priceClone[i] + " öre");

        }
    }

    private static void bestChargingTime(int[] price) {
        int lowPrice = 0;

        for (int i = 0; i < price.length; i++) {
            if (i == price.length - 3)
                break;
            int sum = price[i] + price[i + 1] + price[i + 2] + price[i + 3];
            if (sum < lowPrice)
                lowPrice = sum;

        }
        System.out.println("Det är bäst att ladda bilen mellan klockan " + lowPrice);

    }

    private static void quit() {
        System.out.println("Programmet avslutas, välkommen åter");
    }
}