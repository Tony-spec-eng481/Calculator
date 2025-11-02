package com.example.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.text.DecimalFormat;

public class CalculatorFragment extends Fragment {

    private EditText inputField;
    private TextView resultText, conversionType;
    private RadioGroup conversionGroup;
    private Spinner inputBaseSpinner, outputBaseSpinner;
    private Button btnNumbers, btnLetters;
    private View numberKeyboard, letterKeyboard, baseSelectionLayout;
    private View rootView;

    private DatabaseHelper dbHelper;

    private boolean numbersVisible = true;
    private String currentInput = "";
    private String currentConversion = "arithmetic";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_calculator, container, false);

        initializeViews();
        setupClickListeners();
        setupSpinners();
        dbHelper = new DatabaseHelper(getContext());

        return rootView;
    }

    private void initializeViews() {
        if (rootView == null) return;

        inputField = rootView.findViewById(R.id.inputField);
        resultText = rootView.findViewById(R.id.resultText);
        conversionType = rootView.findViewById(R.id.conversionType);
        conversionGroup = rootView.findViewById(R.id.conversionGroup);
        inputBaseSpinner = rootView.findViewById(R.id.inputBaseSpinner);
        outputBaseSpinner = rootView.findViewById(R.id.outputBaseSpinner);
        btnNumbers = rootView.findViewById(R.id.btnNumbers);
        btnLetters = rootView.findViewById(R.id.btnLetters);
        numberKeyboard = rootView.findViewById(R.id.numberKeyboard);
        letterKeyboard = rootView.findViewById(R.id.letterKeyboard);
        baseSelectionLayout = rootView.findViewById(R.id.baseSelectionLayout);

        // Show number keyboard by default
        showNumberKeyboard();

        // Hide base selection by default
        baseSelectionLayout.setVisibility(View.GONE);
    }

    private void setupSpinners() {
        // Create adapter for base selection
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.base_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        inputBaseSpinner.setAdapter(adapter);
        outputBaseSpinner.setAdapter(adapter);

        // Set default selections
        inputBaseSpinner.setSelection(2); // Decimal
        outputBaseSpinner.setSelection(3); // Hexadecimal
    }

    private void setupClickListeners() {
        if (rootView == null) return;

        // Conversion type radio group
        conversionGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioArithmetic) {
                setConversionMode("arithmetic");
            } else if (checkedId == R.id.radioBaseConversion) {
                setConversionMode("base");
            }
        });

        // Keyboard toggle buttons
        btnNumbers.setOnClickListener(v -> showNumberKeyboard());
        btnLetters.setOnClickListener(v -> showLetterKeyboard());

        // Number buttons (0-9)
        int[] numberButtons = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};

        for (int id : numberButtons) {
            rootView.findViewById(id).setOnClickListener(v -> {
                Button btn = (Button) v;
                appendToInput(btn.getText().toString());
            });
        }

        // Letter buttons (A-F)
        int[] letterButtons = {R.id.btnA, R.id.btnB, R.id.btnC, R.id.btnD, R.id.btnE, R.id.btnF};
        for (int id : letterButtons) {
            rootView.findViewById(id).setOnClickListener(v -> {
                Button btn = (Button) v;
                appendToInput(btn.getText().toString());
            });
        }

        // Arithmetic buttons
        rootView.findViewById(R.id.btnAdd).setOnClickListener(v -> appendToInput("+"));
        rootView.findViewById(R.id.btnSubtract).setOnClickListener(v -> appendToInput("-"));
        rootView.findViewById(R.id.btnMultiply).setOnClickListener(v -> appendToInput("*"));
        rootView.findViewById(R.id.btnDivide).setOnClickListener(v -> appendToInput("/"));
        rootView.findViewById(R.id.btnDecimal).setOnClickListener(v -> appendToInput("."));

        // Function buttons
        rootView.findViewById(R.id.btnEquals).setOnClickListener(v -> performCalculation());
        rootView.findViewById(R.id.btnDelete).setOnClickListener(v -> deleteLastCharacter());
        rootView.findViewById(R.id.btnClear).setOnClickListener(v -> clearAll());
    }

    private void setConversionMode(String mode) {
        currentConversion = mode;
        if ("arithmetic".equals(mode)) {
            conversionType.setText("Arithmetic Calculator");
            baseSelectionLayout.setVisibility(View.GONE);
        } else {
            conversionType.setText("Base Converter");
            baseSelectionLayout.setVisibility(View.VISIBLE);
        }
        clearAll();
    }

    private void showNumberKeyboard() {
        numberKeyboard.setVisibility(View.VISIBLE);
        letterKeyboard.setVisibility(View.GONE);
        numbersVisible = true;
    }

    private void showLetterKeyboard() {
        numberKeyboard.setVisibility(View.GONE);
        letterKeyboard.setVisibility(View.VISIBLE);
        numbersVisible = false;
    }

    private void appendToInput(String text) {
        currentInput += text;
        inputField.setText(currentInput);
    }

    private void deleteLastCharacter() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            inputField.setText(currentInput);
        }
    }

    private void clearAll() {
        currentInput = "";
        inputField.setText("");
        resultText.setText("");
    }

    private void performCalculation() {
        if (currentInput.isEmpty()) {
            Toast.makeText(getContext(), "Please enter input", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String result;
            if ("arithmetic".equals(currentConversion)) {
                result = calculateArithmetic(currentInput);
            } else {
                result = performBaseConversionWithCalculation();
            }

            resultText.setText("Result: " + result);

            // Save to history
            CalculationHistory history = new CalculationHistory(currentInput, result, currentConversion);
            dbHelper.addCalculation(history);

        } catch (Exception e) {
            resultText.setText("Error: Invalid input");
            Toast.makeText(getContext(), "Calculation error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String calculateArithmetic(String expression) {
        try {
            return evaluateExpression(expression);
        } catch (Exception e) {
            return "Error in calculation";
        }
    }

    private String evaluateExpression(String expression) {
        try {
            expression = expression.replaceAll("\\s+", "");

            if (expression.matches("-?\\d+\\.?\\d*")) {
                return expression;
            }

            return evaluateSimpleExpression(expression);
        } catch (Exception e) {
            return "Invalid expression";
        }
    }

    private String evaluateSimpleExpression(String expression) {
        try {
            // Handle multiplication and division first
            if (expression.contains("*") || expression.contains("/")) {
                for (int i = 0; i < expression.length(); i++) {
                    char c = expression.charAt(i);
                    if (c == '*' || c == '/') {
                        double left = getLeftOperand(expression, i);
                        double right = getRightOperand(expression, i);
                        double operationResult = (c == '*') ? left * right : left / right;

                        String leftStr = String.valueOf(left);
                        String rightStr = String.valueOf(right);
                        String toReplace = leftStr + c + rightStr;
                        expression = expression.replace(toReplace, String.valueOf(operationResult));
                        break;
                    }
                }
                return evaluateSimpleExpression(expression);
            }

            // Then handle addition and subtraction
            if (expression.contains("+") || (expression.contains("-") && expression.length() > 1)) {
                for (int i = 1; i < expression.length(); i++) {
                    char c = expression.charAt(i);
                    if ((c == '+' || c == '-') && !isPartOfNumber(expression, i)) {
                        double left = getLeftOperand(expression, i);
                        double right = getRightOperand(expression, i);
                        double operationResult = (c == '+') ? left + right : left - right;

                        String leftStr = String.valueOf(left);
                        String rightStr = String.valueOf(right);
                        String toReplace = leftStr + c + rightStr;
                        expression = expression.replace(toReplace, String.valueOf(operationResult));
                        break;
                    }
                }
                return evaluateSimpleExpression(expression);
            }

            return expression;
        } catch (Exception e) {
            return "Calculation error";
        }
    }

    private double getLeftOperand(String expression, int operatorIndex) {
        int start = operatorIndex - 1;
        while (start >= 0 && (Character.isDigit(expression.charAt(start)) ||
                expression.charAt(start) == '.' ||
                (expression.charAt(start) == '-' && (start == 0 || !Character.isDigit(expression.charAt(start - 1)))))) {
            start--;
        }
        return Double.parseDouble(expression.substring(start + 1, operatorIndex));
    }

    private double getRightOperand(String expression, int operatorIndex) {
        int end = operatorIndex + 1;
        while (end < expression.length() && (Character.isDigit(expression.charAt(end)) ||
                expression.charAt(end) == '.' ||
                (expression.charAt(end) == '-' && end == operatorIndex + 1))) {
            end++;
        }
        return Double.parseDouble(expression.substring(operatorIndex + 1, end));
    }

    private boolean isPartOfNumber(String expression, int index) {
        return Character.isDigit(expression.charAt(index - 1));
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.valueOf((long) result);
        } else {
            DecimalFormat df = new DecimalFormat("#.#######");
            return df.format(result);
        }
    }

    private String performBaseConversionWithCalculation() {
        String input = currentInput.toUpperCase();
        int inputBase = getBaseFromSpinner(inputBaseSpinner);
        int outputBase = getBaseFromSpinner(outputBaseSpinner);

        // Check if the input contains arithmetic operations
        if (containsArithmeticOperations(input)) {
            return performArithmeticWithBaseConversion(input, inputBase, outputBase);
        } else {
            // Simple base conversion without arithmetic
            return performSimpleBaseConversion(input, inputBase, outputBase);
        }
    }

    private boolean containsArithmeticOperations(String input) {
        return input.contains("+") || input.contains("-") || input.contains("*") || input.contains("/");
    }

    private String performArithmeticWithBaseConversion(String expression, int inputBase, int outputBase) {
        try {
            // Split the expression into parts based on operators
            String[] parts = expression.split("(?=[+\\-*/])|(?<=[+\\-*/])");

            if (parts.length < 3) {
                return "Invalid expression";
            }

            // Parse numbers according to input base and perform calculation
            double result = 0;
            char currentOperator = '+';

            for (String part : parts) {
                if (part.matches("[+\\-*/]")) {
                    currentOperator = part.charAt(0);
                } else {
                    // Convert the number from input base to decimal
                    long decimalValue;
                    try {
                        decimalValue = Long.parseLong(part, inputBase);
                    } catch (NumberFormatException e) {
                        return "Invalid number: " + part + " for base " + inputBase;
                    }

                    // Perform the operation
                    switch (currentOperator) {
                        case '+':
                            result += decimalValue;
                            break;
                        case '-':
                            result -= decimalValue;
                            break;
                        case '*':
                            result *= decimalValue;
                            break;
                        case '/':
                            if (decimalValue == 0) {
                                return "Cannot divide by zero";
                            }
                            result /= decimalValue;
                            break;
                    }
                }
            }

            // Convert the final result to the output base
            return convertDecimalToBase((long) result, outputBase);

        } catch (Exception e) {
            return "Error in calculation: " + e.getMessage();
        }
    }

    private String performSimpleBaseConversion(String input, int inputBase, int outputBase) {
        // Validate input based on selected base
        if (!isValidInputForBase(input, inputBase)) {
            return "Invalid input for " + getBaseName(inputBase);
        }

        // Convert to decimal first
        long decimalValue;
        try {
            decimalValue = Long.parseLong(input, inputBase);
        } catch (NumberFormatException e) {
            return "Invalid input for " + getBaseName(inputBase);
        }

        // Convert to output base
        return convertDecimalToBase(decimalValue, outputBase);
    }

    private String convertDecimalToBase(long decimalValue, int outputBase) {
        switch (outputBase) {
            case 2: return Long.toBinaryString(decimalValue);
            case 8: return Long.toOctalString(decimalValue);
            case 10: return String.valueOf(decimalValue);
            case 16: return Long.toHexString(decimalValue).toUpperCase();
            default: return "Unsupported base";
        }
    }

    private int getBaseFromSpinner(Spinner spinner) {
        String selected = spinner.getSelectedItem().toString();
        switch (selected) {
            case "Binary": return 2;
            case "Octal": return 8;
            case "Decimal": return 10;
            case "Hexadecimal": return 16;
            default: return 10;
        }
    }

    private String getBaseName(int base) {
        switch (base) {
            case 2: return "Binary";
            case 8: return "Octal";
            case 10: return "Decimal";
            case 16: return "Hexadecimal";
            default: return "Unknown";
        }
    }

    private boolean isValidInputForBase(String input, int base) {
        try {
            switch (base) {
                case 2: // Binary
                    return input.matches("[01]+");
                case 8: // Octal
                    return input.matches("[0-7]+");
                case 10: // Decimal
                    return input.matches("[0-9]+");
                case 16: // Hexadecimal
                    return input.matches("[0-9A-Fa-f]+");
                default:
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
}