package com.presta.saving_management.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Saving {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "saving_seq")
    @SequenceGenerator(name = "saving_seq", sequenceName = "saving_seq", allocationSize = 1)
    private long id;
    @NotEmpty
    private String savingName;
    private double balance;
    private double initialBalance;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Saving(long id) {
        this.id = id;
    }
}
