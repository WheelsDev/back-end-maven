package org.example;

import java.util.Random;

public class Cliente {

    private String nome = null;
    private String endereco = null;
    private String telefone = "";
    private int clienteID = 0;
    private String email = "";

    public Cliente(String nome, String endereco, String telefone){

        clienteID = new Random().nextInt(1,10000);
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = "não existente";
    }
    public Cliente(String nome, String endereco, String telefone, String email){

        clienteID = new Random().nextInt(1,10000);
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }
    public Cliente(int id, String nome, String endereco, String telefone, String email){
        clienteID = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    public int getClienteID(){
        return clienteID;
    }

    public String getNome(){
        return nome;
    }

    public String getEndereco(){
        return endereco;
    }

    public String getTelefone(){
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public void exibirDetalhes() {
        System.out.println("Identificador do Cliente: " + clienteID);
        System.out.println("Nome: " + nome);
        System.out.println("Endereço: " + endereco);
        System.out.println("Telefone: " + telefone);
        System.out.println("E-mail: " + email);
    }

    @Override
    public String toString() {
        return "\n"+clienteID + "," + nome + "," + endereco + "," + telefone + "," + email;
    }
}
