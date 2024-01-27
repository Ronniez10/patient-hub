package com.neelav.patienthub.service;

import com.neelav.patienthub.exceptions.PatientNotFoundException;
import com.neelav.patienthub.model.Patient;
import com.neelav.patienthub.repository.PatientRepository;
import com.neelav.patienthub.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @Test
    void testGetAllPatients() {
        // Mock repository behavior
        when(patientRepository.findAll()).thenReturn(Collections.emptyList());

        // Call the service method
        List<Patient> patients = patientService.getAllPatients();

        // Verify the result
        assertEquals(0, patients.size());

        // Verify that the repository method was called
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void testGetPatientById() {
        // Mock repository behavior
        int patientId = 1;
        Patient mockPatient = Patient.builder()
                .id(1)
                .firstname("John")
                .lastname("Doe")
                .age(45)
                .email("johdoe@gmail.com").bloodgroup("AB+").build();
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(mockPatient));

        // Call the service method
        Patient patient = patientService.getPatientById(patientId);

        // Verify the result
        assertEquals(patientId, patient.getId());

        // Verify that the repository method was called
        verify(patientRepository, times(1)).findById(patientId);
    }

    @Test
    void testGetPatientByIdNotFound() {
        // Mock repository behavior
        int nonExistentPatientId = 999;
        when(patientRepository.findById(nonExistentPatientId)).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(nonExistentPatientId));

        // Verify that the repository method was called
        verify(patientRepository, times(1)).findById(nonExistentPatientId);
    }

    @Test
    void testCreatePatient() {
        // Mock repository behavior
        Patient newPatient =Patient.builder()
                .id(1)
                .firstname("John")
                .lastname("Doe")
                .age(45)
                .email("johdoe@gmail.com").bloodgroup("AB+").build();

        when(patientRepository.save(newPatient)).thenReturn(Patient.builder()
                .id(1)
                .firstname("John")
                .lastname("Doe")
                .age(45)
                .email("johdoe@gmail.com").bloodgroup("AB+").build());

        // Call the service method
        Patient createdPatient = patientService.createPatient(newPatient);

        // Verify the result
        assertEquals(1, createdPatient.getId());

        // Verify that the repository method was called
        verify(patientRepository, times(1)).save(newPatient);
    }

    @Test
    void testUpdatePatient() {
        // Mock repository behavior
        int existingPatientId = 1;
        Patient existingPatient = Patient.builder()
                .id(1)
                .firstname("John")
                .lastname("Doe")
                .age(45)
                .email("johdoe@gmail.com")
                .bloodgroup("AB+").build();;
        when(patientRepository.findById(existingPatientId)).thenReturn(Optional.of(existingPatient));

        Patient updatedPatientData = Patient.builder()
                .id(1)
                .firstname("Updated")
                .lastname("Doe")
                .age(26)
                .email("updated.email@example.com")
                .bloodgroup("B-").build();

        when(patientRepository.save(updatedPatientData)).thenReturn(updatedPatientData);


        // Call the service method
        Patient updatedPatient = patientService.updatePatient(existingPatientId, updatedPatientData);

        // Verify the result
        assertEquals("Updated", updatedPatient.getFirstname());
        assertEquals("Doe", updatedPatient.getLastname()); // Ensure that lastname wasn't updated
        assertEquals("updated.email@example.com", updatedPatient.getEmail());
        assertEquals(26, updatedPatient.getAge());
        assertEquals("B-", updatedPatient.getBloodgroup());

        // Verify that the repository method was called
        verify(patientRepository, times(1)).findById(existingPatientId);
        verify(patientRepository, times(1)).save(existingPatient);
    }

    @Test
    void testRemovePatient() {
        // Mock repository behavior
        int existingPatientId = 1;
        Patient existingPatient = Patient.builder()
                .id(1)
                .firstname("John")
                .lastname("Doe")
                .age(45)
                .email("johdoe@gmail.com").bloodgroup("AB+").build();
        when(patientRepository.findById(existingPatientId)).thenReturn(Optional.of(existingPatient));

        // Call the service method
        String result = patientService.removePatient(existingPatientId);

        // Verify the result
        assertEquals("Patient Removed", result);

        // Verify that the repository method was called
        verify(patientRepository, times(1)).findById(existingPatientId);
        verify(patientRepository, times(1)).deleteById(existingPatientId);
    }

    @Test
    void testRemovePatientNotFound() {
        // Mock repository behavior
        int nonExistentPatientId = 999;
        when(patientRepository.findById(nonExistentPatientId)).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        assertThrows(PatientNotFoundException.class, () -> patientService.removePatient(nonExistentPatientId));

        // Verify that the repository method was called
        verify(patientRepository, times(1)).findById(nonExistentPatientId);
    }

    @Test
    void testFindByFirstName() {
        // Mock repository behavior
        String firstName = "John";
        when(patientRepository.findByName(firstName)).thenReturn(Collections.singletonList(Patient.builder()
                .id(1)
                .firstname("John")
                .lastname("Doe")
                .age(45)
                .email("johdoe@gmail.com").bloodgroup("AB+").build()));

        // Call the service method
        List<Patient> patients = patientService.findByFirstName(firstName);

        // Verify the result
        assertEquals(1, patients.size());
        assertEquals("John", patients.get(0).getFirstname());

        // Verify that the repository method was called
        verify(patientRepository, times(1)).findByName(firstName);
    }

}
