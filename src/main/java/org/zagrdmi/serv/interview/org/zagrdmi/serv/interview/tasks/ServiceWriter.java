package org.zagrdmi.serv.interview.org.zagrdmi.serv.interview.tasks;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.zagrdmi.serv.interview.ServicesRepository;
import org.zagrdmi.serv.interview.entity.Service;

import java.util.List;

public class ServiceWriter implements ItemWriter<Service> {

    @Autowired
    ServicesRepository servicesRepository;

    @Override
    public void write(List<? extends Service> list) throws Exception {
        System.out.println("Write " + list);
        servicesRepository.save(list);
    }
}
