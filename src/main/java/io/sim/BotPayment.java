package io.sim;

import java.util.List;

public class BotPayment extends Thread {
    private Route route;
    private DrivingData drivingData;
    private Driver driver;
    private Car car;
    private Account account;
    private AlphaBank alphaBank;
    private FuelStation fuelStation;
    private Company company;

    // Construtor da classe BotPayment
    public BotPayment(Route route) {
        this.route = route;
    }

    @Override
    public void run() {
        // Monitora o progresso dos Cars na rota enquanto não estiverem completos
        while (!route.isCompleted()) {
            List<Car> carsOnRoute = route.getCarsOnRoute();
    
            // Itera sobre os Cars na rota
            for (Car car : carsOnRoute) {
                int kmRodados = car.getKmRodados();
                double paymentDriver= kmRodados * 3.25;
                
                // Realize o pagamento ao Driver
                double companyBalance = company.getAccountBalance(company.getAccountInfo()[0], company.getAccountInfo()[1]);
                if (companyBalance >= paymentDriver) {
                    alphaBank.withdraw(company.getAccountInfo()[0], company.getAccountInfo()[1], paymentDriver);
                    alphaBank.deposit(driver.getAccount()[0], paymentDriver);
                    account.recordTransation("Pagamento recebido!", driver.getAccount()[0], paymentDriver);
                    account.recordTransation("Pagamento efetuado!", company.getAccountInfo()[0], -paymentDriver);
                } else {
                    System.out.println("Erro: Saldo insuficiente na conta da Company.");
                }
                
            }

            //Enquanto o carro não estiver cheio, abasteça-o, após abastecer realiza o pagamento a FuelStation
        while(!fuelStation.fullSupply()) {
            double paymentFuelStation = car.getFuelTank() * drivingData.getFuelPrice();
    
                double driverBalance = driver.getAccountBalance(driver.getAccount()[0], driver.getAccount()[1]);
                if (driverBalance >= paymentFuelStation) {
                    alphaBank.withdraw(driver.getAccount()[0], driver.getAccount()[1], paymentFuelStation);
                    alphaBank.deposit(fuelStation.getAccount()[0], paymentFuelStation);
                    account.recordTransation("Pagamento recebido!", fuelStation.getAccount()[0], paymentFuelStation);
                    account.recordTransation("Pagamento efetuado!", driver.getAccount()[0], -paymentFuelStation);
                } else {
                    System.out.println("Erro: Saldo insuficiente na conta do Driver.");
                }
        }

        
            try {
                Thread.sleep(1000); // Aguarda 1 segundo antes de verificar novamente
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    }

