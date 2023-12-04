package io.sim;

public class App {
    public static void main( String[] args ) {
        try {
            EnvSimulator ev = new EnvSimulator();
            ev.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
