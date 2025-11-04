package ch.bzz;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

public class Project {
    @Id
    @Column(name="projectName", nullable=false, unique=true)
    private String projectName;

    @Column(name="passwordHash", nullable=false)
    private String passwordHash;
}
