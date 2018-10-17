package experiments;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class DataAnnotationWIthImplementedEqualsAndHashCode {
    private String string;

    @Override
    public boolean equals(Object o) {
        System.out.println("equals");
        if (this == o) return true;
        if (!(o instanceof DataAnnotationWIthImplementedEqualsAndHashCode)) return false;
        DataAnnotationWIthImplementedEqualsAndHashCode that = (DataAnnotationWIthImplementedEqualsAndHashCode) o;
        return Objects.equals(string, that.string);
    }

    @Override
    public int hashCode() {
        System.out.println("hashCode");
        return Objects.hash(string);
    }

    public static void main(String[] args) {
        System.out.println(
            new DataAnnotationWIthImplementedEqualsAndHashCode().equals(
                new DataAnnotationWIthImplementedEqualsAndHashCode()
            )
        );
    }
}
