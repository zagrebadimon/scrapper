package org.zagrdmi.serv.interview.org.zagrdmi.serv.interview.tasks;

import org.springframework.batch.item.*;
import org.zagrdmi.serv.interview.entity.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceReader implements ItemStreamReader<Service> {

    private final List<String> companies = new ArrayList<>();

    @Override
    public synchronized Service read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (companies.size() == 0) {
            return null;
        }

        String name = companies.remove(0);
        if (name == null) {
            return null;
        }

        Service service = new Service();
        service.setCategory("development");
        service.setName(name);
        return service;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {

        companies.add("Google");
        companies.add("Microsoft");
        companies.add("SpaceX");
        companies.add("Amazon");
        companies.add("Luxoft");
        companies.add("Epam");
        companies.add("Oracle");
        companies.add("Sun");
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() throws ItemStreamException {

    }
}
