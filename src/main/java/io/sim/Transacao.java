package io.sim;

import java.util.Date;

public class Transacao {
    
    private String descricao;
    private double valor;
    private Date timestamp;

    //Construtor da classe Transacao
    public Transacao(String descricao, double valor, Date timestamp) {
        this.descricao = descricao;
        this.valor = valor;
        this.timestamp = timestamp;
    }

    //Método para retornar a descrição da transação
    public String getDescricao() {
        return descricao;
    }

    //Método para retornar o valor da transação
    public double getValor() {
        return valor;
    }

    //Método para retornar o timestamp da transação
    public Date getTimestamp() {
        return timestamp;
    }
}

