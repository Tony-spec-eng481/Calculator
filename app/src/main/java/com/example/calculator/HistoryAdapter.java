package com.example.calculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<CalculationHistory> historyList;

    public HistoryAdapter() {
        this.historyList = new ArrayList<>();
    }

    public void setHistoryList(List<CalculationHistory> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CalculationHistory history = historyList.get(position);
        holder.inputText.setText(history.getInput());
        holder.resultText.setText(history.getResult());
        holder.typeText.setText(history.getType());

        if (history.getTimestamp() != null) {
            holder.timestampText.setText(history.getTimestamp());
        }
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView inputText, resultText, typeText, timestampText;

        ViewHolder(View itemView) {
            super(itemView);
            inputText = itemView.findViewById(R.id.inputText);
            resultText = itemView.findViewById(R.id.resultText);
            typeText = itemView.findViewById(R.id.typeText);
            timestampText = itemView.findViewById(R.id.timestampText);
        }
    }
}