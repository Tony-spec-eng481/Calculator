package com.example.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView historyRecyclerView;
    private HistoryAdapter historyAdapter;
    private DatabaseHelper dbHelper;
    private Button btnClearHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        historyRecyclerView = view.findViewById(R.id.historyRecyclerView);
        btnClearHistory = view.findViewById(R.id.btnClearHistory);
        dbHelper = new DatabaseHelper(getContext());

        setupRecyclerView();
        setupClickListeners();
        loadHistory();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadHistory();
    }

    private void setupRecyclerView() {
        historyAdapter = new HistoryAdapter();
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        historyRecyclerView.setAdapter(historyAdapter);
    }

    private void setupClickListeners() {
        btnClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHistory();
            }
        });
    }

    private void loadHistory() {
        List<CalculationHistory> historyList = dbHelper.getAllCalculations();
        historyAdapter.setHistoryList(historyList);

        // Show/hide clear button based on whether there's history
        if (historyList.isEmpty()) {
            btnClearHistory.setVisibility(View.GONE);
        } else {
            btnClearHistory.setVisibility(View.VISIBLE);
        }
    }

    private void clearHistory() {
        dbHelper.clearHistory();
        loadHistory(); // Refresh the list (will be empty now)
        Toast.makeText(getContext(), "History cleared", Toast.LENGTH_SHORT).show();
    }
}