package com.company;

import java.lang.invoke.StringConcatFactory;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String result = calc(input);
        System.out.println(result);
    }

    public static String calc(String input) throws Exception {
        String strA = createSubstringA(input);
        String strB = createSubstringB(input);
        String operandA = createFirstOperand(strA);
        String operandB = createSecondOperand(strB);
        char symbol = strB.charAt(0);
        String result = createResult(operandA, operandB, symbol);
        return result;
    }

    public static String createSubstringA(String input) throws Exception {
        int minusCounter = 0;
        boolean checkOperator = false;
        boolean checkRoman = false;
        StringBuilder strA = new StringBuilder();
        String[] romanNumbers = indexingRomanNumbersEnum();
        if(input.isEmpty()) {
            throw new Exception("Your string is empty");
        }
        for(int i = 0; i < input.length(); i++) {
            if(String.valueOf(input.charAt(i)).equals(".") || String.valueOf(input.charAt(i)).equals(",")) {
                throw new Exception("Wrong input data. Maybe you can use float operands, but integer only.");
            }
        }
        for(int i = 0; i < input.length(); i++) {
            if(!String.valueOf(input.charAt(i)).isBlank() && !isDigit(String.valueOf(input.charAt(i)))
                    && !String.valueOf(input.charAt(i)).equals("+")
                    && !String.valueOf(input.charAt(i)).equals("-")
                    && !String.valueOf(input.charAt(i)).equals("*")
                    && !String.valueOf(input.charAt(i)).equals("/")) {
                for (String romanNumber : romanNumbers) {
                    if (String.valueOf(input.charAt(i)).equals(romanNumber)) {
                        checkRoman = true;
                        break;
                    }
                }
                if(!checkRoman) {
                    throw new Exception("Wrong input format");
                }
                checkRoman = false;
            }
        }
        if(isDigit(String.valueOf(input.charAt(0)))) {
            for(int i = 0; i < input.length(); i++) {
                if(String.valueOf(input.charAt(i)).equals("+") || String.valueOf(input.charAt(i)).equals("*")
                        || String.valueOf(input.charAt(i)).equals("/") || String.valueOf(input.charAt(i)).equals("-")) {
                    checkOperator = true;
                    break;
                }
            }
            if(checkOperator) {
                boolean checkSpace = false;
                for(int i = 0; i < input.length(); i++) {
                    if(String.valueOf(input.charAt(i)).isBlank()) {
                        checkSpace = true;
                    }
                    if(String.valueOf(input.charAt(i)).equals("+")
                            || String.valueOf(input.charAt(i)).equals("-")
                            || String.valueOf(input.charAt(i)).equals("*")
                            || String.valueOf(input.charAt(i)).equals("/")) {
                        return strA.toString();
                    }
                    if(isDigit(String.valueOf(input.charAt(i))) && checkSpace) {
                        throw new Exception("Wrong format of operands (Ex. a + b)");
                    }
                    if(!String.valueOf(input.charAt(i)).isBlank() && !isDigit(String.valueOf(input.charAt(i)))) {
                        for (String romanNumber : romanNumbers) {
                            if (String.valueOf(input.charAt(i)).equals(romanNumber)) {
                                throw new Exception("Calc cannot work with arabic and roman numbers together");
                            }
                        }
                    }

                    strA.append(input.charAt(i));
                }
            }
            else {
                throw new Exception("This string is not arithmetic operation (Ex. a + b)");
            }
        }
        else if(String.valueOf(input.charAt(0)).isBlank()) {
            boolean checkDigit = false;
            boolean checkEmpty = false;
            for(int i = 0; i < input.length(); i++) {
                if(!String.valueOf(input.charAt(i)).isBlank()) {
                    if((String.valueOf(input.charAt(i)).equals("+")
                            || String.valueOf(input.charAt(i)).equals("*")
                            || String.valueOf(input.charAt(i)).equals("/")) && !checkEmpty) {
                        throw new Exception("Your operand/operands is empty (Ex. a + b)");
                    }
                    if(String.valueOf(input.charAt(i)).equals("-") && !checkEmpty) {
                        throw new Exception("Wrong operator/operand format (Ex. a - b). " +
                                "Maybe, you want to use a negative operand, but 1 to 10 only.");
                    }
                    checkEmpty = true;
                    if(isDigit(String.valueOf(input.charAt(i)))) {
                        checkDigit = true;
                    }
                }
            }
            if(checkEmpty) {
                for(int i = 0; i < input.length(); i++) {
                    if(!String.valueOf(input.charAt(i)).isBlank() && !isDigit(String.valueOf(input.charAt(i)))) {
                        for (String romanNumber : romanNumbers) {
                            if (String.valueOf(input.charAt(i)).equals(romanNumber)) {
                                strA.append(createSubstringARoman(input));
                                return strA.toString();
                            }
                        }
                    }
                }
            }
            else {
                throw new Exception("Your string is empty");
            }
            if(checkDigit) {
                for(int i = 0; i < input.length(); i++) {
                    if (String.valueOf(input.charAt(i)).equals("+") || String.valueOf(input.charAt(i)).equals("*")
                            || String.valueOf(input.charAt(i)).equals("/")) {
                        checkOperator = true;
                        break;
                    }
                    if (String.valueOf(input.charAt(i)).equals("-")) {
                        minusCounter++;
                        if (minusCounter > 1) {
                            throw new Exception("Wrong operator/operand format (Ex. a - b). " +
                                    "Maybe, you want to use a negative operand, but 1 to 10 only.");
                        }
                        checkOperator = true;
                    }
                }
                if(checkOperator) {
                    boolean checkAnotherDigit = false;
                    boolean checkSpace = false;
                    boolean checkAnotherEmpty = false;
                    for(int i = 0; i < input.length(); i++) {
                        if(!String.valueOf(input.charAt(i)).isBlank()) {
                            checkAnotherDigit = true;
                            if(!String.valueOf(input.charAt(i)).equals("+")
                                    && !String.valueOf(input.charAt(i)).equals("-")
                                    && !String.valueOf(input.charAt(i)).equals("*")
                                    && !String.valueOf(input.charAt(i)).equals("/")) {
                                checkAnotherEmpty = true;
                            }
                        }
                        if(String.valueOf(input.charAt(i)).isBlank() && checkAnotherDigit) {
                            checkSpace = true;
                        }
                        if(String.valueOf(input.charAt(i)).equals("+")
                                || String.valueOf(input.charAt(i)).equals("-")
                                || String.valueOf(input.charAt(i)).equals("*")
                                || String.valueOf(input.charAt(i)).equals("/")) {
                            if(checkAnotherEmpty) {
                                return strA.toString();
                            }
                        }
                        if(isDigit(String.valueOf(input.charAt(i))) && checkSpace) {
                            throw new Exception("Wrong format of operands (Ex. a + b)");
                        }
                        if(!String.valueOf(input.charAt(i)).isBlank() && !isDigit(String.valueOf(input.charAt(i)))) {
                            for (String romanNumber : romanNumbers) {
                                if (String.valueOf(input.charAt(i)).equals(romanNumber)) {
                                    throw new Exception("Calc cannot work with arabic and roman numbers together");
                                }
                            }
                        }

                        strA.append(input.charAt(i));
                    }
                }
                else {
                    throw new Exception("This string is not arithmetic operation (Ex. a + b)");
                }
            }
        }
        else if(String.valueOf(input.charAt(0)).equals("-")) {
            throw new Exception("Wrong operator/operand format (Ex. a - b)." +
                    "Maybe, you want to use a negative operand, but 1 to 10 only.");
        }
        else if(String.valueOf(input.charAt(0)).equals("+")
                || String.valueOf(input.charAt(0)).equals("*")
                || String.valueOf(input.charAt(0)).equals("/")) {
            throw new Exception("Your operand/operands is empty (Ex. a + b)");
        }
        else {
            for(String romanNumber: romanNumbers) {
                if(String.valueOf(input.charAt(0)).equals(romanNumber)) {
                    strA.append(createSubstringARoman(input));
                    return strA.toString();
                }
            }
        }
        return "0";
    }

    public static String createSubstringARoman(String input) throws Exception {
        boolean checkOperator = false;
        boolean checkSpace = false;
        boolean checkRoman = false;
        StringBuilder strA = new StringBuilder();
        String[] romanNumbers = indexingRomanNumbersEnum();

        for(int i = 0; i < input.length(); i++) {
            for(String romanNumber: romanNumbers) {
                if (String.valueOf(input.charAt(0)).equals(romanNumber)) {
                    checkRoman = true;
                    break;
                }
            }
            if(String.valueOf(input.charAt(i)).equals("+") || String.valueOf(input.charAt(i)).equals("*")
                    || String.valueOf(input.charAt(i)).equals("/") || String.valueOf(input.charAt(i)).equals("-")) {
                checkOperator = true;
                break;
            }
        }
        if(checkOperator && checkRoman) {
            for(int i = 0; i < input.length(); i++) {
                if(String.valueOf(input.charAt(i)).equals("+")
                        || String.valueOf(input.charAt(i)).equals("-")
                        || String.valueOf(input.charAt(i)).equals("*")
                        || String.valueOf(input.charAt(i)).equals("/")) {
                    return strA.toString();
                }
                if(String.valueOf(input.charAt(i)).isBlank()) {
                    checkSpace = true;
                }
                if(isDigit(String.valueOf(input.charAt(i)))) {
                    throw new Exception("Calc cannot work with arabic and roman numbers together");
                }
                if(!String.valueOf(input.charAt(i)).isBlank() && checkSpace) {
                    for(String romanNumber: romanNumbers) {
                        if(String.valueOf(input.charAt(i)).equals(romanNumber)) {
                            throw new Exception("Wrong format of operands (Ex. a + b)");
                        }
                    }
                }
                strA.append(input.charAt(i));
            }
        }
        else if(checkOperator) {
            for(int i = 0; i < input.length(); i++) {
                if(String.valueOf(input.charAt(i)).equals("+")
                        || String.valueOf(input.charAt(i)).equals("-")
                        || String.valueOf(input.charAt(i)).equals("*")
                        || String.valueOf(input.charAt(i)).equals("/")) {
                    return strA.toString();
                }
                if(!String.valueOf(input.charAt(i)).isBlank()) {
                    checkRoman = true;
                }
                if(String.valueOf(input.charAt(i)).isBlank() && checkRoman) {
                    checkSpace = true;
                }
                if(isDigit(String.valueOf(input.charAt(i)))) {
                    throw new Exception("Calc cannot work with arabic and roman numbers together");
                }
                if(!String.valueOf(input.charAt(i)).isBlank() && checkSpace) {
                    for(String romanNumber: romanNumbers) {
                        if(String.valueOf(input.charAt(i)).equals(romanNumber)) {
                            throw new Exception("Wrong format of operands (Ex. a + b)");
                        }
                    }
                }
                strA.append(input.charAt(i));
            }
        }
        else {
            throw new Exception("This string is not arithmetic operation (Ex. a + b)");
        }
        return strA.toString();
    }

    public static String createSubstringB(String input) throws Exception {
        int position = 0;
        boolean checkDigit = false;
        boolean checkAnotherDigit = false;
        boolean checkSpace = false;
        boolean checkRoman = false;
        boolean checkEmpty = false;
        StringBuilder strB = new StringBuilder();
        String[] romanNumbers = indexingRomanNumbersEnum();
        for(int i = 0; i < input.length(); i++) {
            if(String.valueOf(input.charAt(i)).equals("+")
                    || String.valueOf(input.charAt(i)).equals("-")
                    || String.valueOf(input.charAt(i)).equals("*")
                    || String.valueOf(input.charAt(i)).equals("/")) {
                position = i;
                strB.append(input.charAt(i));
                break;
            }
        }
        for(int i = position+1; i < input.length(); i++) {
            if(String.valueOf(input.charAt(i)).equals("+")
                    || String.valueOf(input.charAt(i)).equals("*")
                    || String.valueOf(input.charAt(i)).equals("/")) {
                throw new Exception("Wrong operator format (Ex. a + b)");
            }
            if(String.valueOf(input.charAt(i)).equals("-")) {
                throw new Exception("Wrong operator/operand format (Ex. a - b). " +
                        "Maybe, you want to use a negative operand, but 1 to 10 only.");
            }
            if(!String.valueOf(input.charAt(i)).isBlank()) {
                checkEmpty = true;
            }
        }
        if(checkEmpty) {
            for(int i = position+1; i < input.length(); i++) {
                for (int j = 0; j < position; j++) {
                        if (isDigit(String.valueOf(input.charAt(j)))) {
                            checkDigit = true;
                            break;
                        }
                }
                 if(checkDigit) {
                     for (int j = position+1; j < input.length(); j++) {
                         for(String romanNumber: romanNumbers) {
                             if(String.valueOf(input.charAt(j)).equals(romanNumber)) {
                                 throw new Exception("Calc cannot work with arabic and roman numbers together");
                             }
                         }
                     }
                     if(isDigit(String.valueOf(input.charAt(i)))) {
                         checkAnotherDigit = true;
                     }
                     if(String.valueOf(input.charAt(i)).isBlank() && checkAnotherDigit) {
                         checkSpace = true;
                     }
                     if(isDigit(String.valueOf(input.charAt(i))) && checkSpace) {
                         throw new Exception("Wrong format of operands (Ex. a + b)");
                     }
                 }
                 else {
                     for (int j = position+1; j < input.length(); j++) {
                        if(isDigit(String.valueOf(input.charAt(j)))) {
                            throw new Exception("Calc cannot work with arabic and roman numbers together");
                        }
                     }
                     strB.append(createSubstringBRoman(input));
                     return strB.toString();
                 }
                strB.append(input.charAt(i));
            }
            if(strB.toString().isEmpty()) {
                throw new Exception("Your second operand is empty (Ex. a + b)");
            }
            return strB.toString();
        }
        else {
            throw new Exception("Your second operand is empty (Ex. a + b)");
        }
    }

    public static String createSubstringBRoman(String input) throws Exception {
        int position = 0;
        boolean checkRoman = false;
        boolean checkSpace = false;
        StringBuilder strB = new StringBuilder();
        String[] romanNumbers = indexingRomanNumbersEnum();

        for(int i = 0; i < input.length(); i++) {
            if(String.valueOf(input.charAt(i)).equals("+")
                    || String.valueOf(input.charAt(i)).equals("-")
                    || String.valueOf(input.charAt(i)).equals("*")
                    || String.valueOf(input.charAt(i)).equals("/")) {
                position = i;
                break;
            }
        }

        for(int i = position+1; i < input.length(); i++) {
            if(!String.valueOf(input.charAt(i)).isBlank()) {
                checkRoman = true;
            }
            if(String.valueOf(input.charAt(i)).isBlank() && checkRoman) {
                checkSpace = true;
            }
            if(!String.valueOf(input.charAt(i)).isBlank() && checkSpace) {
                throw new Exception("Wrong format of operands (Ex. a + b)");
            }
            strB.append(input.charAt(i));
        }
        return strB.toString();
    }

    public static String createFirstOperand(String input) throws Exception {
        int a = 0;
        boolean checkDigit = false;
        StringBuilder strA = new StringBuilder();
        for(int i = 0; i < input.length(); i++) {
            if(!isDigit(String.valueOf(input.charAt(i))) && !String.valueOf(input.charAt(i)).isBlank()) {
                strA.append(createFirstOperandRoman(input));
                return strA.toString();
            }
        }
        for(int i = 0; i < input.length(); i++) {
            if(!String.valueOf(input.charAt(i)).isBlank()) {
                checkDigit = true;
            }
            if(String.valueOf(input.charAt(i)).isBlank() && checkDigit) {
                break;
            }
            if(checkDigit) {
                strA.append(input.charAt(i));
            }
        }
        a = Integer.parseInt(strA.toString());
        if(a == 0 || a > 10) {
            throw new Exception("The number you enter must be less then 10 or not equals 0");
        }
        return strA.toString();
    }

    public static String createFirstOperandRoman(String input) throws Exception {
        boolean checkRoman = false;
        StringBuilder strA = new StringBuilder();
        String[] romanNumberFormat = indexingRomanNumberFormat();
        for(int i = 0; i < input.length(); i++) {
            if(!String.valueOf(input.charAt(i)).isBlank()) {
                checkRoman = true;
            }
            if(String.valueOf(input.charAt(i)).isBlank() && checkRoman) {
                break;
            }
            if(checkRoman) {
                strA.append(input.charAt(i));
            }
        }
        for(String format: romanNumberFormat) {
            if(strA.toString().equals(format)) {
                return strA.toString();
            }
        }
        throw new Exception("Wrong format of roman number. Maybe, you can use number greater then 10");
    }

    public static String createSecondOperand(String input) throws Exception {
        int b = 0;
        boolean checkDigit = false;
        StringBuilder strB = new StringBuilder();
        for(int i = 1; i < input.length(); i++) {
            if(!isDigit(String.valueOf(input.charAt(i))) && !String.valueOf(input.charAt(i)).isBlank()) {
                strB.append(createSecondOperandRoman(input));
                return strB.toString();
            }
        }
        for(int i = 1; i < input.length(); i++) {
            if(!String.valueOf(input.charAt(i)).isBlank()) {
                checkDigit = true;
            }
            if(String.valueOf(input.charAt(i)).isBlank() && checkDigit) {
                break;
            }
            if(checkDigit) {
                strB.append(input.charAt(i));
            }
        }
        b = Integer.parseInt(strB.toString());
        if(b == 0 || b > 10) {
            throw new Exception("The number you enter must be less then 10 or not equals 0");
        }
        return strB.toString();
    }

    public static String createSecondOperandRoman(String input) throws Exception {
        boolean checkRoman = false;
        StringBuilder strB = new StringBuilder();
        String[] romanNumberFormat = indexingRomanNumberFormat();
        for(int i = 1; i < input.length(); i++) {
            if(!String.valueOf(input.charAt(i)).isBlank()) {
                checkRoman = true;
            }
            if(String.valueOf(input.charAt(i)).isBlank() && checkRoman) {
                break;
            }
            if(checkRoman) {
                strB.append(input.charAt(i));
            }
        }
        for(String format: romanNumberFormat) {
            if(strB.toString().equals(format)) {
                return strB.toString();
            }
        }
        throw new Exception("Wrong format of roman number. Maybe, you can use number greater then 10");
    }

    public static boolean isDigit(String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public static String createResult(String operandA, String operandB, char symbol) throws Exception {
        String result;
        int a = 0;
        int b = 0;
        if(isDigit(operandA)) {
            a = Integer.parseInt(operandA);
            b = Integer.parseInt(operandB);
            result = String.valueOf(calcResult(a, b, symbol));
            return result;
        }
        else {
            result = createResultRoman(operandA, operandB, symbol);
            return result;
        }
    }


    public static String createResultRoman(String operandA, String operandB, char symbol) throws Exception {
        int result;
        String finalResult;
        int a = 0;
        int b = 0;

        a = reformRomanToArabic(operandA);
        b = reformRomanToArabic(operandB);

        result = calcResult(a, b, symbol);
        if(result < 1) {
            throw new Exception("The result of arithmetic operation with roman numbers" +
                    "must not be negative or equal to 0");
        }
        finalResult = reformArabicToRoman(result);

        return finalResult;

    }

    public static int calcResult(int a, int b, char symbol) throws Exception {
        int result = 0;
        switch (symbol) {
            case '+' -> {
                result = a + b;
            }
            case '-' -> {
                result = a - b;
            }
            case '*' -> {
                result = a * b;
            }
            case '/' -> {
                result = a / b;
            }
        }
        return result;
    }

    public static int reformRomanToArabic(String operand) throws Exception {
        int result = 0;
        int[] numbers = arithmeticRomanNumberEnum();
        String[] romanNumbers = indexingRomanNumbersEnum();
        for(int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < romanNumbers.length; j++) {
                if (operand.equals(romanNumbers[j])) {
                    result = numbers[j];
                    return result;
                }
                else {
                    if(operand.length() < i+1) {
                        break;
                    }
                    if(String.valueOf(operand.charAt(i)).equals(romanNumbers[j])) {
                        result = result + numbers[j];
                        break;
                    }
                }
            }
            if(operand.length() < i+1) {
                break;
            }
        }
        if(result > 10) {
            throw new Exception("The number you enter must be less then 10");
        }
        if(result < 1) {
            throw new Exception("The roman number cannot be less then 1");
        }
        return result;
    }

    public static String reformArabicToRoman(int result) {
        int[] numbers = arithmeticRomanNumberEnum();
        String[] romanNumbers = indexingRomanNumbersEnum();
        StringBuilder finalResult = new StringBuilder();
        int position = 0;

        for (int i = 0; i < numbers.length; i++) {
            if (result == numbers[i]) {
                return romanNumbers[i];
            } else if (result < numbers[i]) {
                for (int j = i; j > 0; j--) {
                    finalResult.append(romanNumbers[position - 1]);
                    result = result - numbers[position - 1];
                    if (result >= numbers[position - 1]) {
                        j++;
                    } else {
                        for (int k = 0; k < numbers.length; k++) {
                            if (result == 0) {
                                return finalResult.toString();
                            }
                            if (result < numbers[k]) {
                                position = k;
                                break;
                            }
                        }
                    }
                }
            }
            position++;
        }
        return "Hello";
    }

    public static String[] indexingRomanNumbersEnum() {
        return new String[]{String.valueOf(RomanNumbers.I), String.valueOf(RomanNumbers.IV),
                String.valueOf(RomanNumbers.V), String.valueOf(RomanNumbers.IX), String.valueOf(RomanNumbers.X),
                String.valueOf(RomanNumbers.XL), String.valueOf(RomanNumbers.L), String.valueOf(RomanNumbers.XC),
                String.valueOf(RomanNumbers.C), String.valueOf(RomanNumbers.CD), String.valueOf(RomanNumbers.D),
                String.valueOf(RomanNumbers.CM), String.valueOf(RomanNumbers.M)};
    }

    public static String[] indexingRomanNumberFormat() {
        return new String[]{String.valueOf(RomanNumberFormat.I), String.valueOf(RomanNumberFormat.II),
                String.valueOf(RomanNumberFormat.III), String.valueOf(RomanNumberFormat.IV),
                String.valueOf(RomanNumberFormat.V), String.valueOf(RomanNumberFormat.VI),
                String.valueOf(RomanNumberFormat.VII), String.valueOf(RomanNumberFormat.VIII),
                String.valueOf(RomanNumberFormat.IX), String.valueOf(RomanNumberFormat.X)};
    }

    public static int[] arithmeticRomanNumberEnum() {
        return new int[]{1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    }
}
