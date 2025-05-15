package org.example.Integration;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.example.Models.Contrato;

import java.math.BigDecimal;
import java.util.Collections;

public class MercadoPagoService {

    private static final String ACCESS_TOKEN = "TEST-4595925158591094-051422-a85e5e1889160da7ff39743525a55095-1980586377";

    public String criarPagamento(Contrato contrato, double valorTotal) throws MPException, MPApiException {

        MercadoPagoConfig.setAccessToken(ACCESS_TOKEN);

        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .title("Pagamento do contrato " + contrato.getIdentificador())
                .quantity(1)
                .unitPrice(BigDecimal.valueOf(valorTotal))
                .build();

        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .name(contrato.getCliente().getNome())
                .build();

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("http://localhost:3000/pagamento-sucesso")
                .failure("http://localhost:3000/pagamento-falhou")
                .pending("http://localhost:3000/pagamento-pendente")
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(Collections.singletonList(item))
                .payer(payer)
                .externalReference(contrato.getIdentificador())
                .backUrls(backUrls)
                .autoReturn("approved")
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        return preference.getInitPoint();
    }
}
