package org.example.Integration;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.example.Models.Contrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;

@Service
public class MercadoPagoService {

    private final String accessToken;
    private final String appBaseUrl;

    @Autowired
    public MercadoPagoService(
            @Value("${mercadopago.access_token.${spring.profiles.active}}") String accessToken,
            @Value("${app.base-url}") String appBaseUrl
    ) {
        this.accessToken = accessToken;
        this.appBaseUrl = appBaseUrl;
    }

    public String criarLinkDePagamento(Contrato contrato, double valorTotal) throws MPException, MPApiException {
        try {
            MercadoPagoConfig.setAccessToken(this.accessToken);

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
                    .success(this.appBaseUrl + "/pagamento/sucesso")
                    .failure(this.appBaseUrl + "/pagamento/falha")
                    .pending(this.appBaseUrl + "/pagamento/pendente")
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(Collections.singletonList(item))
                    .payer(payer)
                    .externalReference(contrato.getIdentificador())
                    .backUrls(backUrls)
                    .notificationUrl(this.appBaseUrl + "/webhook/mercadopago")
                    .autoReturn("approved")
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            return preference.getInitPoint();

        } catch (MPApiException e) {
            System.err.println("Erro da API do Mercado Pago: " + e.getApiResponse().getContent());
            throw e;
        } catch (MPException e) {
            System.err.println("Erro do SDK do Mercado Pago: " + e.getMessage());
            throw e;
        }
    }
}