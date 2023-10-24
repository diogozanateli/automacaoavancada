package io.sim;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class AlphaBank {
    private ReentrantLock lock;
    private Account account;
    private List<Account> accounts;

    // Construtor da classe AlphaBank
    public AlphaBank(String login, String password) {
        Account account = new Account(login, password);
        lock = new ReentrantLock();
        accounts = new ArrayList<>();
        addAccount(account);
    }

    //Método para realizar depósito na conta
    public void deposit(String login, double value) {
        lock.lock();
        if (account.getAccountInfo()[0].equals(login)) {
            try {
                account.recordTransation("Depósito", login, value);
            } finally {
                lock.unlock();
            }
        }
    }

    //Método para realizar saque na conta
    public void withdraw(String login, String password, double value) {
        if (account.getAccountInfo()[0].equals(login) || account.getAccountInfo()[1].equals(password)) {
            lock.lock();
            try {
                account.recordTransation("Saque", login, -value);
            } finally {
                lock.unlock();
            }
    }
}

    //Método para realizar transferência entre contas
    public void transfer(String origem, String destino, double valor) {
        lock.lock();
        try {
            if (origem == null || destino == null || valor <= 0) {
                throw new IllegalArgumentException("Erro: Parâmetros inválidos.");
            }
            if (account.getAccountInfo()[0].equals(origem)) {
                account.recordTransation("Transferência", origem, -valor);
                account.recordTransation("Transferência", destino, valor);
            }
     } finally {
            lock.unlock();
        }
}

//Método para retornar o saldo da conta
public double getBalance(String login, String password) {
    if (account.getAccountInfo()[0].equals(login) || account.getAccountInfo()[1].equals(password)) {
        return account.getBalance();
    }
    return 0.0;
}

//Método para verificar se a conta existe
public boolean hasAccount(String string) {
    if (account.getAccountInfo()[0].equals(string)) {
        return true;
    }
    return false;
}

//Método para retornar o login e a senha da conta
public String[] getAccountInfo() {
    return account.getAccountInfo();
    
}

//Método para adicionar uma conta na lista de contas
public void addAccount(Account account) {
    accounts.add(account);
}

//Método para remover uma conta da lista de contas
public void removeAccount(String login, String password) {
    if (account.getAccountInfo()[0].equals(login) || account.getAccountInfo()[1].equals(password)) {
        accounts.remove(account);
    }
}

}
