package experiments.getteronfield;

import experiments.getteronfield.classwithgetteronfield.GetterOnField;

public class Main {
    public static void main(String[] args) {
        GetterOnField getterOnField = new GetterOnField("1", "2");

        System.out.println(getterOnField.getField1());
    }
}
