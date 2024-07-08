package com.mynt.banking.config;

import com.currencycloud.client.CurrencyCloudClient;
import com.currencycloud.client.exception.AuthenticationException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Configuration
@AllArgsConstructor
public class Reauthenticator implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(Reauthenticator.class);
    private final CurrencyCloudClient client;
    private final Object target;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        int reattemptsLeft = 2;

        do {
            try {
                log.trace("Attempting {}; will retry {} times after this.", method.getName(), reattemptsLeft);
                return method.invoke(target, args);
            } catch (AuthenticationException e) {
                log.info("Attempt at {} failed with {}", method.getName(), e.toString());
                if (reattemptsLeft-- <= 0) {
                    throw e;
                }
                log.info("Reauthenticating.");
                client.authenticate();
            }
        } while (true);
    }
    public static CurrencyCloudClient createProxy(CurrencyCloudClient client) {
        return (CurrencyCloudClient) Proxy.newProxyInstance(
                client.getClass().getClassLoader(),
                client.getClass().getInterfaces(),
                new Reauthenticator(client, client)
        );
    }
}
