package ua.servas;

/*
 Класс вычмсления (арифметических действий).
 Члены:
  - рабочий массив с числами и операндами;
  - результат вычислений (строка);
  - текущий обрабатываемый размер массива (чмсло).
*/
class Calculations {

    private static String[][] workArray;
    private static String result = null;
    private static int curSizeArray;

    /*
    Метод convertRomanToArabic
    Параметры: нет.
    Возврат: нет.
    Примечание: получение рабочего массива после конвертации чисел.
    */
    private static void convertRomanToArabic() {
        for (int i = 0; i < workArray.length; i++) {
            workArray[i][0] = ConverterNumber.getArabicStrFromRomanStr(workArray[i][0]);
        }
    }

    /*
    Метод: operationsDivisionMultiplication
    Параметр: операнд.
    Возврат: нет.
    Примечание: операции деления и умножения (определяются входным значением операнда).
    (пока делаю раздельно деление и умножение, а вообще планировал переписать арифметику через абстрактный класс!)
    */
    private static void operationsDivisionMultiplication(String operand) {
        for (int i = curSizeArray - 2; i >= 0; i--) {
            if (workArray[i][1].equals(operand)) {
                double num1, num2;
                try {
                    num1 = Double.parseDouble(workArray[i][0]);
                    num2 = Double.parseDouble(workArray[i + 1][0]);
                } catch (NumberFormatException e) {
                    curSizeArray = 1;
                    result = "please, check the expression.";
                    return;
                }
                // нельзя делить на ноль
                if (operand.equals("/") && num2 == 0) {
                    curSizeArray = 1;
                    result = "please, check the expression.";
                    return;
                }
                double res = 0.0;
                if (operand.equals("/")) {
                    res = num1 / num2;
                } else if (operand.equals("*")) {
                    res = num1 * num2;
                }
                result = Double.toString(res);
                workArray[i][0] = result;
                workArray[i][1] = workArray[i + 1][1];
                curSizeArray--;
                for (int j = i + 1; j < curSizeArray; j++) {
                    workArray[j][0] = workArray[j + 1][0];
                    workArray[j][1] = workArray[j + 1][1];
                }
            }
        }
    }

    /*
    Метод: operationsAdditionSubtraction
    Параметр: операнд.
    Возврат: нет.
    Примечание: действия сложения и вычитания в порядке следования.
    (а вообще планировал переписать арифметику через абстрактный класс!)
    */
    private static void operationsAdditionSubtraction() {
        for (int i = 0; i < curSizeArray - 1; i++) {
            double num1, num2;
            try {
                num1 = Double.parseDouble(workArray[i][0]);
                num2 = Double.parseDouble(workArray[i + 1][0]);
            } catch (NumberFormatException e) {
                result = "please, check the expression.";
                return;
            }
            double res = 0.0;
            if (workArray[i][1].equals("-")) {
                res = num1 - num2;
            }
            if (workArray[i][1].equals("+")) {
                res = num1 + num2;
            }
            result = Double.toString(res);
            workArray[i + 1][0] = result;
        }
    }

    /*
    Метод: getResult
    Параметры: входящий массив чисел и операндов, тип цифр.
    Возврат: результат вычислений или сообщение о некоррктном выражении.
    Примеание: в том числе управляет последовательностью арифметических операций.
    */
    protected static String getResult(String[][] inArrayNumbers, Enum typeNumbers) {
        workArray = inArrayNumbers; // рабочий массив чисел
        if (typeNumbers == TypeOfNumbers.ROMAN) {
            // нужно конвертировать числа
            convertRomanToArabic();
        }
        // текущий/рабочий размер массива
        curSizeArray = workArray.length;
        // сначала деление
        operationsDivisionMultiplication("/");
        if (curSizeArray == 1) {
            return result;
        }
        // теперь умножение
        operationsDivisionMultiplication("*");
        if (curSizeArray == 1) {
            return result;
        }
        // теперь сложение и вычитание
        operationsAdditionSubtraction();
        return result;
    }
}
