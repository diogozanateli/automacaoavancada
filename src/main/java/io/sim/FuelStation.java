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
    private int availablePumps = 4;
    private boolean stocked = false;
    private Queue<Car> carQueue = new LinkedList<>();

    public FuelStation(AlphaBank alphaBank) {
        this.alphaBank = alphaBank;
        this.account = new Account("FuelStation", "12345", "12345");
        this.lock = new ReentrantLock();
    }

    public void refuelCar(Car car) { // Abastecer o carro
        lock.lock();
        try {
            if (availablePumps > 0) {
                // Apenas dois carros podem abastecer por vez
                availablePumps--;

                // Simule o processo de abastecimento, levando 2 minutos
                car.setVelocity(0); // Carro parado
                Thread.sleep(2 * 60 * 1000); // 2 minutos

                // Realize o pagamento ao Fuel Station
                double paymentAmount = drivingData.getFuelConsumption() * drivingData.getFuelPrice();
                alphaBank.transfer(driver.getAccount(), account, paymentAmount);

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

    public Account getAccount() {
        return account;
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