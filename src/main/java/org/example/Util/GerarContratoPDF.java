package org.example.Util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.Models.Contrato;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

public class GerarContratoPDF {
    public void criarPDF(Contrato contrato) {
        Document document = new Document();
        String arquivoContrato = contrato.getIdentificador() + ".pdf";
        Path caminhoArquivo = Paths.get("src","main","java","org","example","Util",arquivoContrato);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo.toFile()));
            document.open();

            PdfPTable header = new PdfPTable(3);
            header.setWidthPercentage(100);
            header.setWidths(new int[]{1, 1, 1});

            String arquivoImagem = "LogoWheels.png";
            Path caminhoImagem = Paths.get("src","main","java","org","example","Resources",arquivoImagem);
            Image logoLoja = Image.getInstance(caminhoImagem.toString());
            logoLoja.scaleToFit(80, 80);
            PdfPCell cellLogoLoja = new PdfPCell(logoLoja);
            cellLogoLoja.setBorder(Rectangle.NO_BORDER);
            cellLogoLoja.setHorizontalAlignment(Element.ALIGN_LEFT);

            Image logoEstado = Image.getInstance("C:\\Users\\Usuario\\Documents\\GitHub\\back-end-maven\\src\\main\\java\\org\\example\\Resources\\GovernoRioDeJaneiro.png");
            logoEstado.scaleToFit(80, 80);
            PdfPCell cellLogoEstado = new PdfPCell(logoEstado);
            cellLogoEstado.setBorder(Rectangle.NO_BORDER);
            cellLogoEstado.setHorizontalAlignment(Element.ALIGN_CENTER);

            Image logoPrefeitura = Image.getInstance("C:\\Users\\Usuario\\Documents\\GitHub\\back-end-maven\\src\\main\\java\\org\\example\\Resources\\PrefeituraRioDeJaneiro.png");
            logoPrefeitura.scaleToFit(80, 80);
            PdfPCell cellLogoPrefeitura = new PdfPCell(logoPrefeitura);
            cellLogoPrefeitura.setBorder(Rectangle.NO_BORDER);
            cellLogoPrefeitura.setHorizontalAlignment(Element.ALIGN_RIGHT);

            header.addCell(cellLogoLoja);
            header.addCell(cellLogoEstado);
            header.addCell(cellLogoPrefeitura);

            document.add(header);

            Font tituloFonte = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph titulo = new Paragraph("CONTRATO DE ALUGUEL DA BICICLETA", tituloFonte);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingBefore(20);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            Font fonteNormal = new Font(Font.FontFamily.HELVETICA, 12);

            Paragraph dadosCliente = new Paragraph("CONTRATANTE:\n\n" +
                    "Nome: " + contrato.getCliente().getNome() + "\n" +
                    "E-mail: " + contrato.getCliente().getEmail() + "\n" +
                    "Endereço: " + contrato.getCliente().getEndereco() + "\n" +
                    "Telefone: " + contrato.getCliente().getTelefone() + "\n\n", fonteNormal);
            document.add(dadosCliente);

            Paragraph dadosBike = new Paragraph(
                    "BICICLETA ALUGADA:\n\n" +
                            "Nome: " + contrato.getBicicleta().getNome() + "\n" +
                            "Número: " + contrato.getBicicleta().getNumero() + "\n" +
                            "Modelo: " + contrato.getBicicleta().getModelo() + "\n" +
                            "Marca: " + contrato.getBicicleta().getMarca() + "\n" +
                            "Tipo: " + contrato.getBicicleta().getTipo() + "\n\n", fonteNormal);
            document.add(dadosBike);

            Paragraph clausulas = new Paragraph(
                    "O presente contrato tem como objeto a prestação dos seguintes serviços da Wheels LTDA ao CONTRATANTE:\n\n" +
                            "1 - Aluguel de Bicicleta única;\n" +
                            "2 - Aluguel da bicicleta " + contrato.getBicicleta().getNome() + " de número " + contrato.getBicicleta().getNumero() +
                            ", para o cliente " + contrato.getCliente().getNome() + " de e-mail " + contrato.getCliente().getEmail() + ", no período de tempo do dia " +
                            contrato.getDataInicial().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " até " +
                            contrato.getDataRetorno().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ";\n" +
                            "3 - Caso houver atraso na devolução da bicicleta nos dias previstos, haverá taxa de R$ " +
                            contrato.getTaxaAtraso() + " por dia;\n" +
                            "4 - Em caso de danos à bicicleta, o contratante deverá arcar com os custos equivalentes aos danos", fonteNormal);
            document.add(clausulas);

            double valorTotal = contrato.getNumeroDias() * contrato.getBicicleta().getDiariaTaxaAluguel() + contrato.getBicicleta().getDeposito();
            Paragraph valor = new Paragraph("VALOR TOTAL DO CONTRATO: R$ " + valorTotal + "\n\n" +
                    "O pagamento poderá ser realizado via:\n" +
                    "- Crédito\n- Débito\n- PIX\n- Boleto\n\n", fonteNormal);
            document.add(valor);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
