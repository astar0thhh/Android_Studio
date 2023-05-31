package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int BUTTON_C = R.id.button_c;
    private static final int BUTTON_EQUAL = R.id.button_equal;
    private TextView solutionTextView;
    private TextView resultTextView;
    private StringBuilder expressionBuilder;
    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        solutionTextView = findViewById(R.id.solution_tv);
        resultTextView = findViewById(R.id.result_tv);
        expressionBuilder = new StringBuilder();
        calculator = new Calculator();

        setButtonClickListeners();
    }

    private void setButtonClickListeners() {
        int[] buttonIds = {
                R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
                R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7,
                R.id.button_8, R.id.button_9, R.id.button_add, R.id.button_subtract,
                R.id.button_multiply, R.id.button_divide, R.id.button_dot,
                R.id.button_open_bracket, R.id.button_close_bracket, BUTTON_C,
                BUTTON_EQUAL
        };

        for (int buttonId : buttonIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(this);
        }
    }




    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == BUTTON_C) {
            clearExpression();
        } else if (viewId == BUTTON_EQUAL) {
            evaluateExpression();
        } else {
            Button button = (Button) v;
            appendToExpression(button.getText().toString());
        }
        updateSolutionTextView();
    }

    private void appendToExpression(String value) {
        expressionBuilder.append(value);
        updateSolutionTextView();
    }


    private void clearExpression() {
        expressionBuilder.setLength(0);
        updateSolutionTextView();
        updateResultTextView("0");
    }

    private void evaluateExpression() {
        String expression = expressionBuilder.toString();
        try {
            double result = calculator.evaluateExpression(expression);
            String resultString = Double.toString(result);
            updateResultTextView(resultString);
            expressionBuilder.setLength(0);
            expressionBuilder.append(resultString);
            updateSolutionTextView();
        } catch (IllegalArgumentException e) {
            updateResultTextView("Error: " + e.getMessage());
        }
    }

    private void updateSolutionTextView() {
        String expression = expressionBuilder.toString();
        solutionTextView.setText(expression);
    }

    private void updateResultTextView(String result) {
        resultTextView.setText(result);
    }
}
