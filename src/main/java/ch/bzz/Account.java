package ch.bzz;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Account {
    private int id;
    private int accountNumber;
    private String name;
    private Project project;
}