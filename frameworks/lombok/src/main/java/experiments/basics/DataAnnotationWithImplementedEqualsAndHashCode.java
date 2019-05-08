package experiments.basics;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class DataAnnotationWithImplementedEqualsAndHashCode {
    private String string;

    @Override
    public boolean equals(Object o) {
        System.out.println("equals");
        if (this == o) return true;
        if (!(o instanceof DataAnnotationWithImplementedEqualsAndHashCode)) return false;
        DataAnnotationWithImplementedEqualsAndHashCode that = (DataAnnotationWithImplementedEqualsAndHashCode) o;
        return Objects.equals(string, that.string);
    }

    @Override
    public int hashCode() {
        System.out.println("hashCode");
        return Objects.hash(string);
    }

    public static void main(String[] args) {
        System.out.println(
            new DataAnnotationWithImplementedEqualsAndHashCode().equals(
                new DataAnnotationWithImplementedEqualsAndHashCode()
            )
        );
    }
}
