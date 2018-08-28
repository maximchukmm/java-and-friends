package edu.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity(name = "JodaTime")
@Table(name = "joda_time")
@Data
@NoArgsConstructor
public class JodaTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic(optional = false)
    @Column(name = "start")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime",
        parameters = {@org.hibernate.annotations.Parameter(name = "databaseZone", value = "UTC"),
            @org.hibernate.annotations.Parameter(name = "javaZone", value = "UTC")})
    private DateTime start;

    public JodaTime(DateTime start) {
        this.start = start;
    }
}
