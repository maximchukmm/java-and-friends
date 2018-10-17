package experiments.getteronfield.classwithgetteronfield;

import lombok.Getter;

public class GetterOnField {
    public GetterOnField(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    @Getter
    private String field1;

    private String field2;
}
