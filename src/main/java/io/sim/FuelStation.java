package io.sim;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Queue;

public class FuelStation extends Thread {
    private AlphaBank alphaBank;
    private Account account;
    private Driver driver;
    private DrivingData drivingData;
    private ReentrantLock lock;
    private int availablePumps;
    private boolean stocked = false;
    private Queue<Car> carQueue = new LinkedList<>();

    // Construtor da classe FuelStation
    public FuelStation(AlphaBank alphaBank, int pumpCount) {
        this.alphaBank = alphaBank;
        this.lock = new ReentrantLock();
        this.availablePumps = pumpCount;
    }

    public void refuelCar(Car car) { // Abastecer o carro
        lock.lock();
        try {
            if (availablePumps > 0) {
                // Apenas dois carros podem abastecer por vez
                availablePumps--;

                // Simule o processo de abastecimento, levando 2 minutos
                car.setVelocity(0); // Car parado
                Thread.sleep(2 * 60 * 1000); // 2 minutos

                // Realize o pagamento ao Fuel Station
                double paymentAmount = car.getFuelTank() * drivingData.getFuelPrice();
                alphaBank.transfer(driver.getAccount()[0], this.getAccount()[0], paymentAmount);

                // Abastece o carro
                car.run();

                stocked = true;

                // Libere a bomba
                availablePumps++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //Se o estoque estiver cheio, retorne true
    public boolean fullSupply() {
        return stocked;
    }

    //Verifique se há carros esperando para abastecer
    private boolean areCarsWaiting() {
        synchronized (carQueue) {
            return !carQueue.isEmpty();
        }
    }

    //Verifica se há carros na fila e retorna o próximo carro da fila
    private Car getNextCarInQueue() {
        synchronized (carQueue) {
            if (!carQueue.isEmpty()) {
                return carQueue.poll(); // Remove e retorna o próximo carro da fila
            }
            return null; // Retorna null se não houver carros na fila
        }
    }
    
    // Método para adicionar um carro à fila de espera
    public void addCarToQueue(Car car) {
        synchronized (carQueue) {
            carQueue.offer(car); // Adiciona o carro à fila
        }
    }

    // Método para retornar o login e a senha da conta
    public String[] getAccount(){
        return account.getAccountInfo();
    }

    @Override
    public void run() {
        while (true) {
            // Verifique se há carros esperando para abastecer
            if (areCarsWaiting()) {
                refuelCar(getNextCarInQueue()); // Abasteça o próximo carro da fila
            }
        }
    }

}