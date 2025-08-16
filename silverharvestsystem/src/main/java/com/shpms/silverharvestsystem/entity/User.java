package com.shpms.silverharvestsystem.entity;

import com.shpms.silverharvestsystem.Enum.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User implements SuperEntity{
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
