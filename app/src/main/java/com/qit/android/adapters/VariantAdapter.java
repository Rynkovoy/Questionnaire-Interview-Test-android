package com.qit.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qit.R;
import com.qit.android.models.answer.Variant;

import java.util.List;

public class VariantAdapter extends RecyclerView.Adapter<VariantAdapter.VariantViewHolder> {

    private List<Variant> variantList;

    public static class VariantViewHolder extends RecyclerView.ViewHolder {

        public TextView tvQuestion;
        public TextView tvIsNecessary;

        public VariantViewHolder(View view) {
            super(view);
            tvQuestion = view.findViewById(R.id.tvQuestion);
            tvIsNecessary = view.findViewById(R.id.tvIsNecessary);
        }
    }

    public VariantAdapter(List<Variant> variantList) {
        this.variantList = variantList;
    }

    @Override
    public VariantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(VariantViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return variantList.size();
    }

}
