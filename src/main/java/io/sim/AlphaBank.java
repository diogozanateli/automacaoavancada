package io.sim;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AlphaBank extends Thread{
    private ArrayList<Account> accounts;
    private Account accountCompany;
    private Account accountDriver;
    private Account accountFuelStation;
    private DrivingData drivingData;

    // Construtor da classe AlphaBank
    // Construtor da classe AlphaBank
    public AlphaBank(DrivingData drivingData) {
        this.accounts = new ArrayList<>();
        this.drivingData = drivingData; // Atribua a instância de DrivingData
    }

//Método para retornar o login e a senha da conta
public Account getAccountFuelStation() {
    return accountFuelStation;
}

//Método para retornar o login e a senha da conta
public Account getAccountCompany() {
    return accountCompany;
}

//Método para retornar o login e a senha da conta
public Account getAccountDriver() {
    return accountDriver;
}

//Método para retornar a lista de contas
public ArrayList<Account> getAccounts() {
    return accounts;
}

//Método para adicionar uma conta na lista de contas
public void addAccount(Account account) {
    accounts.add(account);
}

//Método para remover uma conta da lista de contas
public void removeAccount(Account account) {
   accounts.remove(account);
}

public void transfer(Account account, Account account2, double paymentAmount) {
    ReentrantLock lock = new ReentrantLock();
    lock.lock();
    try {
        if (account.getBalance() >= paymentAmount) {
            account.withdraw(paymentAmount);
            account2.deposit(paymentAmount);
        }
    } finally {
        lock.unlock();
    }
}

public void processPaymentRequests(Socket clientSocket) {
        JSONObject json = new JSONObject(clientSocket.getInetAddress());
        String type = json.getString("type");

        if (type.equalsIgnoreCase("PaymentDriver")) {
            BotPayment botPayment = new BotPayment(accountCompany, accountDriver, "Driver", drivingData);
            botPayment.paymentDriver();
        } else if (type.equalsIgnoreCase("PaymentFuelStation")) {
            BotPayment botPayment = new BotPayment(accountDriver, accountFuelStation, "FuelStation", drivingData);
            botPayment.paymentFuelStation();
        }
    }

    @Override
    public void run() {
        try {
            int porta = 12345;
            try (ServerSocket servidorSocket = new ServerSocket(porta)) {
                while (true) {
                    Socket clientSocket = servidorSocket.accept();
                    System.out.println("Conexão estabelecida com " + clientSocket.getInetAddress());
                    processPaymentRequests(clientSocket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



