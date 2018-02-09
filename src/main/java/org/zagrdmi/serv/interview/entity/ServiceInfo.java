package org.zagrdmi.serv.interview.entity;

import javax.persistence.*;

@Entity
public class ServiceInfo {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Integer id;
    private String title;
    private String link;

    @Column(name = "serviceId")
    private Integer serviceId;

    public ServiceInfo(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public ServiceInfo() {
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
