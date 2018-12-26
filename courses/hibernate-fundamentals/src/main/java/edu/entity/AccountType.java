package edu.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "account_type")
@Data
@NoArgsConstructor
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_TYPE_ID")
    private Long accountTypeId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LAST_UPDATED_DATE")
    private LocalDate lastUpdatedDate;

    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;
}
