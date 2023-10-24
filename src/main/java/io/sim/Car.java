package io.sim;

import java.util.ArrayList;


public class Car extends Thread {
    private static final double INITIAL_FUEL = 40.0; // Litros
    private static final double FUEL_CONSUMPTION_KM = 0.4; // Consumo por km
    private static final double REFUEL_AMOUNT = 20.0; // Litros a serem abastecidos
    private static final double MINIMUM_FUEL_FOR_REFUEL = 10.0; // Litros
    private static final int REFUEL_TIME_MINUTES = 2;
    private double kmRodados;
    private double xPosition;
    private double yPosition;
    private double lastXPosition;
    private double lastYPosition;

    private int velocity;
    private AlphaBank alphaBank;
    private Account account;
    private FuelStation fuelStation;
    private DrivingData drivingData;
    private double fuelTank;
    private double distanceTraveled;
    private boolean needsRefuel;
    private ArrayList<Route> routes;
    private Driver driver;

    // Construtor da classe Car
    public Car(AlphaBank alphaBank, ArrayList<Route> routes, Driver driver) {
        this.alphaBank = alphaBank;
        this.fuelTank = INITIAL_FUEL;
        this.distanceTraveled = 0.0;
        this.needsRefuel = false;
        this.routes = routes;
        this.driver = driver;
    }

    // Método para retornar a distância percorrida
    public synchronized double getDistanceTraveled() {
        return distanceTraveled;
    }

    // Método para retornar o combustível restante
    public synchronized double getFuelTank() {
        return fuelTank;
    }

    //Método para incrementar o combustível quando o carro for abastecido
    public void setFuelTank(double fuel) {
        fuelTank = fuel;
    }

    // Método para retornar o Driver
    public Driver getDriver() {
        return driver;
    }

    // Método para retornar o número de km rodados
    public int getKmRodados() {
            return (int) kmRodados; 
    }

    // Método para retornar a quantidade de combustível inicial
    public double getInitialFuel() {
        return INITIAL_FUEL;
    }

    // Método para retornar o consumo de combustível por km
    public double getFuelConsumptionPerKilometer() {
        return FUEL_CONSUMPTION_KM;
    }

    // Método para retornar a quantidade de combustível a ser abastecida
    public double getRefuelAmount() {
        return REFUEL_AMOUNT;
    }

    // Método para retornar a quantidade mínima de combustível para abastecimento
    public double getMinimumFuelForRefuel() {
        return MINIMUM_FUEL_FOR_REFUEL;
    }

    // Método para retornar o tempo de abastecimento
    public int getRefuelTimeMinutes() {
        return REFUEL_TIME_MINUTES;
    }
    
    // Método para adicionar as rotas na lista de rotas
    public void setRoutes(Route route) {
        routes.add(route);
    }

    // Método para abastecer o carro
    public void refuel() {
        try {
            Thread.sleep(REFUEL_TIME_MINUTES * 60 * 1000); // Aguarda 2 minutos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double driverBalance = alphaBank.getBalance(driver.getAccount()[0], driver.getAccount()[1]);

        if (driverBalance >= REFUEL_AMOUNT * drivingData.getFuelPrice()) { // Se o saldo do motorista for suficiente para abastecer
            fuelStation.refuelCar(this);
            setFuelTank(fuelTank + REFUEL_AMOUNT);
            needsRefuel = false;
        }
    }

    // Método para definir a velocidade do carro
    public void setVelocity(int velocity) {
        if (velocity >= 0) {
            this.velocity = velocity; // Defina a velocidade do carro
        } else {
            System.out.println("Erro: Velocidade inválida.");
        }
    }

    //Método para calcular a distância entre dois pontos
    private double calculateDistance(double latInicial, double lonInicial, double LatFinal, double LonFinal) {
            return Math.sqrt(Math.pow(LatFinal - latInicial, 2) + Math.pow(LonFinal - lonInicial, 2));
    }
    
    // Método para atualizar a distância percorrida
    public void updateDistanceTraveled() {
        
           if(drivingData.getSpeed() == 0) {
                xPosition = drivingData.getX_Position();
                yPosition = drivingData.getY_Position();
            }
            else{
                lastXPosition = xPosition;
                lastYPosition = yPosition;
            }

            double distanceTraveled = calculateDistance(xPosition, yPosition, lastXPosition, lastYPosition);
    
            kmRodados += distanceTraveled;
    
    }
   
      @Override
    public void run() {
        while (true) {
            if (fuelTank < MINIMUM_FUEL_FOR_REFUEL) { // Se o combustível for menor que 10 litros
                needsRefuel = true; // O carro precisa ser abastecido
                refuel(); // Abasteça o carro
            }

            // Durante o abastecimento, o Car deve estar parado (velocidade igual a 0)
            if (needsRefuel) {
                setVelocity(0);
            }

            // Atualiza o combustível restante
            double fuelConsumption = FUEL_CONSUMPTION_KM * velocity;
            setFuelTank(fuelTank - fuelConsumption);

            // Atualiza a distância percorrida
            distanceTraveled += fuelConsumption;

            // Atualiza a posição do carro
            updateDistanceTraveled();

            // Atualiza a velocidade do carro
            setVelocity(velocity);

        }
    }
}

