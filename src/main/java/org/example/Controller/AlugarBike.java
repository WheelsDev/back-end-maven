package org.example.Controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.Models.Contrato;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;

public class AlugarBike {

    private Contrato contrato = null;

    public void exibirDetalhesBicicleta() {
        contrato.getBicicleta().exibirDetalhes();
    }

    public Contrato realizarAluguel() {
        return contrato;
    }
}