package io.sim;

import org.json.JSONObject;

public class BestSpeed extends Thread {

    private DrivingData drivingData;
    private JSONObject json;
    private boolean isRunning;
    private Reconciliation reconciliation;
    private double totalTime;
    private double fluxo1, fluxo2, fluxo3;
    private int medFluxo1,medFluxo2,medFluxo3;
    private double timeSubtract;


    public BestSpeed(DrivingData drivingData) {
        isRunning = true;
        this.drivingData = drivingData;
        this.json = new JSONObject();
        medFluxo1 = 1;
        medFluxo2 = 1;
        medFluxo3 = 1;
    }

    @Override
    public void run() {

        while (isRunning) {

            try {
                Thread.sleep(2000);
                //O percurso foi dividido em 3 medidores de fluxo.
                //Nesta primeira condição ele testa se o está no inicio da rota, ou seja, inicio da reconciliação.
                if(drivingData.getDistanceKm(drivingData.getX_Position(), drivingData.getY_Position())<=(drivingData.getOdometer()*0.5) && medFluxo1==1){
                    double totalTime;
                    totalTime = drivingData.getTimeStamp();
                    //Aqui dividi o tempo total de percurso em 3 partes iguais, para cada medidor de fluxo.
                    fluxo1 = totalTime/3;
                    fluxo2 = totalTime/3;
                    fluxo3 = totalTime/3;
                    double[]y = new double[]{totalTime, fluxo1,fluxo2,fluxo3};
                    //Vetor de correção a partir do desvio padrão das distâncias coletadas
                    double[]v = new double[]{0.689626874,0.689626874,0.689626874,0.689626874};
                    double[][] A = new double[][] { { 1, -1, -1, -1} };
                    reconciliation = new Reconciliation(y, v, A);
                    medFluxo1 = 0;
                }

                if(drivingData.getDistanceKm(drivingData.getX_Position(), drivingData.getY_Position())<=(drivingData.getOdometer()*0.5) && (drivingData.getDistanceKm(drivingData.getX_Position(), drivingData.getY_Position())>(drivingData.getOdometer()*0.50)) && medFluxo2==1){
                   totalTime = totalTime-drivingData.getParcialTime(drivingData.getTimeStamp());
                   fluxo2 = fluxo2+(drivingData.getTimeStamp()-fluxo1);
                    timeSubtract = drivingData.getParcialTime(drivingData.getTimeStamp());
                   double[]novo_y = new double[]{totalTime,fluxo2,fluxo3};
                   double[] novo_v = new double[]{0.689626874,0.689626874,0.689626874};
                   double[][]novo_A = new double[][] { { 1, -1, -1} };
                   reconciliation = new Reconciliation(novo_y,novo_v, novo_A);
                   medFluxo2 = 0;
                }
         
                if(drivingData.getDistanceKm(drivingData.getX_Position(), drivingData.getY_Position())>(drivingData.getOdometer()) && medFluxo3 == 1){
                    totalTime = totalTime-(drivingData.getTimeStamp()-timeSubtract);
                    fluxo3 = fluxo3+((drivingData.getTimeStamp()-timeSubtract));
                    double[]novo_y = new double[]{totalTime,fluxo3};
                    double[] novo_v = new double[]{0.689626874,0.689626874};
                    double[][]novo_A = new double[][] { { 1, -1} };
                    reconciliation = new Reconciliation(novo_y,novo_v, novo_A);
                    medFluxo3 = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

   double[]valores = reconciliation.getReconciledFlow();
    //Dividindo o trajeto do veículo em 2 partes para pegar a velocidade em cada intervalo
    double velocidade = (drivingData.getOdometer()/2)/(valores[1]);
     double finalVelocidade = velocidade;

    public double getFinalVelocidadeKmH() {
        return velocidade*3.6;
    }
}

            


