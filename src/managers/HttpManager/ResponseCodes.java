package managers.HttpManager;

public enum ResponseCodes {
    OK(200),//ХОРОШО
    CREATED(201),//создано
    NO_CONTENT(204),//нет содержимого
    BAD_REQUEST(400),//неправильный, некорректный запрос
    NOT_FOUND(404),//не найдено
    METHOD_NOT_ALLOWED(405);//метод не поддерживается

    private int code;

    private ResponseCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
