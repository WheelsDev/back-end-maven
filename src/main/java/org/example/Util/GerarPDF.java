package org.example.Util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import jdk.jfr.Timespan;
import org.example.Models.Contrato;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class GerarPDF {
    DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    Font tituloFonte = new Font(Font.FontFamily.HELVETICA, 16,Font.BOLD);
    Font fonteNegrito = new Font(Font.FontFamily.HELVETICA, 14,Font.BOLD);
    Font fonteNormal = new Font(Font.FontFamily.HELVETICA, 12);
    Font fonteMetodosPagamento = new Font(Font.FontFamily.HELVETICA, 12);
    LineSeparator linha = new LineSeparator();
    Chunk linhaLaranja = new Chunk(linha);
    BaseColor corLaranja = new BaseColor(0xF8, 0x73, 0x14);

    public GerarPDF() {}

    public void gerarContratoAluguel(Contrato contrato) {
        Document document = new Document();
        String arquivoContrato = contrato.getIdentificador() + ".pdf";
        Path caminhoArquivo = Paths.get("src", "main", "java", "org", "example", "Util", arquivoContrato);
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

            Font fonteNegrito = new Font(Font.FontFamily.HELVETICA, 14,Font.BOLD);
            Font fonteNormal = new Font(Font.FontFamily.HELVETICA, 12);

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

            double taxaDiariaAtraso = contrato.getBicicleta().getDiariaTaxaAluguel() * 1.50;
            Font fonteItalico = new Font(Font.FontFamily.HELVETICA, 10,Font.ITALIC);
            Paragraph clausulas = new Paragraph(
                    "\nO presente contrato tem como objeto a prestação dos seguintes serviços da Wheels LTDA ao CONTRATANTE:\n\n" +
                            "1 - Aluguel de Bicicleta única;\n" +
                            "2 - Aluguel da bicicleta " + contrato.getBicicleta().getNome() + " de número " + contrato.getBicicleta().getNumero() +
                            ", para o cliente " + contrato.getCliente().getNome() + " de e-mail " + contrato.getCliente().getEmail() + ", no período de tempo do dia " +
                            contrato.getDataInicial().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " até " +
                            contrato.getDataRetorno().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ";\n" +
                            "3 - Caso houver atraso na devolução da bicicleta nos dias previstos, haverá taxa de R$ " +
                            String.format("%.2f",taxaDiariaAtraso) + " por dia;\n" +
                            "4 - Em caso de danos à bicicleta, o contratante deverá arcar com os custos equivalentes aos danos\n\n", fonteItalico);
            document.add(clausulas);
            document.add(linhaLaranja);

            double valorTotal = contrato.getNumeroDias() * contrato.getBicicleta().getDiariaTaxaAluguel() + contrato.getBicicleta().getDeposito();
            Paragraph tituloValor = new Paragraph(String.format("\nVALOR DO CONTRATO : R$%.2f",valorTotal), fonteNegrito);
            document.add(tituloValor);
            Paragraph valor = new Paragraph("\nO valor total dos serviços será de R$"+String.format("%.2f",valorTotal)+", caso houver atraso, haverá um adicional de R$"+String.format("%.2f",taxaDiariaAtraso)+" para cada dia a mais de atraso do contrato, e se houver dano à bicicleta, será cobrado um valor equivalente ao dano causado.", fonteNormal);
            document.add(valor);

            Font fonteMetodosPagamento = new Font(Font.FontFamily.HELVETICA, 12);
            Paragraph metodosPagamento = new Paragraph("\nO pagamento poderá ser realizado via:\n" +
                    "- Dinheiro\n- PIX\n- Crédito\n- Débito\n- Boleto\n", fonteMetodosPagamento);
            document.add(metodosPagamento);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gerarComprovantePagamento(Contrato contrato) {
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
            logoLoja.scaleToFit(80, 80);

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

            PdfPTable mainTable = new PdfPTable(2);
            mainTable.setWidthPercentage(100);
            mainTable.setWidths(new float[]{60, 40});
            mainTable.setSpacingBefore(30);

            PdfPCell tabelaLado1 = new PdfPCell();
            tabelaLado1.setBorder(Rectangle.NO_BORDER);

            Paragraph tituloDeLoja = new Paragraph(
                    "DE:\n", fonteNegrito);
            Paragraph dadosLoja = new Paragraph(
                    "Wheels LTDA\n" +
                            "Rio de Janeiro, Centro\n" +
                            "(21) 99254-4146\n", fonteNormal);

            Paragraph tituloCobrar = new Paragraph(
                    "COBRAR A:\n", fonteNegrito);
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
                            "DATA DO RECIBO "+contrato.getDataInicial().format(dataFormatada) +"\n" +
                            "P.O # CON-"+contrato.getIdentificador()+"\n" +
                            "DATA DE VENCIMENTO "+contrato.getDataRetorno().format(dataFormatada)+"\n", fonteNormal);

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

            double valorDiaria = contrato.getBicicleta().getDiariaTaxaAluguel() * contrato.getNumeroDias();
            double deposito = contrato.getBicicleta().getDeposito();
            double valorAluguel = valorDiaria + deposito;
            long diasDeDiferenca = ChronoUnit.DAYS.between(contrato.getDataInicial(), contrato.getDataRetorno());
            long diasDeAtraso = 0;
            if (diasDeDiferenca > contrato.getNumeroDias()) diasDeAtraso = diasDeDiferenca - contrato.getNumeroDias();
            double taxaAtraso = contrato.getBicicleta().getDiariaTaxaAluguel() + (contrato.getBicicleta().getDiariaTaxaAluguel()/2);
            double taxaAtrasoTotal = taxaAtraso * diasDeAtraso;
            double taxaDano = contrato.getTaxaDano();
            double total = valorDiaria + deposito + taxaAtrasoTotal + taxaDano;
            double devolver = deposito - (taxaAtrasoTotal + taxaDano);
            if (devolver < 0) devolver = 0;

            Paragraph textoEspaco = new Paragraph("\n");
            document.add(textoEspaco);
            linha.setLineColor(corLaranja);
            linha.setLineWidth(3);
            document.add(linhaLaranja);

            document.add(textoEspaco);
            document.add(textoEspaco);

            PdfPTable tabelaDescricaoPrecoValor = new PdfPTable(3);
            tabelaDescricaoPrecoValor.setWidthPercentage(100);
            tabelaDescricaoPrecoValor.setWidths(new float[]{40, 40, 20});

            Paragraph linhaTitulo = new Paragraph("DESCRIÇÃO",fonteNegrito);
            tabelaDescricaoPrecoValor.addCell(linhaTitulo);
            linhaTitulo = new Paragraph("PREÇO",fonteNegrito);
            tabelaDescricaoPrecoValor.addCell(linhaTitulo);
            linhaTitulo = new Paragraph("VALOR",fonteNegrito);
            tabelaDescricaoPrecoValor.addCell(linhaTitulo);

            Paragraph linha1 = new Paragraph(String.format(" • %s",contrato.getBicicleta().getNome()),fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha1);
            linha1 = new Paragraph(String.format("%.2f + %.2f / dia (%d)",contrato.getBicicleta().getDeposito(), contrato.getBicicleta().getDiariaTaxaAluguel(),contrato.getNumeroDias()),fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha1);
            linha1 = new Paragraph(String.format("%.2f",total),fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha1);

            Paragraph linha2 = new Paragraph(" • Atraso na Devolução",fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha2);
            linha2 = new Paragraph(String.format("%.2f / dia (%d dias)",taxaAtraso,diasDeAtraso),fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha2);
            linha2 = new Paragraph(String.format("%.2f",taxaAtrasoTotal),fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha2);

            Paragraph linha3 = new Paragraph(" • Danos à bicicleta",fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha3);
            linha3 = new Paragraph(String.format("%.2f taxa de dano",contrato.getTaxaDano()),fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha3);
            linha3 = new Paragraph(String.format("%.2f",taxaDano),fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linha3);

            Paragraph linhaValor = new Paragraph("TOTAL",fonteNegrito);
            tabelaDescricaoPrecoValor.addCell(linhaValor);
            linhaValor = new Paragraph("",fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linhaValor);
            linhaValor = new Paragraph(String.format("R$ %.2f",total),fonteNormal);
            tabelaDescricaoPrecoValor.addCell(linhaValor);

            document.add(tabelaDescricaoPrecoValor);

            document.add(textoEspaco);

            Paragraph metodoPagamento = new Paragraph("Método de pagamento: Pix",fonteNegrito);
            document.add(metodoPagamento);

            document.add(textoEspaco);
            document.add(textoEspaco);

            PdfPTable tabelaPagoDevolver = new PdfPTable(2);
            tabelaPagoDevolver.setWidthPercentage(100);
            tabelaPagoDevolver.setWidths(new float[]{70,30});
            tabelaPagoDevolver.setPaddingTop(100);

            Paragraph linhaPago = new Paragraph("TOTAL PAGO",fonteNegrito);
            tabelaPagoDevolver.addCell(linhaPago);
            linhaPago = new Paragraph(String.format("R$ %.2f",total),fonteNormal);
            tabelaPagoDevolver.addCell(linhaPago);

            Paragraph linhaDevolvido = new Paragraph("DEVOLVER",fonteNegrito);
            tabelaPagoDevolver.addCell(linhaDevolvido);
            linhaDevolvido = new Paragraph(String.format("R$ %.2f",devolver),fonteNormal);
            tabelaPagoDevolver.addCell(linhaDevolvido);

            document.add(tabelaPagoDevolver);

            PdfPTable tabelaAssinatura = new PdfPTable(2);
            tabelaAssinatura.setWidthPercentage(100);
            tabelaAssinatura.setWidths(new float[]{65,35});

            PdfPCell cedulaVazia = new PdfPCell();
            cedulaVazia.setBorder(Rectangle.NO_BORDER);

            PdfPCell cedulaAssinatura = new PdfPCell();
            cedulaAssinatura.setBorder(Rectangle.NO_BORDER);
            cedulaAssinatura.setVerticalAlignment(Element.ALIGN_MIDDLE);

            arquivoImagem = "assinaturaWheelsLTDA.png";
            caminhoImagem = Paths.get("src","main","java","org","example","Resources",arquivoImagem);
            Image assinaturaFoto = Image.getInstance(caminhoImagem.toString());
            assinaturaFoto.scaleToFit(160, 160);

            cedulaAssinatura.addElement(new Chunk(assinaturaFoto, 0, 0, true));

            tabelaAssinatura.addCell(cedulaVazia);
            tabelaAssinatura.addCell(cedulaAssinatura);
            document.add(textoEspaco);

            document.add(tabelaAssinatura);

            document.add(linhaLaranja);

            document.add(textoEspaco);
            document.add(textoEspaco);

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