package edu.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "numbers_sum")
@Data
@NoArgsConstructor
public class SumNumbers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic(optional = false)
    private long sum;

    public SumNumbers(TwoNumbers twoNumbers) {
        sum = twoNumbers.getA() + twoNumbers.getB();
    }
}
