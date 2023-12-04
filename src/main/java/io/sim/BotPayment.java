package io.sim;


public class BotPayment extends Thread {
    private DrivingData drivingData;
    private Account accountPayment;
    private Account accountReceive;
    private String client;

    // Construtor da classe BotPayment
    public BotPayment(Account accountpayment, Account accountreceive, String client, DrivingData drivingData) {
        this.accountPayment = accountpayment;
        this.accountReceive = accountreceive;
        this.client = client;
        this.drivingData = drivingData; // Adiciona um parâmetro para receber a instância de DrivingData
    }

    // Método para pagamento do motorista
    public void paymentDriver() {
        double distance = drivingData.getDistanceKm(drivingData.getX_Position(), drivingData.getY_Position());
        accountPayment.setBalance(accountPayment.getBalance() - distance * 3.25);
        accountReceive.setBalance(accountReceive.getBalance() + distance * 3.25);
    }

    public void paymentFuelStation() {
        double fuelConsumption = drivingData.getFuelConsumption();
        double fuelPrice = drivingData.getFuelPrice();
        accountPayment.setBalance(accountPayment.getBalance() - fuelConsumption * fuelPrice);
        accountReceive.setBalance(accountReceive.getBalance() + fuelConsumption * fuelPrice);
    }

    @Override
    public void run() {
        if (this.client.equals("FuelStation")) {
            paymentFuelStation();
        } else if (this.client.equals("Driver")) {
            paymentDriver();
        }
    }
}
