package io.sim;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private String[] points;  // Lista de pontos/edges que formam a trajetória da rota
    private Itinerary itinerary;
    private List<Car> carsOnRoute;

    // Construtor da classe Route
    public Route(String[] points) {
        this.points = points;
        this.carsOnRoute = new ArrayList<>();
    }

    //Retorna a lista de pontos/edges que formam a trajetória da rota
    public String[] getPoints() {
        return points;
    }

    //Método para obter o ID da rota
    public String getId() {
        return itinerary.getIDItinerary();
    }

    //Verifica se a rota está completa
    public boolean isCompleted() {
        return false;
    }

    //Método para adicionar um carro na rota
    public void addCar(Car car) {
        carsOnRoute.add(car);
    }

    //Retorna a lista de carros na rota
    public List<Car> getCarsOnRoute() {
        return carsOnRoute;
    }
}
