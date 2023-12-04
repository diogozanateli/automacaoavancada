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
	private DrivingData drivingData;
	private AlphaBank bank;
	private Company company;
	private FuelStation fuelStation;

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

			new Thread(new Runnable(){
				@Override
				public void run() {
					try {
						while (!sumo.isClosed()) {
							sumo.do_timestep();
							Thread.sleep(500);////////////////////////////////////////velocidade 			
						}
	
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
				
				Itinerary i1 = new Itinerary("data/dados.xml",drivingData.getRouteIDSUMO());

				if (i1.isOn()) {
					for (int i = 1; i <= 1 ; i++) {
						Driver drivers = new Driver ("Driver "+i, "CAR "+i, fuelStation, sumo, bank);
						Car car = new Car("CAR "+i, "Driver "+i, sumo);
						bank.addAccount(drivers.getAccount());
						company.addDriver(drivers);
						company.addCar(car);
						drivers.run();
					}
					
				}

				bank = new AlphaBank(drivingData);
				company = new Company(company.getDrivers(), company.getCars(), i1.getRoutes(), sumo, bank);
				fuelStation = new FuelStation(bank);
				bank.addAccount(fuelStation.getAccount());
				bank.addAccount(company.getAccount());
				fuelStation.run();
				company.run();
				bank.run();
	
			}
		 catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
