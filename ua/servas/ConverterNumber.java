package ua.servas;

import java.util.Map;

/*
 Класс: конвертер чисел.
 Статический член: набор сопоставлений для конвертации.
*/
class ConverterNumber {

    private static final MatchingNumbers mappingsForConversion = MatchingNumbers.getMatchings();

    /*
    Метод getArabicStrFromRomanStr
    Параметр: число из набора римских цифр (строка).
    Возврат: число из набора арабских цифр (строка).
    */
    protected static String getArabicStrFromRomanStr(String numberRoman) {
        // из набора сопоставлений получаем сопоставления для конвертации римских в арабские
        Map mappingRomanArabic = mappingsForConversion.getMatchingRomanArabic();
        char[] charArray = numberRoman.toCharArray();
        int result = 0;
        // конвертация по сопосталениям и правилу римского исчисления
        for (int i = 0; i < charArray.length; i++) {
            int curValue = (int)mappingRomanArabic.get(charArray[i]);
            if (i < charArray.length - 1) {
                int nextValue = (int)mappingRomanArabic.get(charArray[i + 1]);
                if (curValue < nextValue) {
                    result -= curValue;
                } else {
                    result += curValue;
                }
            } else {
                result += curValue;
            }
        }
        return Integer.toString(result);
    }

    /*
    Метод getRomanStrFromArabicStr
    Параметр: число из набора арабских цифр (строка).
    Возврат: число из набора римских цифр (строка).
    */
    protected static String getRomanStrFromArabicStr(String numberArabic) {
        // из набора сопоставлений получаем сопоставления для конвертации арабских в римские
        Map mappingArabicRoman = mappingsForConversion.getMatchingArabicRoman();
        return ""; // заглушка
    }
}
