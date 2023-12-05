package io.sim;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import it.polito.appeal.traci.SumoTraciConnection;

public class Company extends Thread {
    private ArrayList<Driver> drivers;
    private ArrayList<Car> cars;
    private ArrayList<Route> routesStarted;
    private ArrayList<Route> routesDone;
    private ArrayList<Route> routes;
    private long timestamp;
    private SumoTraciConnection sumo;
    private Server server;
    private Client clientCompany;
    private Account accountCompany;
    private JSONObject dataCompany;
    private Socket socket;
    private Dotenv dotenv = Dotenv.configure().filename(".env").load();
    private String url = dotenv.get("DB_URL");
    private String user = dotenv.get("DB_USER");
    private String password = dotenv.get("DB_PASSWORD");
    private AlphaBank alphaBank; // Adicione uma instância de AlphaBank

    // Construtor da classe Company
    public Company(ArrayList<Driver> drivers, ArrayList<Car> cars, ArrayList<Route> routes, SumoTraciConnection sumo, AlphaBank alphaBank) throws IOException {
        this.cars = cars;
        this.sumo = sumo;
        this.drivers = drivers;
        this.routes = routes;
        this.timestamp = System.currentTimeMillis();
        this.routesStarted = new ArrayList<Route>();
        this.routesDone = new ArrayList<Route>();
        this.accountCompany = new Account("Company", "1234", "12345");
        this.dataCompany = new JSONObject();
        this.clientCompany = new Client();
        this.socket = clientCompany.getSocket();
        this.server = new Server(socket);
        this.alphaBank = alphaBank; // Atribua a instância de AlphaBank
        this.server.run();
        this.clientCompany.conectar();
    }

    // Método para adicionar uma rota
    public void routeStarted(Route route) {
        routesStarted.add(route); // Adicione a rota na lista de rotas iniciadas
    }

    // Método para remover uma rota
    public void routeDone(Route route) {
        routesDone.add(route); // Adicione a rota na lista de rotas finalizadas
    }

    // Método para adicionar um motorista
    public void addDriver(Driver driver) {
        drivers.add(driver); // Adicione o motorista na lista de motoristas
    }

    // Método para remover um motorista
    public void removeDriver(Driver driver) {
        drivers.remove(driver); // Remova o motorista da lista de motoristas
    }

    // Método para adicionar um carro
    public void addCar(Car car) {
        cars.add(car); // Adicione o carro na lista de carros
    }

    // Método para remover um carro
    public void removeCar(Car car) {
        cars.remove(car); // Remova o carro da lista de carros
    }

    public Account getAccount() {
        return accountCompany;
    }

    //Retorna o arraylist de drivers
    public ArrayList<Driver> getDrivers() {
        return drivers;
    }

    //Retorna o arraylist de carros
    public ArrayList<Car> getCars() {
        return cars;
    }

public void insertData(String jsonData) {
    try {
        // Parse the JSON data
        JSONObject jsonObject = new JSONObject(jsonData);

        // Extract data from JSON
        double time_stamp = jsonObject.getDouble("time_stamp");
        String id_card = jsonObject.getString("id_card");
        int id_route = jsonObject.getInt("id_route");
        float speed = (float) jsonObject.getDouble("speed");
        float distance = (float) jsonObject.getDouble("distance");
        float fuel_comsumption = (float) jsonObject.getDouble("fuel_comsumption");
        String fuel_type = jsonObject.getString("fuel_type");
        float co2_emission = (float) jsonObject.getDouble("co2_emission");
        float longitude = (float) jsonObject.getDouble("longitude");
        float latitude = (float) jsonObject.getDouble("latitude");
        float best_speed = (float) jsonObject.getDouble("best_speed");

        // Connect to the database and insert data
        Connection connection = DriverManager.getConnection(url, user, password);
        String query = "INSERT INTO sumodriving (time_stamp, id_card, id_route, speed, distance, fuel_comsumption, fuel_type, co2_emission, longitude, latitude) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, time_stamp);
        preparedStatement.setString(2, id_card);
        preparedStatement.setInt(3, id_route);
        preparedStatement.setFloat(4, speed);
        preparedStatement.setFloat(5, distance);
        preparedStatement.setFloat(6, fuel_comsumption);
        preparedStatement.setString(7, fuel_type);
        preparedStatement.setFloat(8, co2_emission);
        preparedStatement.setFloat(9, longitude);
        preparedStatement.setFloat(10, latitude);
        preparedStatement.setFloat(11, best_speed);
        preparedStatement.executeUpdate();
    } catch (SQLException | JSONException e) {
        e.printStackTrace();
    }
}

@Override
public void run() {
    while (true) {
        try {
            int porta = 12345; 
            try (ServerSocket servidorSocket = new ServerSocket(porta)) {
                System.out.println("Aguardando conexões na porta " + porta + "...");

                while (true) {
                    // Aguarda por uma conexão de cliente
                    Socket clientSocket = servidorSocket.accept();
                    System.out.println("Conexão estabelecida com " + clientSocket.getInetAddress());

                    // Cria um objeto Server para lidar com a conexão
                    server = new Server(clientSocket);
                    
                    server.run();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
}
