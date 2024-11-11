package store.view;

import store.constant.ErrorMessage;

public class InputValidator {
    private final String YES_NO_REGEX = "^[YN]$";

    public void validateBlank(String input) {
        if (isBlank(input)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_BLANK_ERROR);
        }
    }

    public void validateWhitespace(String input) {
        if (hasWhitespace(input)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_WITH_BLANK_ERROR);
        }
    }

    public void validateYesOrNo(String input) {
        if (isYesOrNo(input)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_NUMBER_CHARACTER_ERROR);
        }
    }

    private boolean isBlank(String input) {
        return input == null || input.isBlank();
    }

    private boolean hasWhitespace(String input) {
        return input.contains(" ");
    }

    private boolean isYesOrNo(String input) {
        return (input.matches(YES_NO_REGEX));
    }
}
