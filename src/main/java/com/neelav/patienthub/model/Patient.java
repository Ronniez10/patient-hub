package com.neelav.patienthub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.neelav.patienthub.exceptions.BloodGroupConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient implements Serializable {

    private static final long serialVersionUID = 1234567L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Cannot be Empty")
    private String firstname;

    private String middlename;

    private String lastname;

    @Email
    @NotBlank(message = "Cannot be Empty")
    private String email;

    @Column(nullable = false)
    @Max(value = 150,message = "Patient cannot be older than 150")
    private Integer age;


    @Column(nullable = false)
    @BloodGroupConstraint
    private String bloodgroup;

    @JsonIgnore
    private Date dateOfBirth;

}