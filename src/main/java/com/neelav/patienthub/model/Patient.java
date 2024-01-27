package com.neelav.patienthub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
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
    private String bloodgroup;

    private Date dateOfBirth;

}