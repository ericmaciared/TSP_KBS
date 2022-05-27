import java.util.ArrayList;

public class Routes {
    private ArrayList<City> cities;
    private ArrayList<Connection> connections;

    public ArrayList<City> getCities() {
        return cities;
    }

    public City getCity(String city){
        for (City c: cities) {
            if (c.getName().equalsIgnoreCase(city)) return c;
        }
        return null;
    }

    public Connection getConnection(String from, String to) {
        for (Connection c: connections) {
            if (c.getFrom().equalsIgnoreCase(from) && c.getTo().equalsIgnoreCase(to)) return c;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Routes{" +
                "cities=" + cities +
                ", connections=" + connections +
                '}';
    }
}
