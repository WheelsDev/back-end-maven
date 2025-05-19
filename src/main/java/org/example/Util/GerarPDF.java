package org.example.Util;

import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import javafx.scene.text.TextAlignment;
import org.example.Models.Contrato;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

public class GerarPDF {
    Font tituloFonte = new Font(Font.FontFamily.HELVETICA, 16,Font.BOLD);
    Font fonteNegrito = new Font(Font.FontFamily.HELVETICA, 14,Font.BOLD);
    Font fonteNormal = new Font(Font.FontFamily.HELVETICA, 12);
    Font fonteMetodosPagamento = new Font(Font.FontFamily.HELVETICA, 12);

    public void criarPDF(Contrato contrato) {
        Document document = new Document();
        String arquivoContrato = contrato.getIdentificador() + ".pdf";
        Path caminhoArquivo = Paths.get("src","main","java","org","example","Util",arquivoContrato);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo.toFile()));
            document.open();

            PdfPTable headerTable = new PdfPTable(3);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{15, 70, 15});

            PdfPCell logoCell = new PdfPCell();
            logoCell.setBorder(Rectangle.NO_BORDER);
            logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);

            String arquivoImagem = "LogoWheels.png";
            Path caminhoImagem = Paths.get("src","main","java","org","example","Resources",arquivoImagem);
            Image logoLoja = Image.getInstance(caminhoImagem.toString());
            logoLoja.scaleToFit(60, 60);

            logoCell.addElement(new Chunk(logoLoja, 0, 0, true));

            PdfPCell titleCell = new PdfPCell();
            titleCell.setBorder(Rectangle.NO_BORDER);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            Paragraph titulo = new Paragraph("CONTRATO DE ALUGUEL DA BICICLETA", tituloFonte);
            titulo.setAlignment(Element.ALIGN_CENTER);

            titleCell.addElement(titulo);

            PdfPCell emptyCell = new PdfPCell();
            emptyCell.setBorder(Rectangle.NO_BORDER);

            headerTable.addCell(logoCell);
            headerTable.addCell(titleCell);
            headerTable.addCell(emptyCell);

            document.add(headerTable);

            PdfPTable mainTable = new PdfPTable(2);
            mainTable.setWidthPercentage(100);
            mainTable.setWidths(new float[]{60, 40});
            mainTable.setSpacingBefore(30);

            PdfPCell dataCell = new PdfPCell();
            dataCell.setBorder(Rectangle.NO_BORDER);

            Paragraph tituloCliente = new Paragraph(
                    "CONTRATANTE:\n\n", fonteNegrito);
            Paragraph dadosCliente = new Paragraph(
                    "Nome: " + contrato.getCliente().getNome() + "\n" +
                    "E-mail: " + contrato.getCliente().getEmail() + "\n" +
                    "Endereço: " + contrato.getCliente().getEndereco() + "\n" +
                    "Telefone: " + contrato.getCliente().getTelefone() + "\n\n", fonteNormal);

            Paragraph tituloBike = new Paragraph(
                    "BICICLETA ALUGADA:\n\n", fonteNegrito);
            Paragraph dadosBike = new Paragraph(
                      "Nome: " + contrato.getBicicleta().getNome() + "\n" +
                            "Número: " + contrato.getBicicleta().getNumero() + "\n" +
                            "Modelo: " + contrato.getBicicleta().getModelo() + "\n" +
                            "Marca: " + contrato.getBicicleta().getMarca() + "\n" +
                            "Tipo: " + contrato.getBicicleta().getTipo() + "\n\n", fonteNormal);

            dataCell.addElement(tituloCliente);
            dataCell.addElement(dadosCliente);
            dataCell.addElement(tituloBike);
            dataCell.addElement(dadosBike);

            PdfPCell govLogoCell = new PdfPCell();
            govLogoCell.setBorder(Rectangle.NO_BORDER);
            govLogoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            govLogoCell.setVerticalAlignment(Element.ALIGN_TOP);
            govLogoCell.setPaddingTop(20);

            arquivoImagem = "GovernoRioDeJaneiro.png";
            caminhoImagem = Paths.get("src","main","java","org","example","Resources",arquivoImagem);
            Image logoEstado = Image.getInstance(caminhoImagem.toString());
            logoEstado.scaleToFit(150, 150);

            arquivoImagem = "PrefeituraRioDeJaneiro.png";
            caminhoImagem = Paths.get("src","main","java","org","example","Resources",arquivoImagem);
            Image logoPrefeitura = Image.getInstance(caminhoImagem.toString());
            logoPrefeitura.scaleToFit(150, 150);

            PdfPTable logoTable = new PdfPTable(1);
            logoTable.setWidthPercentage(100);

            PdfPCell estadoCell = new PdfPCell(new Phrase(new Chunk(logoEstado, 0, 0, true)));
            estadoCell.setBorder(Rectangle.NO_BORDER);
            estadoCell.setPaddingBottom(30);

            PdfPCell prefeituraCell = new PdfPCell(new Phrase(new Chunk(logoPrefeitura, 0, 0, true)));
            prefeituraCell.setBorder(Rectangle.NO_BORDER);

            logoTable.addCell(estadoCell);
            logoTable.addCell(prefeituraCell);

            govLogoCell.addElement(logoTable);

            mainTable.addCell(dataCell);
            mainTable.addCell(govLogoCell);

            document.add(mainTable);

            LineSeparator linha = new LineSeparator();
            Chunk linhaLaranja = new Chunk(linha);
            BaseColor corPersonalizada = new BaseColor(0xF8, 0x73, 0x14); // RGB de #FF6600
            linha.setLineColor(corPersonalizada);
            linha.setLineWidth(3);
            document.add(linhaLaranja);

            Font fonteItalico = new Font(Font.FontFamily.HELVETICA, 10,Font.ITALIC);
            Paragraph clausulas = new Paragraph(
                    "\nO presente contrato tem como objeto a prestação dos seguintes serviços da Wheels LTDA ao CONTRATANTE:\n\n" +
                            "1 - Aluguel de Bicicleta única;\n" +
                            "2 - Aluguel da bicicleta " + contrato.getBicicleta().getNome() + " de número " + contrato.getBicicleta().getNumero() +
                            ", para o cliente " + contrato.getCliente().getNome() + " de e-mail " + contrato.getCliente().getEmail() + ", no período de tempo do dia " +
                            contrato.getDataInicial().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " até " +
                            contrato.getDataRetorno().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ";\n" +
                            "3 - Caso houver atraso na devolução da bicicleta nos dias previstos, haverá taxa de R$ " +
                            contrato.getTaxaAtraso() + " por dia;\n" +
                            "4 - Em caso de danos à bicicleta, o contratante deverá arcar com os custos equivalentes aos danos\n\n", fonteItalico);
            document.add(clausulas);
            document.add(linhaLaranja);

            double valorTotal = contrato.getNumeroDias() * contrato.getBicicleta().getDiariaTaxaAluguel() + contrato.getBicicleta().getDeposito();
            Paragraph tituloValor = new Paragraph("\nVALOR DO CONTRATO:" + valorTotal, fonteNegrito);
            document.add(tituloValor);
            Paragraph valor = new Paragraph("\nO valor total dos serviços será de R$ "+valorTotal+", caso houver atraso, haverá um adicional de R$"+valorTotal+" para cada dia a mais de atraso do contrato, caso houver da a ser pago da seguinte forma:" + valorTotal, fonteNormal);
            document.add(valor);

            Paragraph metodosPagamento = new Paragraph("\nO pagamento poderá ser realizado via:\n" +
                    "- Crédito\n- Débito\n- PIX\n- Boleto\n\n", fonteMetodosPagamento);
            document.add(metodosPagamento);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void criarComprovantePagamento(Contrato contrato) {
        Document document = new Document();
        String arquivoComprovantePagamento = "CDP-"+contrato.getIdentificador() + ".pdf";
        Path caminhoArquivo = Paths.get("src","main","java","org","example","Util",arquivoComprovantePagamento);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo.toFile()));
            document.open();

            PdfPTable headerTable = new PdfPTable(3);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{15, 70, 15});

            PdfPCell logoCell = new PdfPCell();
            logoCell.setBorder(Rectangle.NO_BORDER);
            logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);

            String arquivoImagem = "LogoWheels.png";
            Path caminhoImagem = Paths.get("src","main","java","org","example","Resources",arquivoImagem);
            Image logoLoja = Image.getInstance(caminhoImagem.toString());
            logoLoja.scaleToFit(60, 60);

            logoCell.addElement(new Chunk(logoLoja, 0, 0, true));

            PdfPCell titleCell = new PdfPCell();
            titleCell.setBorder(Rectangle.NO_BORDER);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            Font tituloFonte = new Font(Font.FontFamily.HELVETICA, 16,Font.BOLD);
            Paragraph titulo = new Paragraph("COMPROVANTE DE PAGAMENTO", tituloFonte);
            titulo.setAlignment(Element.ALIGN_CENTER);

            titleCell.addElement(titulo);

            PdfPCell emptyCell = new PdfPCell();
            emptyCell.setBorder(Rectangle.NO_BORDER);

            headerTable.addCell(logoCell);
            headerTable.addCell(titleCell);
            headerTable.addCell(emptyCell);

            document.add(headerTable);
