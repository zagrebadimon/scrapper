package org.zagrdmi.serv.interview.org.zagrdmi.serv.interview.tasks;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.CustomsearchRequestInitializer;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import org.springframework.batch.item.ItemProcessor;
import org.zagrdmi.serv.interview.entity.Service;
import org.zagrdmi.serv.interview.entity.ServiceInfo;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class ServiceProcessor implements ItemProcessor<Service, Service> {

    @Override
    public Service process(Service service) throws Exception {

        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        NetHttpTransport transport = (new NetHttpTransport.Builder()).setSslSocketFactory(sc.getSocketFactory()).build();

        Customsearch cs = new Customsearch.Builder(transport, JacksonFactory.getDefaultInstance(), null)
                .setApplicationName("Scrapper")
                .setGoogleClientRequestInitializer(new CustomsearchRequestInitializer("AIzaSyCX2mkbM0dvMwOLwKbFq3Z3lsEblDZtNMY"))
                .build();

        Customsearch.Cse.List list = cs.cse().list(service.getName()).setCx("008941999053643613628:wyw8rzd7hn8");

        Search result = list.execute();
        if (result.getItems() != null) {
            for (Result ri : result.getItems()) {

                ServiceInfo serviceInfo = new ServiceInfo(ri.getTitle(), ri.getLink());
                service.getDetails().add(serviceInfo);
            }
        }

        return service;
    }
}
