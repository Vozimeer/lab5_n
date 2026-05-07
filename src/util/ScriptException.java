package util;

/**
 * Исключение, выбрасываемое при ошибке в данных скрипта.
 * Прерывает выполнение текущего скрипта.
 */
public class ScriptException extends RuntimeException {
    /**
     * @param message описание ошибки
     */
    public ScriptException(String message) {
        super(message);
    }
}