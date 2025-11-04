package ch.bzz;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Booking {
    private int id;
    private int bookingNumber;
    private String date;
    private int text;
    private Account debitAccount;
    private Account creditAccount;
    private double amount;
    private Project project;
}