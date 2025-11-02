package com.example.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class InfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        TextView infoText = view.findViewById(R.id.infoText);
        String info = "Advanced Calculator & Converter\n\n" +
                "Features:\n" +
                "• Arithmetic calculations (+, -, *, /)\n" +
                "• Fraction calculations\n" +
                "• Base conversions (Binary, Octal, Decimal, Hexadecimal)\n" +
                "• History tracking\n" +
                "• Professional UI with toggle keyboards\n\n" +
                "Instructions:\n" +
                "1. Select conversion type\n" +
                "2. Choose input/output bases\n" +
                "3. Use number/letter keyboards\n" +
                "4. View history in History tab";

        infoText.setText(info);

        return view;
    }
}