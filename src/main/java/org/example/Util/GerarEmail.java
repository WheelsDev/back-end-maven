package org.example.Util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.example.Models.Cliente;
import org.example.Models.Contrato;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class GerarEmail {
    private final String remetente = "wheelsltda@gmail.com";
    private final String senha = "aghh xeus srsg riyy";

    private Properties propriedadesEmail() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.port", "587");
        return props;
    }

    public void enviarContratoDeAluguel(Cliente cliente, Contrato contrato) {
        String arquivoContrato = contrato.getIdentificador() + ".pdf";
        Path caminhoArquivo = Paths.get("src","main","java","org","example","Util",arquivoContrato);
        String destinatario = cliente.getEmail();
        String assunto = "Envio de Contrato de Aluguel de Bicicleta";
        String corpo = "Prezado(a) "+cliente.getNome()+",\n" +
                "\n" +
                "Tudo bem?\n" +
                "\n" +
                "Conforme combinado, estou encaminhando em anexo o contrato referente ao aluguel da bicicleta. Por favor, leia com atenção para verificar o contrato com os nossos termos.\n" +
                "\n" +
                "Se tiver qualquer dúvida ou precisar de ajustes, estou à disposição para ajudar.\n" +
                "\n" +
                "Agradeço pela confiança e volte sempre!\n" +
                "\n" +
                "Atenciosamente,\n" +
                "Wheels LTDA\n" +
                "Brasil - Rio de Janeiro - Centro\n" +
                "+55 (21) 99822-5741\n" +
                "wheelsltda@gmail.com\n" +
                "\n";

        Session session = Session.getInstance(propriedadesEmail(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha);
            }
        });
        try {
            Message mensagem = new MimeMessage(session);
            mensagem.setFrom(new InternetAddress(remetente));
            mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensagem.setSubject(assunto);
            mensagem.setText(corpo);
            Multipart multipart = new MimeMultipart();
            MimeBodyPart corpoTexto = new MimeBodyPart();
            corpoTexto.setText(corpo);
            multipart.addBodyPart(corpoTexto);
            MimeBodyPart anexo = new MimeBodyPart();
            anexo.attachFile(caminhoArquivo.toFile());
            multipart.addBodyPart(anexo);
            mensagem.setContent(multipart);
            Transport.send(mensagem);
            System.out.println("Mensagem enviada com sucesso!");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void enviarComprovantePagamento(Cliente cliente, Contrato contrato) {
        String arquivoComprovantePagamento = "CDP-" + contrato.getIdentificador() + ".pdf";
        Path caminhoArquivo = Paths.get("src","main","java","org","example","Util",arquivoComprovantePagamento);
        String destinatario = cliente.getEmail();
        String assunto = "Envio do Comprovante de Pagamento";
        String corpo = "Prezado(a) " + cliente.getNome() + ",\n" +
                "\n" +
                "Como vai?\n" +
                "\n" +
                "Segue em anexo o comprovante de pagamento referente ao aluguel da bicicleta.\n" +
                "Verifique, por gentileza, se todas as informações estão corretas.\n" +
                "\n" +
                "Em caso de qualquer dúvida ou necessidade de correção, fico à disposição para auxiliá-lo(a).\n" +
                "\n" +
                "Agradecemos pela preferência!\n" +
                "Volte sempre aos pedais!\n" +
                "\n" +
                "Atenciosamente,\n" +
                "Wheels LTDA\n" +
                "Brasil - Rio de Janeiro - Centro\n" +
                "+55 (21) 99822-5741\n" +
                "wheelsltda@gmail.com\n" +
                "\n";
        Session session = Session.getInstance(propriedadesEmail(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha);
            }
        });
        try {
            Message mensagem = new MimeMessage(session);
            mensagem.setFrom(new InternetAddress(remetente));
            mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensagem.setSubject(assunto);
            mensagem.setText(corpo);
            Multipart multipart = new MimeMultipart();
            MimeBodyPart corpoTexto = new MimeBodyPart();
            corpoTexto.setText(corpo);
            multipart.addBodyPart(corpoTexto);
            MimeBodyPart anexo = new MimeBodyPart();
            anexo.attachFile(caminhoArquivo.toFile());
            multipart.addBodyPart(anexo);
            mensagem.setContent(multipart);
            Transport.send(mensagem);
            System.out.println("Mensagem enviada com sucesso!");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
