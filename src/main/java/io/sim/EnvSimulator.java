package io.sim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import de.tudresden.sumo.objects.SumoColor;
import it.polito.appeal.traci.SumoTraciConnection;

public class EnvSimulator extends Thread{

    private SumoTraciConnection sumo;
	private ArrayList<Itinerary> itineraries = new ArrayList<>();
	private boolean allItinerariesLoaded = true;

    public EnvSimulator(){

    }

    public void run(){

		/* SUMO */
		String sumo_bin = "sumo-gui";		
		String config_file = "map/map.sumo.cfg";
		
		// Sumo connection
		this.sumo = new SumoTraciConnection(sumo_bin, config_file);
		sumo.addOption("start", "1"); // auto-run on GUI show
		sumo.addOption("quit-on-end", "1"); // auto-close on end

		try {
			sumo.runServer(12345);

			for (Itinerary itinerary : itineraries) {
				if (!itinerary.isOn()) {
					allItinerariesLoaded = false;
					break;
				}
			}

			if (allItinerariesLoaded) {

				for (int carIndex = 0; carIndex < 100; carIndex++) {
					int fuelType = 2;
					int fuelPreferential = 2;
					double fuelPrice = 3.40;
					int personCapacity = 1;
					int personNumber = 1;
					SumoColor green = new SumoColor(0, 255, 0, 126);
				
					// Escolha o itinerário com base no índice
					String itineraryParameter = String.valueOf(carIndex);
				
					// Crie uma conta bancária para o driver
					String driverLogin = "driver" + carIndex; // Login com base no índice
					String driverSenha = "senha" + carIndex; // Senha com base no índice
					AlphaBank driverAccount = new AlphaBank(driverLogin, driverSenha);
					
					Driver driver = new Driver(driverAccount);
					Car car = new Car(driverAccount, new ArrayList<Route>(), driver);

					// Crie o driver com o itinerário e a conta bancária
					Itinerary randomItinerary = new Itinerary("data/dados2.xml", itineraryParameter);
					
				
					// Associe a rota ao driver e ao carro
					String[] points = randomItinerary.getItinerary();
					Route route = new Route(points);
					driver.addRouteToExecute(route);
					car.setRoutes(route);
					route.addCar(car);
				
					// Crie o objeto Auto
					Auto auto = new Auto(true, "Car" + carIndex, green, "D" + carIndex, sumo, 500, fuelType, fuelPreferential, fuelPrice, personCapacity, personNumber, car);
				
					// Crie o objeto TransportService associado ao carro e itinerário
					TransportService transportService = new TransportService(true, "TransportService" + carIndex, randomItinerary, auto, sumo);
		
					// Inicie o driver, o carro, o Auto e o TransportService
					driver.run();
					car.run();
					auto.start();
					transportService.start();
				
					Thread.sleep(5000); // Aguarde 5 segundos antes de criar o próximo carro
				}
				
				
			}

		
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

    }

}
