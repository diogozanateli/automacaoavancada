package io.sim;

public class App {
    public static void main( String[] args ) {
        try {
            AlphaBank companyAccount = new AlphaBank("company","12345");
            AlphaBank fuelStationAccount = new AlphaBank("fuelStationAccount","12345");
            Company company = new Company(companyAccount);
            FuelStation fuelStation = new FuelStation(fuelStationAccount, 4);  
            EnvSimulator ev = new EnvSimulator();
            ev.start();
            company.start(); 
            fuelStation.start(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
