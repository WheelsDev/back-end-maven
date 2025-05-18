package org.example.Util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.example.Models.Cliente;

import java.io.File;
import java.util.Properties;

public class GerarEmail {
    private final String remetente = "wheelsltda@gmail.com";
    private final String senha = "aghh xeus srsg riyy";

    private final Properties propriedadesEmail() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }
    public void enviarContratoDeAluguel(Cliente cliente) {//File contratoPDF
        String destinatario = "richard.alves@al.infnet.edu.br"; //cliente.getEmail();
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
                "+55 (21) 99822-8014\n" +
                "@wheelsltda@gmail.com\n" +
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
            //message. "File";
            Transport.send(mensagem);
            System.out.println("Mensagem enviada com sucesso!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
