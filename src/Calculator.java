import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Calculator {
    static final String ALLOWED_ARABIC_NUMBERS = "1234567890"; // Обозначаем доступные для обработки символы
    static final String ALLOWED_ROMAN_NUMBERS = "IVX";
    static final String ALLOWED_OPERATIONS = "+-*/";


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) { // Ввод данных пользователя
            try {
                System.out.println("Введите арифметическое выражение:");
                String inputData = scanner.nextLine().toUpperCase();
                String result = calc(inputData);
                System.out.println(inputData + " = " + result);
            } catch (Exception e) { // Обработка возможных исключений
                System.out.println(e.toString());
            }
        }
    }


    public static String calc(String input) throws Exception {
        String[] data = input.split(" ");
        if (data.length != 3) throw new Exception("Недопустимое количество слагаемых/операндов");

        String firstNumber = data[0]; // Разбиение строки на отдельные числа и операцию
        String operation = data[1];
        String secondNumber = data[2];
        boolean fl = false;

        if (isArabic(firstNumber) && isRoman(secondNumber) || isArabic(secondNumber) && isRoman(firstNumber))
            throw new Exception("Числа разных систем счисления");
        if (!ALLOWED_OPERATIONS.contains(String.valueOf(operation))) throw new Exception("Недопустимая операция");
        if (isRoman(firstNumber)) fl = true;

        int firstNumberInt;
        int secondNumberInt;
        int result = 0;

        if (isArabic(firstNumber)) { // Проверяем, в какой системе будем работать: арабская или римская
            firstNumberInt = Integer.parseInt(firstNumber);
            secondNumberInt = Integer.parseInt(secondNumber);
        } else if (isRoman((firstNumber))) {
            firstNumberInt = Integer.parseInt(romanToArabic(firstNumber));
            secondNumberInt = Integer.parseInt(romanToArabic(secondNumber));
        } else throw new Exception("Недопустимые символы");

        if (firstNumberInt < 1 || firstNumberInt > 10 || secondNumberInt < 1 || secondNumberInt > 10)
            throw new Exception("Ошибка диапазона");


        switch (operation) { // В зависимости от операции, выполняем действие над числами
            case "+": {
                result = firstNumberInt + secondNumberInt;
                break;
            }
            case "-": {
                result = firstNumberInt - secondNumberInt;
                break;
            }
            case "*": {
                result = firstNumberInt * secondNumberInt;
                break;
            }
            case "/": {
                result = firstNumberInt / secondNumberInt;
                break;
            }
        }
        if (fl) {
            if (result <= 0) throw new Exception("Для римских чисел недопустимы отрицательные значения");
            return arabicToRoman(result);
        }
        return Integer.toString(result);
    }


    public static String romanToArabic(String romanNumber) {       // Функция для перевода римских чисел в арабские
        final Map<Character, Integer> romanToArabicMap = new HashMap<>();
        romanToArabicMap.put('I', 1);
        romanToArabicMap.put('V', 5);
        romanToArabicMap.put('X', 10);
        romanToArabicMap.put('L', 50);
        romanToArabicMap.put('C', 100);
        romanToArabicMap.put('D', 500);
        romanToArabicMap.put('M', 1000);

        int result = 0;
        int prev = 0;
        for (int i = romanNumber.length() - 1; i >= 0; i--) {
            int current = romanToArabicMap.get(romanNumber.charAt(i));
            if (current < prev) {
                result -= current;
            } else {
                result += current;
            }
            prev = current;
        }

        return Integer.toString(result);
    }


    public static String arabicToRoman(int arabicNumber) { // Функция для перевода арабских чисел в римские
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return thousands[arabicNumber / 1000] +
                hundreds[(arabicNumber % 1000) / 100] +
                tens[(arabicNumber % 100) / 10] +
                ones[arabicNumber % 10];
    }


    public static boolean isRoman(String number) { // Проверка на запись числа в римской СС
        if (number == null || number.isEmpty())
            return false;

        for (int i = 0; i < number.length(); i++) {
            if (!ALLOWED_ROMAN_NUMBERS.contains(String.valueOf(number.charAt(i))))
                return false;
        }
        return true;
    }


    public static boolean isArabic(String number) { // Проверка на запись числа в арабской СС
        if (number == null || number.isEmpty())
            return false;

        for (int i = 0; i < number.length(); i++) {
            if (!ALLOWED_ARABIC_NUMBERS.contains(String.valueOf(number.charAt(i))))
                return false;
        }
        return true;
    }
}