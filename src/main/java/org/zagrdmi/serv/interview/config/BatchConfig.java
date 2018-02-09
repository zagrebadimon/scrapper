package org.zagrdmi.serv.interview.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.zagrdmi.serv.interview.entity.Service;
import org.zagrdmi.serv.interview.org.zagrdmi.serv.interview.tasks.ServiceProcessor;
import org.zagrdmi.serv.interview.org.zagrdmi.serv.interview.tasks.ServiceReader;
import org.zagrdmi.serv.interview.org.zagrdmi.serv.interview.tasks.ServiceWriter;

import javax.net.ssl.SSLContext;


@Configuration
public class BatchConfig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean
    public RestTemplate restTemplate() throws Exception {
        /*ClassPathResource classPathResource = new ClassPathResource("key.jks");
        SSLContext sslContext = SSLContexts
                .custom()
                .loadTrustMaterial(classPathResource.getFile(), "changeit".toCharArray())
                .build();
        final CloseableHttpClient client = HttpClients
                .custom()
                .setSSLContext(sslContext)
                .build();
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));*/
        return new RestTemplate();
    }

    @Bean
    public ServiceProcessor serviceProcessor() {
        return new ServiceProcessor();
    }

    @Bean("servicesReader")
    public ItemReader<Service> servicesReader() {
        return new ServiceReader();
    }

    @Bean("servicesWriter")
    public ServiceWriter servicesWriter() {
        return new ServiceWriter();
    }

    @Bean("loadServicesJob")
    public Job loadServicesJob() {

        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(2);

        return  jobBuilderFactory.get("loadServicesJob")
                .incrementer(new RunIdIncrementer())
                .start(loadServicesStep())
                .build();
    }

    @Bean("loadServicesStep")
    public TaskletStep loadServicesStep() {
        return stepBuilderFactory.get("loadServicesStep")
                .<Service, Service>chunk(2)
                .reader(servicesReader())
                .processor(serviceProcessor())
                .writer(servicesWriter())
                .taskExecutor(taskExecutor())
                .throttleLimit(3)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(3);
        return taskExecutor;
    }


}

