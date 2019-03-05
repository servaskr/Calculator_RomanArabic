package ua.servas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 Приложение 'Calculator_RomanArabic'.
 Программа калькулятор: вычисление выражения.
 Допустимый тип цифр: римские, арабские.
 Операнды: '+'(сложение), '-'(вычитание), '*'(умножение), '/'(деление).
*/

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(System.in));
        String expression; // выражение для вычисления
        while (true) {
            System.out.print("Please, enter Your expression (or word 'bye'): ");
            // сразу подчистим непечатаемые символы и пробелы
            expression = buffReader.readLine().replaceAll("\\s+","");
            if (expression.equals("bye")) {
                // выход из приложения
                System.out.println("Bye-bye!");
                break;
            }
            // определим тип цифр (римские, арабские или неопределенные)
            Enum curTypeNumbers = getTypeNumbers(expression);
            if (curTypeNumbers == TypeOfNumbers.UNKNOWN) {
                System.out.println("Sorry, I could not determine the type of numbers!");
            } else {
                // сообщаем о типе цифр и пытаемся получить результат
                System.out.print("Type of the numbers: " + curTypeNumbers.toString() + ". ");
                System.out.println("RESULT: " + makeCalculations(expression, curTypeNumbers));
            }
            System.out.println();
        }
    }

    /*
     Метод getTypeNumbers
     Параметры: выражение-строка.
     Возврат: тип (из перечисления) цифр выражения.
    */
    private static Enum getTypeNumbers(String expressionToTest) {
        Map<Enum, String> hashMapTypesPatterns = new HashMap<>();
        // коллекция типов (ключи) и соответствующих им регулярных выражений (значения)
        hashMapTypesPatterns.put(TypeOfNumbers.ARABIC,"^[0-9+-/\\*]+");
        hashMapTypesPatterns.put(TypeOfNumbers.ROMAN,"^[IVXLCDM+-/\\*]+");
        Set<Map.Entry<Enum, String>> typesPatterns = hashMapTypesPatterns.entrySet();
        for (Map.Entry<Enum,String> typePattern : typesPatterns) {
            Pattern patternForExpression = Pattern.compile(typePattern.getValue());
            Matcher matcher = patternForExpression.matcher(expressionToTest);
            // получаем тип цифр
            if (matcher.matches()) {
                return typePattern.getKey();
            }
        }
        // неопределенный тип цифр
        return TypeOfNumbers.UNKNOWN;
    }

    /*
    Метод: makeCalculations
    Параметры: вычмсляемое выражение (строка) и тип цифр.
    Возврат: результат вычисления выражения или сообщение о некорректном выражении (строка).
    */
    private static String makeCalculations(String expressionForCalculation, Enum typeNumbers) {
        String[][] arrayNumbers = getArrayNumbers(expressionForCalculation);
        if (arrayNumbers.length == 0) {
            return "please, check the expression.";
        }
        return Calculations.getResult(arrayNumbers, typeNumbers);
    }

    /*
    Метод: makeCalculations
    Параметры: выражение (строка).
    Возврат: двумерный массив с числами и операндами (результат распарсивания выражения).
    Примечание: в том числе проверяется корректность расстановки операндов.
    */
    private  static String[][] getArrayNumbers(String expression) {
        Pattern patternForSizeArray = Pattern.compile("[+-/\\*]");
        Matcher matcher = patternForSizeArray.matcher(expression);
        ArrayList<Integer> operandNumbers = new ArrayList<>();
        int prevNumOperand = -1;
        while (matcher.find()) {
            int curNumOperand = matcher.start();
            // проверка на рядом стоящие операнды и операнд вначале выражения
            if ((curNumOperand == 0) | (prevNumOperand != -1 && curNumOperand == prevNumOperand + 1)) {
                return new String[0][0];
            }
            operandNumbers.add(curNumOperand);
            prevNumOperand = curNumOperand;
        }
        // проверка на отсутствие операндов и операнд вконце выражения
        if ((prevNumOperand == expression.length() - 1) | (operandNumbers.size() == 0)) {
            return new String[0][0];
        }
        String[][] arrayNumbers = new String[operandNumbers.size() + 1][2];
        int numLine = 0;
        int startIndex = 0;
        for (int endIndex : operandNumbers) {
            arrayNumbers[numLine][0] = expression.substring(startIndex, endIndex);
            arrayNumbers[numLine][1] = expression.substring(endIndex, endIndex + 1);
            numLine++;
            startIndex = endIndex + 1;
        }
        arrayNumbers[numLine][0] = expression.substring(startIndex);
        return arrayNumbers;
    }
}
