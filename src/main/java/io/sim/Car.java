package io.sim;

import java.io.IOException;
import de.tudresden.sumo.cmd.Vehicle;
import org.json.JSONObject;

import it.polito.appeal.traci.SumoTraciConnection;


public class Car extends Vehicle implements Runnable {
    private static final double INITIAL_FUEL = 40.0; // Litros
    private static final double FUEL_CONSUMPTION_KM = 0.4; // Consumo por km
    private static final double REFUEL_AMOUNT = 20.0; // Litros a serem abastecidos
    private static final double MINIMUM_FUEL_FOR_REFUEL = 10.0; // Litros
    private static final int REFUEL_TIME_MINUTES = 2;

    private double velocity;
    private Auto auto;
    private Account account;
    private FuelStation fuelStation;
    private DrivingData drivingData;
    private boolean needsRefuel;
    private Driver driver;
    private Company company;
    private String idCar;
    private String idDriver;
    private double fuelTank;

    // Construtor da classe Car
    public Car(String idCar, String idDriver, SumoTraciConnection sumo) {
        this.idCar = idCar;
        this.idDriver = idDriver;
        this.fuelTank = INITIAL_FUEL;
        this.needsRefuel = false;
    }

    //Método para incrementar o combustível quando o carro for abastecido
    public void setFuelTank(double fuel) {
        fuelTank = fuel;
    }

    // Método para retornar o Driver
    public String getidDriver() {
        return idDriver;
    }

    // Método para retornar o ID do carro
    public String getidCar() {
        return idCar;
    }

    // Método para retornar o combustível restante
    public double getFuelTank() {
        return fuelTank;
    }

    // Método para retornar a quantidade de combustível inicial
    public double getInitialFuel() {
        return INITIAL_FUEL;
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

    public double decrementFuel(){
        return fuelTank - drivingData.getFuelConsumption();
    }

    //Método JSON to string com os dados dessa classe
    public String toJSONString() throws IOException {
        Client client = new Client();
        client.conectar();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("time_stamp", System.currentTimeMillis());
        jsonObject.put("idCar", idCar);
        jsonObject.put("idDriver", idDriver);
        jsonObject.put("speed", drivingData.getSpeed());
        jsonObject.put("distance", drivingData.getDistanceKm(drivingData.getX_Position(), drivingData.getY_Position()));
        jsonObject.put("fuel_consumption", drivingData.getFuelConsumption());
        jsonObject.put("fuel_type", drivingData.getFuelType());
        jsonObject.put("co2_emission", drivingData.getCo2Emission());
        jsonObject.put("longitude", drivingData.getX_Position());
        jsonObject.put("latitude", drivingData.getY_Position());
        return jsonObject.toString();
    }

    // Método para abastecer o carro
    public void refuel() {
        try {
            Thread.sleep(REFUEL_TIME_MINUTES * 60 * 1000); // Aguarda 2 minutos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double driverBalance = account.getBalance();

        if (driverBalance >= REFUEL_AMOUNT * drivingData.getFuelPrice()) { // Se o saldo do motorista for suficiente para abastecer
            fuelStation.refuelCar(this);
            setFuelTank(fuelTank + REFUEL_AMOUNT);
            needsRefuel = false;
        }
    }

    // Método para definir a velocidade do carro
    public void setVelocity(double velocity) {
        if (velocity >= 0) {
            this.velocity = velocity; // Defina a velocidade do carro
        } else {
            System.out.println("Erro: Velocidade inválida.");
        }
    }

    public Auto getAutoData() {
		return auto;
	}

    public Driver getDriverData() {
        return driver;
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

            //Envia dados para a company para inserir no banco SQL
            try {
                company.insertData(toJSONString());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }


}

