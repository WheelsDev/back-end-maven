package org.example.Controller;

import org.example.Models.Contrato;

public class AlugarBike {
    private Contrato contrato = null;

    public void exibirDetalhesBicicleta(){
        contrato.getBicicleta().exibirDetalhes();
    }

    public Contrato realizarAluguel() {
        return contrato;
    }
}
