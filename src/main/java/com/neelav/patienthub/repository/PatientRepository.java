package com.neelav.patienthub.repository;

import com.neelav.patienthub.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer> {

    public List<Patient> findByFirstname(String name);

    @Query(nativeQuery = true,value = "SELECT * from patient p where p.firstname LIKE %:name%")
    public List<Patient> findByName(@Param("name") String name);
}
