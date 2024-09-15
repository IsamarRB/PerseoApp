package com.perseo.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "work_experience")

public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_experience_id")
    private int workExperienceId;

    @Column (name = "company_name")
    private String companyName;

    @Column(name = "position")
    private String position;

    @Column(name = "years_of_experience")
    private int yearsOfExperience;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
