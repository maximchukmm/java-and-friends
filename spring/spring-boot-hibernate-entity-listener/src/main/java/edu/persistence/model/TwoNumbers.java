package edu.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "two_numbers")
@Data
@NoArgsConstructor
public class TwoNumbers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic(optional = false)
    private int a;

    @Basic(optional = false)
    private int b;

    public TwoNumbers(int a, int b) {
        this.a = a;
        this.b = b;
    }
}
