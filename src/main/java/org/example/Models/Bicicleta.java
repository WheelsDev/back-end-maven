package org.example.Models;

import java.util.Random;

public class Bicicleta {

    private int numero;
    private String nome;
    private String marca;
    private String modelo;
    private double deposito = 0;
    private String tipo;
    private double diariaTaxaAluguel = 0;
    private boolean disponibilidade = true;

    public Bicicleta() {}

    public Bicicleta(String nome, String marca, String modelo, double deposito, String tipo, double diariaTaxaAluguel , boolean disponibilidade) {
        numero = new Random().nextInt(10000);
        this.nome = nome;
        this.deposito = deposito;
        this.diariaTaxaAluguel = diariaTaxaAluguel;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo =  tipo;
        this.disponibilidade = disponibilidade;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setDeposito(double deposito) {
        this.deposito = deposito;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDiariaTaxaAluguel(double diariaTaxaAluguel) {
        this.diariaTaxaAluguel = diariaTaxaAluguel;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public int getNumero() {
        return numero;
    }

    public String getNome() {
        return nome;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getDeposito() {
        return deposito;
    }

    public String getTipo() {
        return tipo;
    }

    public double getDiariaTaxaAluguel() {
        return diariaTaxaAluguel;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void exibirDetalhes() {
        System.out.println("Número: " + numero);
        System.out.println("Nome: " + nome);
        System.out.println("Marca: " + marca);
        System.out.println("Modelo: " + modelo);
        System.out.println("Tipo de bicicleta: " + tipo);
        System.out.println("Depósito: R$ " + deposito);
        System.out.println("diariaTaxaAluguel: R$ " + diariaTaxaAluguel);
        System.out.println("Disponibilidade: " + disponibilidade);
    }

    public void calcularCusto(int numeroDeDias) {
        double custo = deposito + (diariaTaxaAluguel * numeroDeDias);
        System.out.println("O total custo para o alguel deve ser: R$ " + custo + "\n");
    }

}