//
            PdfPTable mainTable = new PdfPTable(2);
            mainTable.setWidthPercentage(100);
            mainTable.setWidths(new float[]{60, 40});
            mainTable.setSpacingBefore(30);

            PdfPCell tabelaLado1 = new PdfPCell();
            tabelaLado1.setBorder(Rectangle.NO_BORDER);

            Paragraph tituloDeLoja = new Paragraph(
                    "DE:", fonteNegrito);
            Paragraph dadosLoja = new Paragraph(
                    "Wheels LTDA\n" +
                            "Rio de Janeiro, Centro\n" +
                            "(21) 99254-4146\n", fonteNormal);

            Paragraph tituloCobrar = new Paragraph(
                    "COBRAR A:", fonteNegrito);
            Paragraph dadosCobrar = new Paragraph(
                    contrato.getCliente().getNome()+"\n" +
                            contrato.getCliente().getEndereco()+"\n" +
                            contrato.getCliente().getTelefone()+"\n", fonteNormal);
            tabelaLado1.addElement(tituloDeLoja);
            tabelaLado1.addElement(dadosLoja);
            tabelaLado1.addElement(tituloCobrar);
            tabelaLado1.addElement(dadosCobrar);

            PdfPCell tabelaLado2 = new PdfPCell();
            tabelaLado2.setBorder(Rectangle.NO_BORDER);
            tabelaLado2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tabelaLado2.setVerticalAlignment(Element.ALIGN_TOP);

            Paragraph tituloRecibo = new Paragraph(
                    "RECIBO # CDP-"+contrato.getIdentificador()+"\n", fonteNormal);
            Paragraph dadosRecibo = new Paragraph(
                    "DATA DO RECIBO 08/05/12\n" +
                            "P.O # CON-"+contrato.getIdentificador()+"\n" +
                            "DATA DE VENCIMENTO 12/07/2025\n", fonteNormal);

            Paragraph tituloEnviarPara = new Paragraph(
                    "ENVIAR PARA\n", fonteNegrito);
            Paragraph dadosEnviarPara = new Paragraph(
                    contrato.getCliente().getNome()+"\n" +
                            contrato.getCliente().getEndereco()+"\n" +
                            contrato.getCliente().getTelefone()+"\n", fonteNormal);

            tabelaLado2.addElement(tituloRecibo);
            tabelaLado2.addElement(dadosRecibo);
            tabelaLado2.addElement(tituloEnviarPara);
            tabelaLado2.addElement(dadosEnviarPara);

            mainTable.addCell(tabelaLado1);
            mainTable.addCell(tabelaLado2);

            document.add(mainTable);

            // Tabela de itens
            Paragraph textoEspaço = new Paragraph("\n");
            document.add(textoEspaço);
            document.add(textoEspaço);

            PdfPTable tabelaDescricaoPrecoValor = new PdfPTable(3);
            tabelaDescricaoPrecoValor.setWidthPercentage(100);
            tabelaDescricaoPrecoValor.setWidths(new float[]{40, 40, 20});

            PdfPCell tabelaCedula = new PdfPCell();
            logoCell.setBorder(2);
            logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);

            Paragraph linhaTitulo = new Paragraph("DESCRIÇÃO",fonteNegrito);
            tabelaDescricaoPrecoValor.addCell(linhaTitulo);
            linhaTitulo = new Paragraph("PREÇO",fonteNegrito);
            tabelaDescricaoPrecoValor.addCell(linhaTitulo);
            linhaTitulo = new Paragraph("VALOR",fonteNegrito);
            tabelaDescricaoPrecoValor.addCell(linhaTitulo);

            Paragraph linha1 = new Paragraph(" • "+contrato.getBicicleta().getNome(),fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha1);
            linha1 = new Paragraph("123,00",fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha1);
            linha1 = new Paragraph("123,00",fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha1);

            Paragraph linha2 = new Paragraph(" • Atraso na Devolução",fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha2);
            linha2 = new Paragraph("60,5 / dia (2 dias)",fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha2);
            linha2 = new Paragraph("121,00",fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha2);

            Paragraph linha3 = new Paragraph(" • Danos à bicicleta",fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha3);
            linha3 = new Paragraph("199,00",fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha3);
            linha3 = new Paragraph("199,00",fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha3);

            Paragraph linhaValor = new Paragraph("TOTAL",fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linhaValor);
            linhaValor = new Paragraph("",fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linhaValor);
            linhaValor = new Paragraph("R$ 443,00",fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linhaValor);

            document.add(tabelaDescricaoPrecoValor);

            document.add(textoEspaço);

            Paragraph metodoPagamento = new Paragraph("Método de pagamento: Pix",fonteNegrito);
            document.add(metodoPagamento);

            document.add(textoEspaço);
            document.add(textoEspaço);

            PdfPTable tabelaPagoDevolver = new PdfPTable(2);
            tabelaPagoDevolver.setWidthPercentage(100);
            tabelaPagoDevolver.setWidths(new float[]{70,30});
            tabelaPagoDevolver.setPaddingTop(100);

            Paragraph linhaPago = new Paragraph("TOTAL",fonteNegrito);
            tabelaPagoDevolver.addCell(linhaPago);
            linhaPago = new Paragraph("R$ 443,00",fonteNormal);
            tabelaPagoDevolver.addCell(linhaPago);

            Paragraph linhaDevolvido = new Paragraph("DEVOLVER",fonteNegrito);
            tabelaPagoDevolver.addCell(linhaDevolvido);
            linhaDevolvido = new Paragraph("R$ 0,00",fonteNormal);
            tabelaPagoDevolver.addCell(linhaDevolvido);

            document.add(tabelaPagoDevolver);

            document.add(textoEspaço);

            PdfPTable tabelaAssinatura = new PdfPTable(2);
            tabelaAssinatura.setWidthPercentage(100);
            tabelaAssinatura.setWidths(new float[]{80,20});

            PdfPCell cedulaVazia = new PdfPCell();
            cedulaVazia.setBorder(Rectangle.NO_BORDER);

            PdfPCell cedulaAssinatura = new PdfPCell();
            cedulaAssinatura.setBorder(Rectangle.NO_BORDER);
            cedulaAssinatura.setVerticalAlignment(Element.ALIGN_MIDDLE);

            Paragraph assinatura = new Paragraph("Assinatura: ....",fonteNormal);
            assinatura.setAlignment(Element.ALIGN_CENTER);

            cedulaAssinatura.addElement(assinatura);
            tabelaAssinatura.addCell(cedulaVazia);
            tabelaAssinatura.addCell(cedulaAssinatura);

            document.add(tabelaAssinatura);
            document.add(textoEspaço);
            document.add(textoEspaço);

            Paragraph termosTitulo = new Paragraph("TERMOS E CONDIÇÕES",fonteNegrito);
            document.add(termosTitulo);

            Paragraph termosConteudo = new Paragraph("Banco, Mercado Pago\nIBAN: PT32 2573 7284\nCNPJ: 17.2843.1883-79",fonteNormal);
            document.add(termosConteudo);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
