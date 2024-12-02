package com.example.teamcity.api.generators;

import org.apache.commons.lang3.RandomStringUtils;
import java.util.Random;

public final class RandomData {
    private static final String TEST_PREFIX = "test_";
    private static final String TEST_POSTFIX = "_test";
    private static final int LENGTH = 10;
    private static final String SPECIAL_CHARACTERS = " @!#$%^&*()-_=+[]{};:'\",.<>?/\\|";
    private static final Random random = new Random();
    private static final String ALPHANUMERIC_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String ALPHABETS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String CYRILLIC_ALPHABETS = "абвгдезийклмнопрстуфхцчшщъыьэюяАБВГДЕИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

    public static String getString() {
        return TEST_PREFIX + RandomStringUtils.randomAlphabetic(LENGTH);
    }

    public static String getStringWithCyrillic() {
        int remainingLength = LENGTH - TEST_PREFIX.length();
        StringBuilder stringBuilder = new StringBuilder(TEST_PREFIX);

        for (int i = 0; i < remainingLength; i++) {
            int randomIndex = random.nextInt(CYRILLIC_ALPHABETS.length());
            stringBuilder.append(CYRILLIC_ALPHABETS.charAt(randomIndex));
        }

        return stringBuilder.toString();
    }

    public static String getStringWith_() {
        return "_" + TEST_PREFIX + RandomStringUtils.randomAlphabetic(LENGTH);
    }

    public static String getStringWithNumber() {
        String randomDigit = RandomStringUtils.randomNumeric(1);
        String randomAlphabets = RandomStringUtils.randomAlphabetic(LENGTH);
        return randomDigit + TEST_PREFIX + randomAlphabets;
    }

    public static String getStringWithRandomRepeatingSymbols() {
        // Генерируем случайный символ
        char repeatingChar = getRandomAlphabeticCharacter(); // Можно использовать другой метод для генерации, если нужно

        // Создаем строку, содержащую повторяющийся символ
        String repeatingPart = String.valueOf(repeatingChar).repeat(Math.max(0, LENGTH));
        return repeatingPart + TEST_POSTFIX;
    }

    public static String generateStringWithLatinDigits() {
        // Учитываем длину префикса
        int remainingLength = LENGTH - TEST_PREFIX.length();
        StringBuilder stringBuilder = new StringBuilder(TEST_PREFIX);

        // Генерируем случайные символы до достижения желаемой длины
        for (int i = 0; i < remainingLength; i++) {
            int randomIndex = random.nextInt(ALPHANUMERIC_CHARACTERS.length());
            stringBuilder.append(ALPHANUMERIC_CHARACTERS.charAt(randomIndex));
        }

        return stringBuilder.toString();
    }

    public static String getStringWithRandomSpecialCharacter() {
        StringBuilder stringBuilder = new StringBuilder(LENGTH);
        boolean hasSpecialChar = false;

        // Генерируем первую букву (латиница)
        stringBuilder.append(getRandomAlphabeticCharacter());

        // Генерируем случайный индекс для специального символа
        int specialCharIndex = random.nextInt(LENGTH - 1) + 1; // +1, чтобы не вставить на первую позицию

        // Заполняем строку обычными символами
        for (int i = 1; i < LENGTH; i++) {
            if (i == specialCharIndex) {
                // Вставляем случайный специальный символ
                stringBuilder.append(getRandomInvalidSpecialCharacter()); // Изменили на метод, генерирующий недопустимые символы
                hasSpecialChar = true;
            } else {
                // Вставляем обычный алфавитный символ
                stringBuilder.append(getRandomAlphabeticCharacter());
            }
        }

        // Если не был вставлен специальный символ, добавляем его в случайное место
        if (!hasSpecialChar) {
            stringBuilder.setCharAt(random.nextInt(LENGTH - 1) + 1, getRandomInvalidSpecialCharacter()); // Изменили на метод, генерирующий недопустимые символы
        }

        return stringBuilder.toString();
    }

    // Пример метода для генерации недопустимых специальных символов
    private static char getRandomInvalidSpecialCharacter() {
        // Список символов, которые явно не должны быть в id
        char[] invalidSpecialChars = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '+', '=', '{', '}', '[', ']', ':', ';', '"', '\'', '<', '>', ',', '.', '?'};
        return invalidSpecialChars[random.nextInt(invalidSpecialChars.length)];
    }


    private static char getRandomSpecialCharacter() {
        int randomIndex = random.nextInt(SPECIAL_CHARACTERS.length()); // Генерируем случайный индекс
        return SPECIAL_CHARACTERS.charAt(randomIndex); // Возвращаем символ по этому индексу
    }

    private static char getRandomAlphabeticCharacter() {
        // Генерируем случайное значение (0 или 1) для выбора строчной или заглавной буквы
        boolean isUpperCase = random.nextBoolean();
        char base = isUpperCase ? 'A' : 'a'; // Базовая буква зависит от того, строчная это или заглавная

        // Генерируем случайный алфавитный символ (a-z, A-Z)
        return (char) (base + random.nextInt(26)); // Добавляем случайное значение от 0 до 25
    }


    public static String getString(int length) {
        return TEST_PREFIX + RandomStringUtils
                .randomAlphabetic(Math.max(LENGTH, length - TEST_PREFIX.length()));
    }

    public static String getRandomLetter() {
        int randomIndex = random.nextInt(ALPHABETS.length());
        return String.valueOf(ALPHABETS.charAt(randomIndex));
    }
}
