package com.mynt.banking.currency_cloud;

import com.currencycloud.client.CurrencyCloudClient;
import com.currencycloud.client.backoff.BackOff;
import com.currencycloud.client.backoff.BackOffResult;
import com.currencycloud.client.backoff.BackOffResultStatus;
import com.currencycloud.client.model.Account;
import com.currencycloud.client.model.DetailedRate;
import com.mynt.banking.currency_cloud.request.CreateAccountRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.Callable;

@Service
@AllArgsConstructor
@Configuration
public class CurrencyCloudClientService {

    private final CurrencyCloudClient currencyCloudClient;
    private final ModelMapper modelMapper;

    public Optional<DetailedRate> getDetailedRate(String currency1, String currency2, String type, BigDecimal amount) {
        return executeWithBackOff(() ->
                currencyCloudClient.detailedRates(currency1, currency2, type, amount, null, null));

    }

    public void createAccount(CreateAccountRequest request) {
        Optional<Account> account = executeWithBackOff(() ->
                currencyCloudClient.createAccount(modelMapper.map(request, Account.class))
        );

        account.ifPresentOrElse(
                acc -> System.out.println("Created Account ID: " + acc.getId()),
                () -> System.out.println("Account creation failed.")
        );
    }




    @SneakyThrows
    private <T> Optional<T> executeWithBackOff(Callable<T> task) {
        BackOffResult<T> result = BackOff.<T>builder()
                .withTask(task)
                .execute();
        System.out.println(result.status);
        System.out.println(result.data);
        if (result.status == BackOffResultStatus.SUCCESSFUL) {
            return result.data;
        } else {
            throw new Exception("Exceeded maximum number of retries");
        }
    }

    public void authenticate() {
        currencyCloudClient.authenticate();
    }
}
