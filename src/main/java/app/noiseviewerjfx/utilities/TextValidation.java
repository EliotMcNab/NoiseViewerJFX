package app.noiseviewerjfx.utilities;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public class TextValidation {

    /**
     * Determines whether a string can be considered as an integer
     * @param text (String): the string being tested
     * @return (boolean): whether the string can be parsed to an integer
     */
    public static boolean isValidIntString(String text) {

        // if a number has been deleted (backspace)
        if (text.length() == 0) return true;

        // checks to see if the string can be parsed into an int
        try {
            // parsing successful
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            // string could not be parsed
            return false;
        }

    }

    public static boolean isValidDoubleString(String text) {
        if (text.length() == 0 || text.equals(".")) return true;

        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static StringConverter<Integer> newIntegerPercentageConverter() {
        return new StringConverter<>() {
            @Override
            public String toString(Integer integer) {
                return integer + "%";
            }

            @Override
            public Integer fromString(String valueString) {
                String valueWithoutUnits = removeUnit(valueString, "%");

                if (valueWithoutUnits.isEmpty() || !isValidIntString(valueWithoutUnits)) return 0;

                return Integer.parseInt(valueWithoutUnits);
            }
        };
    }

    public static TextFormatter<String> newIntegerPercentageFormatter() {
        return new TextFormatter<>(change -> {

            if (!change.isContentChange()) return change;

            String valueWithoutUnits = removeUnit(change.getText(), "%");

            if (!isValidIntString(valueWithoutUnits)) return null;

            return change;
        });
    }

    public static TextFormatter<String> newIntegerFormatter() {
        return new TextFormatter<>(change -> {

            if (!change.isContentChange()) return change;

            String text = change.getText();

            if (!isValidIntString(text)) return null;

            return change;
        });
    }

    public static StringConverter<Double> newDoubleUnitConverter(String unit) {
        return new StringConverter<>() {
            @Override
            public String toString(Double aDouble) {
                return aDouble + unit;
            }

            @Override
            public Double fromString(String valueString) {
                String valueWithoutUnits = removeUnit(valueString, unit);

                if (valueWithoutUnits.isEmpty() || !isValidDoubleString(valueWithoutUnits)) return (double) 0;

                return Double.parseDouble(valueWithoutUnits);
            }
        };
    }

    public static TextFormatter<String> newDoubleUnitFormatter(String unit) {
        return new TextFormatter<>(change -> {

            if (!change.isContentChange()) return change;

            String valueWithoutUnits = removeUnit(change.getText(), unit);

            if (!isValidDoubleString(valueWithoutUnits)) return null;

            return change;
        });
    }

    public static StringConverter<Double> newDoubleConverter(int precision) {
        return new StringConverter<>() {
            @Override
            public String toString(Double aDouble) {
                return Double.toString(ComplementaryMath.roundToPrecision(aDouble, precision));
            }

            @Override
            public Double fromString(String valueString) {

                if (!isValidDoubleString(valueString)) return (double) 0;

                return Double.parseDouble(valueString);
            }
        };
    }

    public static TextFormatter<String> newDoubleFormatter() {
        return new TextFormatter<>(change -> {
            if (!change.isContentChange()) return change;

            String valueString = change.getText();

            if (!isValidDoubleString(valueString)) return null;

            return change;
        });
    }

    private static String removeUnit(String valueString, String unit) {
        return valueString.replaceFirst(unit, "").trim();
    }
}
