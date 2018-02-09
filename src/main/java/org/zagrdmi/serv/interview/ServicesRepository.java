package org.zagrdmi.serv.interview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.zagrdmi.serv.interview.entity.Service;

@Repository
public interface ServicesRepository extends CrudRepository<Service, Long> {
}
