package io.sim;

import java.util.ArrayList;
import java.util.List;


public class Driver extends Thread {
    private AlphaBank alphaBank;
    private Account account;
    private DrivingData drivingData;
    private List<BotPayment> botPayments;
    private List<Route> routesToExecute;
    private Route currentRoute;
    private List<Route> completedRoutes;
    
    //Construtor da classe Driver
    public Driver(AlphaBank alphaBank) {
        this.alphaBank = alphaBank;
        this.botPayments = new ArrayList<>();
        this.routesToExecute = new ArrayList<>();
        this.completedRoutes = new ArrayList<>();
    }

    //Método para obter o ID do driver
    public String getID() {
        return drivingData.getDriverID();
    }

    public String[] getAccount(){
        return account.getAccountInfo();
    }

    //Método para adicionar uma rota a ser executada
    public void addRouteToExecute(Route route) {
        routesToExecute.add(route);
    }

    //Método para selecionar rota a ser executada
    private synchronized Route selectRouteToExecute() {
        if (routesToExecute.isEmpty()) {
            return null;
        }
        Route route = routesToExecute.remove(0);
        currentRoute = route;
        return route;
    }

    //Método para adicionar uma rota completada
    private synchronized void routeCompleted(Route route) {
        currentRoute = null;
        completedRoutes.add(route);
    }

     //Método para obter o saldo do driver
     public synchronized double getAccountBalance(String login, String senha) {
        return alphaBank.getBalance(login, senha);
    }

    @Override
    //Método para executar o driver
    public void run() {
    
    //Para cada rota a ser executada, cria um bot de pagamento e adiciona a lista de bots de pagamento    
    for (Route route : routesToExecute) {
        BotPayment botPayment = new BotPayment(route);
        botPayments.add(botPayment);
        botPayment.run(); // Inicia o bot de pagamento
    }

    //Enquanto houver rotas a serem executadas, seleciona uma rota para executar e marca a rota como completada caso não seja nula
    while (!routesToExecute.isEmpty()) { 
        Route route = selectRouteToExecute(); 
        if (route != null) { 
            routeCompleted(route);
        }
    }
    }
    
}