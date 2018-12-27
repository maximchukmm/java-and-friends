package edu.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name = "User")
@Table(name = "FINANCES_USER")
@Data
@NoArgsConstructor
public class User {
    @Id

//    @GeneratedValue(strategy = GenerationType.IDENTITY)

//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
//    @SequenceGenerator(name = "user_seq", sequenceName = "USER_ID_SEQ")

//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "user_table_generator")
//    @TableGenerator(
//        name = "user_table_generator",
//        table = "IFINANCES_KEYS",
//        pkColumnName = "PK_NAME",
//        valueColumnName = "PK_VALUE")

    @GeneratedValue

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "BIRTH_DATE", nullable = false)
    private LocalDateTime birthDate;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @Column(name = "LAST_UPDATED_DATE")
    private LocalDateTime lastUpdatedDate;

    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    @Column(name = "CREATED_DATE", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;
}
