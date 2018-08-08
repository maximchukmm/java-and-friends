package edu.diffclasses;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

class NumbersDemo {
    public static void main(String[] args) {
//        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
//        decimalFormat.applyPattern(".#######");
//        float[] values = new float[]{
//            round2Decimal(123.125F),
//            round2Decimal(0.0F),
//            round2Decimal(0.1F),
//            round2Decimal(0.456F),
//            round2Decimal(111.456F),
//            round2Decimal(10.10F),
//            round2Decimal(10.000F)
//        };
//        for (float v : values) {
//            System.out.println(v + " vs " + decimalFormat.format(v));
//        }

//        System.out.println(2.0 - 1.1);
//        System.out.println(2.0 - 1.2);
//        System.out.println(new Double(2.0 - 1.1));
//        System.out.println(BigDecimal.valueOf(2.0 - 1.1).toPlainString());
//        printDecimal(1.0);
//        printDecimal(1.0D);
//        printDecimal(1.0F);
//        printDecimal(round2Decimal(123.125F));


//        float f = 1.12412351965F;
//        BigDecimal bigDecimal = new BigDecimal(f);
//        BigDecimal bigDecimal1 = bigDecimal.setScale(2, BigDecimal.ROUND_CEILING);
//        float newF = bigDecimal1.floatValue();
//        System.out.println(f);
//        System.out.println(newF);
//        System.out.println(Float.compare(f, 0.0F));
//        System.out.println(Float.compare(0.0F, f));
//        float zeroF = 0.0F;
//        System.out.println(Float.compare(zeroF, 0.0F));
//
//

//        List<Float> floats = new ArrayList<>();
//        floats.add(1F);
//        floats.add(1.1F);
//        floats.add(1.11F);
//        floats.add(1.111F);
//        floats.add(1.1111F);
//        floats.add(1.11111F);

//        for (RoundingMode roundingMode : RoundingMode.values()) {
//            roundingModeTest(roundingMode);
//            System.out.println();
//        }

//        MathContext mathContext = new MathContext(19, RoundingMode.HALF_UP);
//        BigDecimal number = new BigDecimal(123.5555555F, mathContext);
//        System.out.println("number = " + number + " with scale " + number.scale());
//        number = number.setScale(2, RoundingMode.HALF_UP);
//        System.out.println("after set scale=2:");
//        System.out.println("number= " + number + " with scale " + number.scale());

//        BigDecimal bigDecimal1 = new BigDecimal("12345.987654321");
//        System.out.println("bigDecimal1 = " + bigDecimal1 + " with scale " + bigDecimal1.scale());

//        DecimalFormat decimalFormat = new DecimalFormat("#####################.##############################");
//        System.out.println("Float.MIN_EXPONENT = " + Float.MIN_EXPONENT);
//        System.out.println("Float.MIN_NORMAL = " + decimalFormat.format(Float.MIN_NORMAL));
//        System.out.println("Float.MIN_VALUE = " + decimalFormat.format(Float.MIN_VALUE));
//        System.out.println("Float.MAX_EXPONENT = " + Float.MAX_EXPONENT);
//        System.out.println("Float.MAX_VALUE = " + decimalFormat.format(Float.MAX_VALUE));

//        System.out.println(String.format(" %s - %d", Integer.toBinaryString(Integer.MAX_VALUE), Integer.toBinaryString(Integer.MAX_VALUE).length()));
//        System.out.println(String.format("%s - %d", Integer.toBinaryString(Integer.MIN_VALUE + 1), Integer.toBinaryString(Integer.MIN_VALUE + 1).length()));
//        System.out.println(String.format("%s - %d", Integer.toBinaryString(Integer.MIN_VALUE), Integer.toBinaryString(Integer.MIN_VALUE).length()));
//        System.out.println(-0b1111);

//        System.out.println('1');
//        System.out.println(1 + '1');
//        System.out.println(1 + '1' - 1);
//        System.out.println(1 + "1");
////        System.out.println(1 + "1" - 1); //don't compile

//        // Print out a number using the localized number, integer, currency,
//        // and percent format for each locale</strong>
//        Locale[] locales = NumberFormat.getAvailableLocales();
//        double myNumber = -1234.56;
//        NumberFormat form;
//        for (int j = 0; j < 4; ++j) {
//            System.out.println("FORMAT");
//            for (int i = 0; i < locales.length; ++i) {
//                if (locales[i].getCountry().length() == 0) {
//                    continue; // Skip language-only locales
//                }
//                System.out.print(locales[i].getDisplayName());
//                switch (j) {
//                    case 0:
//                        form = NumberFormat.getInstance(locales[i]);
//                        break;
//                    case 1:
//                        form = NumberFormat.getIntegerInstance(locales[i]);
//                        break;
//                    case 2:
//                        form = NumberFormat.getCurrencyInstance(locales[i]);
//                        break;
//                    default:
//                        form = NumberFormat.getPercentInstance(locales[i]);
//                        break;
//                }
//                if (form instanceof DecimalFormat) {
//                    System.out.print(": " + ((DecimalFormat) form).toPattern());
//                }
//                System.out.print(" -> " + form.format(myNumber));
//                try {
//                    System.out.println(" -> " + form.parse(form.format(myNumber)));
//                } catch (ParseException e) {
//                }
//            }
//        }

        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator(' ');
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.##", decimalFormatSymbols);
        double f = 0.56F;
        double addend = 0.0F;
        for (int i = 0; i < 10; i++) {
            System.out.println(f);
            System.out.println(addend);
            String format = decimalFormat.format(f + addend);
            addend += Math.pow(10, i);
            System.out.println(format);
            System.out.println();
        }
    }

    private static void roundingModeTest(RoundingMode roundingMode) {
        System.out.println(roundingMode);
        for (float f = 1.0F; f < 1.25F; f += 0.001F) {
            String stringF = Float.toString(f);
            BigDecimal bd = new BigDecimal(stringF);
            bd = bd.setScale(2, roundingMode);
            System.out.println(String.format("%s -> %f", stringF, bd.floatValue()));
        }
    }

    private static void printDecimal(double number) {
        System.out.println("double = " + number);
    }

    private static void printDecimal(float number) {
        System.out.println("float = " + number);
    }

    public static float round2Decimal(float number) {
        return Math.round(number * 100.f) / 100.f;
    }
}
