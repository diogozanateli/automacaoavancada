package io.sim;

import java.util.ArrayList;
import org.json.JSONObject;

import it.polito.appeal.traci.SumoTraciConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Driver extends Thread {
    private Auto auto;
    private Car car;
    private Client client;
    private Itinerary itinerary;
    private Account account;
    private DrivingData drivingData;
    private ArrayList<Route> routesToExecute;
    private Route currentRoute;
    private ArrayList<Route> completedRoutes;
    private String idDriver;
    private String idCar;
    private AlphaBank alphaBank;
    
    //Construtor da classe Driver
    public Driver(String idDriver, String idCar,FuelStation fuelStation, SumoTraciConnection sumo, AlphaBank alphaBank){
        this.idDriver = idDriver;
        this.idCar = idCar;
        this.alphaBank = alphaBank;
        this.account = new Account(idDriver, "12345", "12345");
        Car car = new Car(idCar, idDriver, sumo);
        Thread t = new Thread(car);
        Itinerary itinerary = new Itinerary("data/dados2.xml", drivingData.getRouteIDSUMO());
        TransportService tS = new TransportService(true, idCar, itinerary, car.getAutoData(), sumo);
		tS.start();
		t.start();
    }

    //Método para obter o ID do driver
    public String getID() {
        return this.idDriver;
    }

    public String getIDCar() {
        return this.idCar;
    }

    //Método para obter a conta do driver
    public Account getAccount() {
        return this.account;
    }

    //Método para obter o itinerário do driver
    public Itinerary getItinerary() {
        return this.itinerary;
    }

    //Método para obter o carro do driver
    public Auto getAuto() {
        return this.auto;
    }

    //Método para obter os dados de direção do driver
    public DrivingData getDrivingData() {
        return this.drivingData;
    }

    //Método para obter a lista de rotas completadas
    public ArrayList<Route> getCompletedRoutes() {
        return this.completedRoutes;
    }

    //Método para obter a lista de rotas a serem executadas
    public ArrayList<Route> getRoutesToExecute() {
        return this.routesToExecute;
    }

    //Método para obter a rota atual
    public Route getCurrentRoute() {
        return this.currentRoute;
    }

    //Método para remover uma rota da lista de rotas a serem executadas
    public void removeRouteToExecute(Route route) {
        this.routesToExecute.remove(route);
    }

    //Método para adicionar uma rota a lista de rotas a serem executadas
    public void addRouteToExecute(Route route) {
        this.routesToExecute.add(route);
    }

    //Método para adicionar uma rota a lista de rotas completadas
    public void addCompletedRoute(Route route) {
        this.completedRoutes.add(route);
    }

    public void startedRoute(Route route) throws IOException {
        this.currentRoute = route;
        JSONObject json = new JSONObject();
        json.put("type", "Route Started!");
        json.put("data", route);
        client.enviarMensagem(json.toString());
    }

    public void finishedRoute(Route route) throws IOException {
        this.currentRoute = null;
        JSONObject json = new JSONObject();
        json.put("type", "Route Finished!");
        json.put("data", route);
        client.enviarMensagem(json.toString());
    }

    //Método para pagar a FuelStation
    public void payFuelStation() {
        try {
            alphaBank.transfer(account, alphaBank.getAccountFuelStation(), car.getRefuelAmount() * drivingData.getFuelPrice());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    //Método para executar o driver
    public void run() {
        try {
            int porta = 12345; 
            try (ServerSocket servidorSocket = new ServerSocket(porta)) {
                System.out.println("Aguardando conexões na porta " + porta + "...");

                while (true) {
                    // Aguarda por uma conexão de cliente
                    Socket clientSocket = servidorSocket.accept();
                    System.out.println("Conexão estabelecida com " + clientSocket.getInetAddress());

                    // Cria um objeto Client para lidar com a conexão
                    client = new Client();
                    client.conectar();
                    // Cria uma nova thread para lidar com a conexão
                    Thread t = new Thread((Runnable) client);
                    t.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}