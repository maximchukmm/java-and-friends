package edu.enums;

import org.junit.Test;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class EnumMapTest {

    @Test
    public void enumMappingBetweenTwoEnums() {
        for (WinterFull winterFull : WinterFull.values()) {
            WinterShort winterShort = WinterShort.fromWinterFull(winterFull);
            System.out.println(winterShort.readableForm); //todom make actual assertions instead of prints
            System.out.println(winterShort.winterFull);
        }
    }

    enum WinterShort {
        DEC("December", WinterFull.DECEMBER),
        JAN("January", WinterFull.JANUARY),
        FEB("February", WinterFull.FEBRUARY);

        private static final EnumMap<WinterFull, WinterShort> WINTER_FULL_MAPPER = new EnumMap<>(
            EnumSet.allOf(WinterShort.class)
                .stream()
                .collect(Collectors.toMap(WinterShort::getWinterFull, Function.identity()))
        );

        private final String readableForm;
        private final WinterFull winterFull;

        WinterShort(String readableForm, WinterFull winterFull) {
            this.readableForm = readableForm;
            this.winterFull = winterFull;
        }

        public String getReadableForm() {
            return readableForm;
        }

        public WinterFull getWinterFull() {
            return winterFull;
        }

        public static WinterShort fromWinterFull(WinterFull winterFull) {
            return winterFull == null ? null : WINTER_FULL_MAPPER.get(winterFull);
        }
    }

    enum WinterFull {
        DECEMBER,
        JANUARY,
        FEBRUARY;
    }

    @Test
    public void enumMappingBetweenEnumAndItsValue() {
        for (Number expected : Number.values()) {
            Number actual = Number.readableFormToNumber(expected.getReadableForm());
            assertEquals(expected, actual);
        }
    }

    enum Number {
        ONE("One"),
        TWO("Two"),
        THREE("Three");

        private static final Map<String, Number> READABLE_FORM_MAP =
            EnumSet.allOf(Number.class)
                .stream()
                .collect(Collectors.toMap(Number::getReadableForm, Function.identity()));

        private final String readableForm;

        Number(String readableForm) {
            this.readableForm = readableForm;
        }

        public String getReadableForm() {
            return readableForm;
        }

        public static Number readableFormToNumber(String readableForm) {
            return READABLE_FORM_MAP.get(readableForm);
        }
    }
}
