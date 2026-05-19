package util;

import model.*;
import java.util.Scanner;

/**
 * Обработчик пользовательского ввода с проверкой корректности.
 */
public class InputHandler {
    private Scanner scanner;
    private boolean interactive;

    public InputHandler(Scanner scanner, boolean interactive) {
        this.scanner = scanner;
        this.interactive = interactive;
    }

    /**
     * Читает все поля организации с консоли.
     * @return новая организация
     */
    public Organization readOrganization() {
        String name = readNonEmpty("Название организации");
        Coordinates coords = readCoordinates();
        float turnover = readPositiveFloat("Годовой оборот (annualTurnover)");
        OrganizationType type = readOrganizationType();
        Address address = readAddress();
        return new Organization(name, coords, turnover, type, address);
    }

    private Coordinates readCoordinates() {
        System.out.println("Ввод координат:");
        long x = readLong("x");
        Long y = readNotNullLong("y");
        return new Coordinates(x, y);
    }

    private Address readAddress() {
        System.out.println("Ввод адреса:");
        String zipCode = readNonEmpty("zipCode");
        Location town = readLocation();
        return new Address(zipCode, town);
    }

    private Location readLocation() {
        System.out.println("Ввод локации:");
        Integer x = readNotNullInt("x");
        float y = readFloat("y");
        String name = readNonEmpty("name");
        return new Location(x, y, name);
    }

    private OrganizationType readOrganizationType() {
        System.out.print("Типы: ");
        for (OrganizationType t : OrganizationType.values()) {
            System.out.print(t.name() + " ");
        }
        System.out.println();
        while (true) {
            System.out.print("Введите тип (или пусто для null): ");
            String line = readLineSafe();
            if (line.isEmpty()) return null;
            try {
                return OrganizationType.valueOf(line.toUpperCase());
            } catch (IllegalArgumentException e) {
                if (!interactive) throw new ScriptException("Неверный тип: " + line);
                System.out.println("Неверный тип, попробуйте снова");
            }
        }
    }

    private String readNonEmpty(String fieldName) {
        while (true) {
            System.out.print("Введите " + fieldName + ": ");
            String line = readLineSafe();
            if (!line.isEmpty()) return line;
            if (!interactive) throw new ScriptException("Пустое значение поля " + fieldName);
            System.out.println("Поле не может быть пустым");
        }
    }

    private long readLong(String fieldName) {
        while (true) {
            System.out.print("Введите " + fieldName + ": ");
            String line = readLineSafe();
            try {
                java.math.BigInteger num = new java.math.BigInteger(line);
                if (num.compareTo(java.math.BigInteger.valueOf(Long.MAX_VALUE)) > 0 ||
                        num.compareTo(java.math.BigInteger.valueOf(Long.MIN_VALUE)) < 0) {
                    if (!interactive) throw new ScriptException("Число слишком большое в поле " + fieldName);
                    System.out.println("Число слишком большое для типа long");
                    continue;
                }
                return num.longValue();
            } catch (NumberFormatException e) {
                if (!interactive) throw new ScriptException("Не число в поле " + fieldName);
                System.out.println("Нужно целое число");
            }
        }
    }

    private Long readNotNullLong(String fieldName) {
        while (true) {
            System.out.print("Введите " + fieldName + ": ");
            String line = readLineSafe();
            if (line.isEmpty()) {
                if (!interactive) throw new ScriptException("Пустое значение поля " + fieldName);
                System.out.println("Поле не может быть null");
                continue;
            }
            try {
                java.math.BigInteger num = new java.math.BigInteger(line);
                if (num.compareTo(java.math.BigInteger.valueOf(Long.MAX_VALUE)) > 0 ||
                        num.compareTo(java.math.BigInteger.valueOf(Long.MIN_VALUE)) < 0) {
                    if (!interactive) throw new ScriptException("Число слишком большое в поле " + fieldName);
                    System.out.println("Число слишком большое для типа long");
                    continue;
                }
                return num.longValue();
            } catch (NumberFormatException e) {
                if (!interactive) throw new ScriptException("Не число в поле " + fieldName);
                System.out.println("Нужно целое число");
            }
        }
    }

    private Integer readNotNullInt(String fieldName) {
        while (true) {
            System.out.print("Введите " + fieldName + ": ");
            String line = readLineSafe();
            if (line.isEmpty()) {
                if (!interactive) throw new ScriptException("Пустое значение поля " + fieldName);
                System.out.println("Поле не может быть null");
                continue;
            }
            try {
                java.math.BigInteger num = new java.math.BigInteger(line);
                if (num.compareTo(java.math.BigInteger.valueOf(Integer.MAX_VALUE)) > 0 ||
                        num.compareTo(java.math.BigInteger.valueOf(Integer.MIN_VALUE)) < 0) {
                    if (!interactive) throw new ScriptException("Число слишком большое в поле " + fieldName);
                    System.out.println("Число слишком большое для типа int");
                    continue;
                }
                return num.intValue();
            } catch (NumberFormatException e) {
                if (!interactive) throw new ScriptException("Не число в поле " + fieldName);
                System.out.println("Нужно целое число");
            }
        }
    }

    private float readFloat(String fieldName) {
        while (true) {
            System.out.print("Введите " + fieldName + ": ");
            try {
                String line = readLineSafe().replace(',', '.');
                float val = Float.parseFloat(line);
                if (Float.isInfinite(val)) {
                    if (!interactive) throw new ScriptException("Число слишком большое в поле " + fieldName);
                    System.out.println("Число слишком большое для типа float");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                if (!interactive) throw new ScriptException("Не число в поле " + fieldName);
                System.out.println("Нужно число");
            }
        }
    }

    private float readPositiveFloat(String fieldName) {
        while (true) {
            System.out.print("Введите " + fieldName + " (>0): ");
            try {
                String line = readLineSafe().replace(',', '.');
                float val = Float.parseFloat(line);
                if (Float.isInfinite(val)) {
                    if (!interactive) throw new ScriptException("Число слишком большое в поле " + fieldName);
                    System.out.println("Число слишком большое для типа float");
                    continue;
                }
                if (val > 0) return val;
                if (!interactive) throw new ScriptException("Значение должно быть больше 0 в поле " + fieldName);
                System.out.println("Значение должно быть больше 0");
            } catch (NumberFormatException e) {
                if (!interactive) throw new ScriptException("Не число в поле " + fieldName);
                System.out.println("Нужно число");
            }
        }
    }

    private String readLineSafe() {
        if (!scanner.hasNextLine()) {
            throw new ScriptException("Недостаточно данных в скрипте");
        }
        String line = scanner.nextLine().trim();
        System.out.println(line);
        return line;
    }
}