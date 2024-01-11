package com.presta.saving_management.models;

import com.presta.saving_management.dto.CustomerDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", sequenceName = "customer_seq", allocationSize = 1)
    private long id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private String password;
    private String phoneNumber;
    private String memberNumber;
    private LocalDateTime dateCreated = LocalDateTime.now();

    public Customer(long id) {
        this.id = id;
    }

    public CustomerDTO toDTO() {
        return new CustomerDTO(
                getId(), getFirstName(), getLastName(), getEmail(), getPhoneNumber(), getMemberNumber()
        );
    }
}
