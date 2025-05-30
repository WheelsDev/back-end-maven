package org.example.Integration;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.example.Models.Contrato;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;

@Service
public class MercadoPagoService {

    public String criarPagamento(Contrato contrato, double valorTotal) throws MPException, MPApiException {
        try {
            MercadoPagoConfig.setAccessToken("TEST-4595925158591094-051422-a85e5e1889160da7ff39743525a55095-1980586377");

            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title("Pagamento do contrato " + contrato.getIdentificador())
                    .quantity(1)
                    .description("Aluguel de bicicleta por " + contrato.getNumeroDias() + " dias")
                    .currencyId("BRL")
                    .unitPrice(BigDecimal.valueOf(valorTotal))
                    .build();

            PreferencePayerRequest payer = PreferencePayerRequest.builder()
                    .name(contrato.getCliente().getNome())
                    .email(contrato.getCliente().getEmail())
                    .build();

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("https://www.mercadopago.com.br")
                    .failure("https://www.mercadopago.com.br")
                    .pending("https://www.mercadopago.com.br")
                    .build();

            PreferencePaymentMethodsRequest paymentMethods = PreferencePaymentMethodsRequest.builder()
                    // Não define defaultPaymentMethodId
                    // Não exclui nada — permite Pix, Cartão, Boleto, etc.
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(Collections.singletonList(item))
                    .payer(payer)
                    .externalReference(contrato.getIdentificador())
                    .backUrls(backUrls)
                    .notificationUrl("https://www.example.com/webhook/mercadopago")
                    .paymentMethods(paymentMethods)
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            return preference.getSandboxInitPoint();

        } catch (MPApiException e) {
            System.out.println("Status: " + e.getApiResponse().getStatusCode());
            System.out.println("Content: " + e.getApiResponse().getContent());
            throw e;
        } catch (MPException e) {
            System.out.println("Error MP: " + e.getMessage());
            throw e;
        }
    }
}
