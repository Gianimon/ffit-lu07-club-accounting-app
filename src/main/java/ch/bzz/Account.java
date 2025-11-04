package ch.bzz;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="account")

public class Account {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private int id;

    @Column(name="accountNumber", nullable=false, unique=true)
    private int accountNumber;

    @Column(name="name", nullable=false, unique=true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}