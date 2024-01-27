package com.neelav.patienthub.controller;

import com.neelav.patienthub.PatientHubApplication;
import com.neelav.patienthub.exceptions.PatientNotFoundException;
import com.neelav.patienthub.model.Patient;
import com.neelav.patienthub.repository.PatientRepository;
import com.neelav.patienthub.service.PatientService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/patient/")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("all")
    public List<Patient> getAllPatients(){
        return patientService.getAllPatients();
    }

    @GetMapping("{id}")
    public ResponseEntity<Patient> patientById(@PathVariable int id){
        return  new ResponseEntity<>(patientService.getPatientById(id),HttpStatus.OK);
    }

    @GetMapping("name/{name}")
    public List<Patient> findByFirstName(@PathVariable String name){
        return patientService.findByFirstName(name);
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid  @RequestBody Patient patient){
        return new ResponseEntity<>(patientService.createPatient(patient),HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable int id , @RequestBody @Valid Patient patient){
        return new ResponseEntity<>(patientService.updatePatient(id,patient),HttpStatus.ACCEPTED);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> removePatient(@PathVariable int id){
        return new ResponseEntity<>(patientService.removePatient(id),HttpStatus.OK) ;
    }
}
