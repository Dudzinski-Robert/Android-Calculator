package com.dudzinski.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Objects;

public class Calculator extends AppCompatActivity implements Serializable {

    private final Number number1;
    private final Number number2;
    private String operation = "";
    private final int maxNumberLength;

    public Calculator(int maxNumberLength){
        number1 = new Number(maxNumberLength);
        number2 = new Number(maxNumberLength);
        this.maxNumberLength = maxNumberLength;
    }

    public int getMaxNumberLength(){
        return maxNumberLength;
    }

    public String getOperation(){
        return operation;
    }

    public Number getCurrentNumber(){
        if(Objects.equals(operation, ""))
            return number1;
        else
            return number2;
    }

    public Number getNumber(int index){
        if(index == 0){
            return number1;
        } else {
            return number2;
        }
    }

    public void addDigit(String digit){
        getCurrentNumber().addDigit(digit);
    }

    public void setOperation(String operation){
        if(!Objects.equals(this.operation, "")) {
            Log.i("set operator", operation);
            try{
                number1.setNumber(getResult());
            } catch (Exception e){
                number1.setError(true);
            }
            number2.reset();
        }

        this.operation = operation;
    }

    public void clearAll(){
        number1.reset();
        number2.reset();
        operation = "";
    }

    public double calculate(){
        double result = 0d;
        double num1;
        double num2;
        try{
            num1 = number1.toDouble();
            num2 = number2.toDouble();
        } catch (Exception e){
            throw new IllegalArgumentException();
        }

        try{
            num1 = calculateFunction(num1, number1.getFunction());

            if(!number2.isEmpty())
                num2 = calculateFunction(num2, number2.getFunction());

        } catch (Exception e){
            throw new IllegalArgumentException();
        }


        switch (operation){
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "×":
                if(number2.getLength() == 0)
                    num2 = 1d;

                result = num1 * num2;
                break;
            case "÷":
                if(number2.getLength() == 0)
                    num2 = 1d;

                if(num2 == 0d){
                    throw new ArithmeticException();
                }

                result = num1 / num2;
                break;

            case "^":
                if(number2.getLength() == 0)
                    num2 = 1d;

                result = Math.pow(num1, num2);
                break;

            default:
                result = num1;
        }

        if(Math.abs(result) > 9999999999d){
            throw new OutOfMemoryError();
        }

        return result;
    }

    private double calculateFunction(double number, String func){

        double newNumber;

        switch (func){
            case "SIN":
                newNumber = Math.sin(number);
                break;
            case "COS":
                newNumber = Math.cos(number);
                break;

            case "TAN":
                newNumber = Math.tan(number);
                break;

            case "LN":
                if(number <= 0)
                    throw new IllegalArgumentException();

                newNumber = Math.log(number);
                break;

            case "LOG":
                if(number <= 0)
                    throw new IllegalArgumentException();

                newNumber = Math.log10(number);
                break;

            case "√":
                if(number < 0)
                    throw new IllegalArgumentException();

                newNumber = Math.sqrt(number);
                break;

            case "%":
                newNumber = number / 100;
                break;

            default:
                newNumber = number;
        }

        return newNumber;
    }

    public String getResult(){
        double result = calculate();

        DecimalFormat df = new DecimalFormat("#.########");
        int precision = getMaxNumberLength() - df.format(result).indexOf('.') - 1;
        if(result < 0)
            precision += 1;
        long tmp = (long)Math.pow(10, precision);
        long newNumber = Math.round(result * tmp);

        return df.format((double) newNumber / tmp).replace(',','.');
    }
}
