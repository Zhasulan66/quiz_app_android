package com.example.quizapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.activities.QuestionActivity;
import com.example.quizapp.databinding.ItemSetBinding;
import com.example.quizapp.models.SetModel;

import java.util.ArrayList;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder> {

    Context context;
    ArrayList<SetModel> list;
    private final LayoutInflater inflater;

    public SetAdapter(Context context, ArrayList<SetModel> setList) {
        this.context =context;
        this.list = setList;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_set, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SetModel setModel = list.get(position);

        holder.binding.setName.setText(setModel.getSetName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionActivity.class);
                intent.putExtra("set", setModel.getSetName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ItemSetBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemSetBinding.bind(itemView);
        }
    }
}
