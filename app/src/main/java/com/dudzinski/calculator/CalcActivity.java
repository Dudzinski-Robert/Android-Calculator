package com.dudzinski.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Objects;

public class CalcActivity extends AppCompatActivity {

    private TextView inputText, outputText;
    private Calculator calc = new Calculator(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black_background));

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            if (Objects.equals(extras.getString("calculator"), "SIMPLE")) {
                setContentView(R.layout.simple_calc);
            } else if (Objects.equals(extras.getString("calculator"), "ADVANCED")) {
                setContentView(R.layout.advanced_calc);
            }
        }

        inputText = findViewById(R.id.input_text);
        outputText = findViewById(R.id.output_text);

        if(extras != null){

            if(extras.getSerializable("calcData") != null){
                Calculator tempCalc = (Calculator)extras.getSerializable("calcData");
                if(tempCalc != null){
                    calc = tempCalc;
                    updateTextViews();
                }
            }
        }

        if(savedInstanceState != null){
            calc = (Calculator) savedInstanceState.getSerializable("calcData");
            updateTextViews();
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("calcData", calc);
    }

    private void updateTextViews(){
        String inputString = "";
        if(Objects.equals(calc.getOperation(), "")){
            inputString = calc.getNumber(0).toString();
        } else {
            inputString = calc.getNumber(0).toString();
            inputString += calc.getOperation();
            if(!calc.getNumber(1).isEmpty())
                inputString += calc.getNumber(1).toString();
        }

        inputText.setText(inputString);
        outputText.setText(getResult());
    }

    private void addDigit(String digit){
        Log.i("New digit", digit);

        calc.addDigit(digit);
        updateTextViews();
    }

    private void setOperation(String operation){
        Log.i("Button down", operation);

        calc.setOperation(operation);
        updateTextViews();
    }

    private void addFunction(String func){
        Log.i("Button down", func);

        calc.getCurrentNumber().setFunction(func);
        updateTextViews();
    }

    private String getResult(){
        try{
            return calc.getResult();
        } catch (IllegalArgumentException e){
            calc.getNumber(0).setError(true);
            return getResources().getString(R.string.error);
        } catch (ArithmeticException e){
            return getResources().getString(R.string.divisionByZero);
        }catch (OutOfMemoryError e){
            return getResources().getString(R.string.tooBig);
        }
    }

//    **************************************************
//    ****************   SIMPLE CALC  ******************
//    **************************************************

    public void clearAll(View view){
        calc.clearAll();
        updateTextViews();
    }

    public void clearDigit(View view){
        Log.i("Button down", "Clear");

        if(calc.getCurrentNumber().isEmpty()){
            calc.setOperation("");
        } else {
            calc.getCurrentNumber().clearDigit();
        }

        updateTextViews();
    }

    public void signChangeButton(View view){
        Log.i("Button down", "signChange");

        calc.getCurrentNumber().signChange();
        updateTextViews();
    }

    public void pointButton(View view){
        Log.i("Button down", "Point");

        calc.getCurrentNumber().addPoint();
        updateTextViews();
    }

    public void equalsButton(View view){
        calc.getNumber(0).setNumber(getResult());
        calc.getNumber(1).reset();
        calc.setOperation("");

        updateTextViews();
    }

    public void addButton(View view) {
        setOperation("+");
    }

    public void subButton(View view) {
        setOperation("-");
    }

    public void mulButton(View view) {
        setOperation("×");
    }

    public void divButton(View view) {
        setOperation("÷");
    }

    public void zeroButton(View view) {
        addDigit("0");
    }

    public void oneButton(View view) {
        addDigit("1");
    }

    public void twoButton(View view) {
        addDigit("2");
    }

    public void threeButton(View view) {
        addDigit("3");
    }

    public void fourButton(View view) {
        addDigit("4");
    }

    public void fiveButton(View view) {
        addDigit("5");
    }

    public void sixButton(View view) {
        addDigit("6");
    }

    public void sevenButton(View view) {
        addDigit("7");
    }

    public void eightButton(View view) {
        addDigit("8");
    }

    public void nineButton(View view) {
        addDigit("9");
    }

    public void backToHome(View view) {
        Log.i("Button down", "Back to home");
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("calcData", calc);
        startActivity(intent);
    }

//    **************************************************
//    ****************  ADVANCED CALC  *****************
//    **************************************************

    public void sinButton(View view) {
        addFunction("SIN");
    }

    public void cosButton(View view) {
        addFunction("COS");
    }

    public void tanButton(View view) {
        addFunction("TAN");
    }

    public void lnButton(View view) {
        addFunction("LN");
    }

    public void logButton(View view) {
        addFunction("LOG");
    }

    public void percentButton(View view) {
        addFunction("%");
    }

    public void sqrtButton(View view) {
        addFunction("√");
    }

    public void pow2Button(View view) {
        calc.setOperation("^");
        calc.getNumber(1).setNumber("2");

        updateTextViews();
    }

    public void powYButton(View view) {
        setOperation("^");
    }

    public void piButton(View view) {
        calc.getCurrentNumber().setNumber(3.14159265d);
        updateTextViews();
    }
}