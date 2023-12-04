package io.sim;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server extends Thread {
    private static List<BufferedWriter> clientes = new CopyOnWriteArrayList<>();
    private String nome;
    private Socket con;
    private InputStream in;
    private InputStreamReader inr;
    private BufferedReader bfr;
    private Object bfw;

    public Server(Socket con) {
        this.con = con;
        try {
            in = con.getInputStream();
            inr = new InputStreamReader(in);
            bfr = new BufferedReader(inr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String msg;
            OutputStream ou = this.con.getOutputStream();
            Writer ouw = new OutputStreamWriter(ou);
            BufferedWriter bfw = new BufferedWriter(ouw);

            clientes.add(bfw);
            nome = msg = bfr.readLine();

            while (msg != null && !"Sair".equalsIgnoreCase(msg)) {
                msg = bfr.readLine();
                sendToAll(bfw, msg);
                System.out.println(msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clientes.remove(bfw);
            try {
              sendToAll(null, " saiu do chat!");
            } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }

            try {
                con.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendToAll(BufferedWriter bwSaida, String msg) throws IOException {
        for (BufferedWriter bw : clientes) {
            if (bwSaida != null && !(bwSaida == bw)) {
                bw.write(nome + " -> " + msg + "\r\n");
                bw.flush();
            } else if (bwSaida == null) {
                bw.write(msg + "\r\n");
                bw.flush();
            }
        }
    }
}
