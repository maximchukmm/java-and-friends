public class EnumAndGeneric {
}

enum Key implements ValueMapper, ValueValidator {
    KEY1(new BooleanValueMapper(), new BooleanValueValidator(), "false"),
    KEY2(new IntegerValueMapper(), new IntegerValueValidator(), "0");

    private ValueMapper mapper;
    private ValueValidator validator;
    private String defaultValue;

    Key(ValueMapper mapper, ValueValidator validator, String defaultValue) {
        this.mapper = mapper;
        this.validator = validator;
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString(Object value) {
        return mapper.toString(value);
    }

    @Override
    public Object fromString(String value) {
        return mapper.fromString(value);
    }

    @Override
    public boolean validate(String value) {
        return validator.validate(value);
    }
}

interface ValueMapper<T> {
    String toString(T value);

    T fromString(String value);
}

class BooleanValueMapper implements ValueMapper<Boolean> {
    @Override
    public String toString(Boolean value) {
        return value.toString();
    }

    @Override
    public Boolean fromString(String value) {
        return Boolean.valueOf(value);
    }
}

class IntegerValueMapper implements ValueMapper<Integer> {
    @Override
    public String toString(Integer value) {
        return value.toString();
    }

    @Override
    public Integer fromString(String value) {
        return Integer.valueOf(value);
    }
}

interface ValueValidator {
    boolean validate(String value);

    //default void validate(Key key, String value, Error error);
}

class BooleanValueValidator implements ValueValidator {
    @Override
    public boolean validate(String value) {
        return "false".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value);
    }
}

class IntegerValueValidator implements ValueValidator {
    @Override
    public boolean validate(String value) {
        try {
            Integer.valueOf(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}