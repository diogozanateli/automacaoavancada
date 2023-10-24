package io.sim;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Account {
    
    private String login;
    private String password;
    private double balance;
    private ReentrantLock lock;
    private List<Transacao> extract;


  public Account(String login, String password) {
    this.login = login;
    this.password = password;
    this.balance = 0.0;
    this.lock = new ReentrantLock();
    this.extract = new ArrayList<>();
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

    //Método para retornar o login e a senha da conta
    public String[] getAccountInfo() {
        String[] accountInfo = new String[2];
        accountInfo[0] = login;
        accountInfo[1] = password;
        return accountInfo;
    }

    //Método para retornar o saldo da conta
    public double getBalance() {
        return balance;
    }

}

