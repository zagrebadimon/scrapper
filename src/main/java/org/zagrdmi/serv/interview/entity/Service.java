package org.zagrdmi.serv.interview.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "serviceId", referencedColumnName = "id")
    private Collection<ServiceInfo> details = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service)) return false;
        Service services = (Service) o;
        return Objects.equals(id, services.id) &&
                Objects.equals(name, services.name) &&
                Objects.equals(category, services.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category);
    }

    public Collection<ServiceInfo> getDetails() {
        return details;
    }

    public void setDetails(Collection<ServiceInfo> details) {
        this.details = details;
    }
}
