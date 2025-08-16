package com.shpms.silverharvestsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shpms.silverharvestsystem.Enum.Gender;
import com.shpms.silverharvestsystem.Enum.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Staff")
public class Staff implements SuperEntity {
    @Id
    private String StaffId;
    private String FirstName;
    private String LastName;
    private String Designation;

    @Enumerated(EnumType.STRING)
    private Gender Gender;

    private Date JoinedDate;
    private Date DOB;
    private String address;
    private String Contact_No;
    private String Email;

    @OneToMany(mappedBy = "staff",cascade = CascadeType.REMOVE)
    @JsonManagedReference  // Serialize the vehicles for the staff
    private List<Vehicle> vehicles;

    private Role role;

    @OneToMany(mappedBy = "staff",cascade = CascadeType.REMOVE)
    @JsonManagedReference  // Serialize the equipment for the staff
    private List<Equipment> equipment;

    /*@ManyToMany(mappedBy = "staffs", cascade = {CascadeType.PERSIST})*/
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Field_Staff",
            joinColumns = @JoinColumn(name = "staffId"),
            inverseJoinColumns = @JoinColumn(name = "fieldCode")
    )
    private List<Field> fields;

    @ManyToOne
    @JsonBackReference  // Prevent infinite recursion on the log relationship
    @JoinColumn(name = "logCode", nullable = false)
    private MoniteringLog log;
}
