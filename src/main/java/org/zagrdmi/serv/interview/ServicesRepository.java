package org.zagrdmi.serv.interview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zagrdmi.serv.interview.entity.Service;

@Repository
public interface ServicesRepository extends JpaRepository<Service, Long> {
}
