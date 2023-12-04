package io.sim;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Account extends Thread {
    
    private String login;
    private String password;
    private double balance;
    private ReentrantLock lock;
    private List<Transacao> extract;


  public Account(String login, String password, String client) {
    this.login = login;
    this.password = password;
    this.lock = new ReentrantLock();
    this.extract = new ArrayList<>();
    this.balance = 0;

    if (this.login.equals("Driver") && this.password.equals(password)) {
        this.balance = 5000;
    } else if (this.login.equals("Company") && this.password.equals(password)) {
        this.balance = 500000; 
    } else if (this.login.equals("FuelStation") && this.password.equals(password)) {
        this.balance = 50000;
    }
    
    }

    public String getLogin() {
        return this.login;
    }

    public void withdraw(double paymentAmount) {
        if (this.balance >= paymentAmount) {
            this.balance -= paymentAmount;
        }
    }

    public void deposit(double paymentAmount) {
        this.balance += paymentAmount;
    }

    //Método para gerar o extrato da conta
    public List<Transacao> getExtract() {   
        return extract;
    }

    //Método para mapear as transações realizadas
    public void recordTransation(String descricao, String login, double valor) {
       if(this.login.equals(login)){
            lock.lock();
            try {
                balance += valor;
                Date timestamp = new Date();
                Transacao transacao = new Transacao(descricao, valor, timestamp);
                extract.add(transacao);
            } finally {
                lock.unlock();
            }
        }
    }

    //Método para retornar o saldo da conta
    public double getBalance() {
        return balance;
    }

    //Método para alterar o saldo da conta
    public void setBalance(double saldo) {
        this.balance = saldo;
    }

    @Override
    public void run(){}

}

