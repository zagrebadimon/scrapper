package org.zagrdmi.serv.interview.org.zagrdmi.serv.interview.tasks;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.CustomsearchRequestInitializer;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.zagrdmi.serv.interview.entity.Service;
import org.zagrdmi.serv.interview.entity.ServiceInfo;

import java.util.HashMap;

public class ServiceProcessor implements ItemProcessor<Service, Service> {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Service process(Service service) throws Exception {


        String url = "https://www.googleapis.com/customsearch/v1";

        HashMap<String, String> query = new HashMap<>();
        query.put("q", service.getName());
        query.put("key", "AIzaSyCX2mkbM0dvMwOLwKbFq3Z3lsEblDZtNMY");
        query.put("cx", "008941999053643613628:wyw8rzd7hn8");



        Customsearch cs = new Customsearch.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
                .setApplicationName("Scrapper")
                .setGoogleClientRequestInitializer(new CustomsearchRequestInitializer("AIzaSyCX2mkbM0dvMwOLwKbFq3Z3lsEblDZtNMY"))
                .build();

        //Set search parameter
        Customsearch.Cse.List list = cs.cse().list(service.getName()).setCx("008941999053643613628:wyw8rzd7hn8");

        //Execute search
        Search result = list.execute();
        if (result.getItems()!=null){
            for (Result ri : result.getItems()) {
                //Get title, link, body etc. from search
                System.out.println(ri.getTitle() + ", " + ri.getLink());
            }
        }


        ///ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, query);





        System.out.println("processing " + service.getName() +  "  " + Thread.currentThread().getName());

        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setLink("http://" + service.getName() + ".com");

        service.getDetails().add(serviceInfo);

        return service;
    }
}
