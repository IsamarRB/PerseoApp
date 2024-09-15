package com.perseo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payments")

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column (name = "payment_id")
    private int paymentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String paymentStatus;
    private double amount;

    @ManyToMany
    private List<Course> courses = new ArrayList<>();
}

