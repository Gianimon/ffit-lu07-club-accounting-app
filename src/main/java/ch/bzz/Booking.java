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
@Table(name="project")

public class Booking {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private int id;

    @Column(name="bookingNumber", nullable=false, unique=true)
    private int bookingNumber;

    @Column(name="date", nullable=false)
    @Temporal(TemporalType.DATE)
    private String date;

    @Column(name="text", nullable=false)
    private int text;

    @ManyToOne
    @JoinColumn(name = "debit_account_id")
    private Account debitAccount;

    @ManyToOne
    @JoinColumn(name = "credit_account_id")
    private Account creditAccount;

    @Column(name="amount", nullable=false)
    private double amount;

    @ManyToOne
    @JoinColumn(name = "project_project_name")
    private Project project;
}