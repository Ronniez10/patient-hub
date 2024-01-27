package com.neelav.patienthub.service;

import com.neelav.patienthub.exceptions.PatientNotFoundException;
import com.neelav.patienthub.model.Patient;
import com.neelav.patienthub.repository.PatientRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    @Cacheable(value = "Patient",key = "#id")
    public Patient getPatientById(int id){
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        return optionalPatient.orElseThrow(() -> new PatientNotFoundException("Patient with id "+id+" does not exist"));
    }


    public Patient createPatient(Patient patient){
        return patientRepository.save(patient);
    }


    @CachePut(value ="Patient", key = "#id")
    public Patient updatePatient(int id,Patient p){
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        Patient patient = optionalPatient.orElseThrow(() -> new PatientNotFoundException("Patient with id "+id+" does not exist"));
        updatePatientData(p,patient);
        return patientRepository.save(patient);
    }

    private void updatePatientData(Patient updatedPatient, Patient patient) {

        if(!StringUtils.isEmpty(updatedPatient.getFirstname()))
            patient.setFirstname(updatedPatient.getFirstname());

        if(!StringUtils.isEmpty(updatedPatient.getMiddlename()))
            patient.setMiddlename(updatedPatient.getMiddlename());

        if(!StringUtils.isEmpty(updatedPatient.getLastname()))
            patient.setLastname(updatedPatient.getLastname());

        if(!StringUtils.isEmpty(updatedPatient.getEmail()))
            patient.setEmail(updatedPatient.getEmail());

        if(updatedPatient.getAge()!= null)
            patient.setAge(updatedPatient.getAge());

        if(!StringUtils.isEmpty(updatedPatient.getBloodgroup()))
            patient.setBloodgroup(updatedPatient.getBloodgroup());

        if(updatedPatient.getDateOfBirth()!= null)
            patient.setDateOfBirth(updatedPatient.getDateOfBirth());

    }


    @CacheEvict(cacheNames = "Patient", key = "#id", beforeInvocation = true)
    public String removePatient(int id){
        Optional<Patient> byId = patientRepository.findById(id);
        if(byId.isPresent()) {
            patientRepository.deleteById(id);
            return "Patient Removed";
        }
        else
            throw new PatientNotFoundException("Patient does not exist !!");
    }

    @Cacheable(value = "Patient",key = "#name")
    public List<Patient> findByFirstName(String name) {
        //patientRepository.findByFirstname(name);
        return patientRepository.findByName(name);
    }
}
