package edu.hibernate.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.RevisionType;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class RevisionPojo implements Comparable<RevisionPojo> {
    private BigInteger id;
    private Integer rev;
    private RevisionType revType;

    @Override
    public int compareTo(RevisionPojo revisionPojo) {
        int idCompare = id.compareTo(revisionPojo.id);
        if (idCompare == 0) {
            int revCompare = rev.compareTo(revisionPojo.rev);
            if (revCompare == 0)
                return revType.compareTo(revisionPojo.revType);
            return revCompare;
        }
        return idCompare;
    }
}
