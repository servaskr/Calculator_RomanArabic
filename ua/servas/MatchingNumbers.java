package ua.servas;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 Класс набора сопоставлений для конвертации.
 Разрешает создать только один зкземляр.
 Генерирует сопоставления.
*/
class MatchingNumbers {

    private static MatchingNumbers objMatchingNumbers = null;
    private Map<Character, Integer> matchingRomanArabic;
    private Map<Integer, Character> matchingArabicRoman;

    private MatchingNumbers() {
        matchingRomanArabic = new HashMap<>();
        matchingRomanArabic.put('I', 1);
        matchingRomanArabic.put('V', 5);
        matchingRomanArabic.put('X', 10);
        matchingRomanArabic.put('L', 50);
        matchingRomanArabic.put('C', 100);
        matchingRomanArabic.put('D', 500);
        matchingRomanArabic.put('M', 1000);
        matchingArabicRoman = new HashMap<>();
        Set<Map.Entry<Character, Integer>> matchingSet = matchingRomanArabic.entrySet();
        for (Map.Entry<Character, Integer> matching : matchingSet) {
            matchingArabicRoman.put(matching.getValue(), matching.getKey());
        }
        objMatchingNumbers = this;
    }

    public static MatchingNumbers getMatchings() {
        if (objMatchingNumbers == null) {
            new MatchingNumbers();
        }
        return objMatchingNumbers;
    }

    protected Map getMatchingRomanArabic() {
        return matchingRomanArabic;
    }

    protected Map getMatchingArabicRoman() {
        return matchingArabicRoman;
    }
}
