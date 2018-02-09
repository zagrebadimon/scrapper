package org.zagrdmi.serv.interview;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        System.setProperty("javax.net.debug", "ssl:handshake:verbose:keymanager:trustmanager");

        System.setProperty("https.protocols", "TLSv1.1");

        System.setProperty("javax.net.ssl.trustStore", "google.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "notasecret");

        System.setProperty("javax.net.ssl.trustStore", "key.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");


        SpringApplication.run(Main.class, args);
    }
}
