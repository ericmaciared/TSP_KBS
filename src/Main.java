import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Gson gson = new Gson();
        Routes routes = null;
        // Import routes
        try {
            routes = gson.fromJson(new FileReader("src/routes.json"), Routes.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (routes == null) return;

        // Get source and destination from user
        String source, destination;
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- TSP Solver ---");
        do {
            System.out.print("Enter source city: ");
            source = scanner.nextLine();
        } while (verifyCity(source, routes.getCities()));

        do {
            System.out.print("Enter destination city: ");
            destination = scanner.nextLine();
        } while (verifyCity(source, routes.getCities()));


        // Perform A* Algorithm
        AStar aStar = new AStar(source.toLowerCase(Locale.ROOT), destination.toLowerCase(Locale.ROOT),
                routes);
        aStar.tsp();

        // TODO: Perform other algorithm
    }

    private static boolean verifyCity(String city, ArrayList<City> cities){
        for (City c: cities) {
            if (c.getName().equalsIgnoreCase(city)){
                return false;
            }
        }
        System.out.println("City: " + city + " does not exist!");
        return true;
    }
}
