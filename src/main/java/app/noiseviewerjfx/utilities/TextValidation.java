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

    public static StringConverter<Integer> newIntegerPercentageConverter() {
        return new StringConverter<>() {
            @Override
            public String toString(Integer integer) {
                return integer.toString() + "%";
            }

            @Override
            public Integer fromString(String valueString) {
                String valueWithoutUnits = valueString.replaceAll("%", "").trim();

                if (valueWithoutUnits.isEmpty()) return 0;

                return Integer.parseInt(valueWithoutUnits);
            }
        };
    }

    public static TextFormatter<String> newIntegerPercentageFormatter() {
        return new TextFormatter<String>(change -> {

            if (!change.isContentChange()) return change;

            String text = change.getText().replaceAll("%", "").trim();

            if (!TextValidation.isValidIntString(text)) return null;

            return change;
        });
    }

    public static TextFormatter<String> newIntegerFormatter() {
        return new TextFormatter<>(change -> {

            if (!change.isContentChange()) return change;

            String text = change.getText();

            if (!TextValidation.isValidIntString(text)) return null;

            return change;
        });
    }
}
