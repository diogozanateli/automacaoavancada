package io.sim;

import java.util.ArrayList;

public class Company extends Thread {
    private Account account; // Adicione uma instância de Account
    private AlphaBank alphaBank;
    private ArrayList<BotPayment> botPayments;
    private ArrayList<Route> routes;

    // Construtor da classe Company
    public Company(AlphaBank alphaBank) {
        this.alphaBank = alphaBank;
        this.botPayments = new ArrayList<>();
        this.routes = new ArrayList<>();
    }

    // Método para adicionar uma rota
    public void addRoute(Route route) {
        routes.add(route);
    }

    // Método para retornar o saldo da conta
    public synchronized double getAccountBalance(String login, String senha) {
        return alphaBank.getBalance(login, senha);
    }

    // Método para verificar se a conta existe no AlphaBank
    public boolean hasAccountInAlphaBank() {
        return alphaBank.hasAccount(account.getAccountInfo()[0]); // Verifique se a conta existe no AlphaBank
    }

    //Método para retornar o login e a senha da conta
    public String[] getAccountInfo() {
        return account.getAccountInfo();
    }

    @Override
    public void run() {
        for (Route route : routes) { // Itera sobre as rotas
            BotPayment botPayment = new BotPayment(route); // Cria uma instância de BotPayment
            botPayments.add(botPayment); // Adiciona o BotPayment na lista de BotPayments
            botPayment.run(); // Inicia o BotPayment
        }
    }
}
