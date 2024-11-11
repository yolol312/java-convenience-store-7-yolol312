package store.constant;

public class ErrorMessage {
    public static final String ERROR_PREFIX = "[ERROR] ";

    public static final String INVALID_INPUT_BLANK_ERROR = ERROR_PREFIX + "입력은 공백이 아닌 숫자여야 합니다.";
    public static final String INVALID_INPUT_WITH_BLANK_ERROR = ERROR_PREFIX + "입력은 공백이 포함되지 않은 입력이어야 합니다.";
    public static final String INVALID_INPUT_ANSWER_ERROR = ERROR_PREFIX + "답변은 Y 혹은 N 이어야 합니다.";
}
