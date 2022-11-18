package com.dudzinski.calculator;

import java.io.Serializable;
import java.util.Objects;

public class Number implements Serializable {

    private String number = "";
    private final int maxLength;
    private boolean negativeValue = false;
    private boolean decimalPoint = false;
    private boolean error = false;
    private String function = "";


    public Number(int maxLength){
        this.maxLength = maxLength;
    }

    public void setError(boolean error){
        this.error = error;
    }

    public boolean getError(){
        return error;
    }

    public String toString(){
        String newString = "";
        if(negativeValue)
            newString = "-";

        newString += number;

//        if(newString.equals("")){
//            newString = "0";
//        }

        if(Objects.equals(function, "")){
            return newString;
        } else {
            return function + "(" + newString + ")";
        }
    }

    public Double toDouble(){
        if(Objects.equals(number, "")){
            return 0d;
        } else{
            if(negativeValue)
                return Double.parseDouble("-" + number);
            else
                return Double.parseDouble(number);
        }
    }

    public boolean isEmpty(){
        return number.length() == 0 && Objects.equals(function, "");
    }

    public void setNumber(String number){
        reset();
        this.number = number;
        setAttributes();
    }

    public void setFunction(String func){
        if(Objects.equals(this.function, func)){
            this.function = "";
        } else {
            this.function = func;
        }
    }

    public String getFunction(){
        return function;
    }

    public void setNumber(Double number){
        reset();
        this.number = number.toString();
        setAttributes();
    }

    private void setAttributes(){
        if(number.charAt(0) == '-'){
            number = number.substring(1);
            negativeValue = true;
        }

        if(number.indexOf('.') != -1)
            decimalPoint = true;

        function = "";
    }

    public void reset(){
        number = "";
        function = "";
        negativeValue = false;
        decimalPoint = false;
    }

    public int getLength(){
        return number.length();
    }

    public void addDigit(String digit){
        if(number.length() >= maxLength)
            return;

        number += digit;
    }

    public void addPoint(){
        if(!decimalPoint){
            decimalPoint = true;
            if(Objects.equals(number, "")){
                number += "0.";
            } else {
                number += ".";
            }
        }
    }

    public void clearDigit(){
        if(error)
            reset();

        if(number.length() == 0){
            function = "";
            return;
        }

        if(number.charAt(number.length() - 1) == '.'){
            decimalPoint = false;
        }
        number = number.substring(0, number.length() - 1);
    }

    public void signChange(){
        negativeValue = !negativeValue;
    }
}
