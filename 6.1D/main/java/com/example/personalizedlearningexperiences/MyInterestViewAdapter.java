package com.example.personalizedlearningexperiences;

import static com.example.personalizedlearningexperiences.MainActivity.interestsList;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalizedlearningexperiences.databinding.FragmentInterestsBinding;
import com.example.personalizedlearningexperiences.databinding.InterestBinding;

import java.util.ArrayList;
import java.util.List;

public class MyInterestViewAdapter extends RecyclerView.Adapter<MyInterestViewAdapter.ViewHolder> {

    private Clickable clickListener;
    private int selectedItem = -1; // Initially no item is selected
    public static List<String> chosenInterests = new ArrayList<>();



    public MyInterestViewAdapter(List<String> allinterests, Clickable listener) {
        interestsList = allinterests;
        this.clickListener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InterestBinding binding = InterestBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String selInterest = interestsList.get(position);
        if (selInterest != null) {
            holder.bindCard(selInterest);
        } else {
            holder.bindCard("selInterest");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass the clicked item's position to the listener
                selectedItem = holder.getAdapterPosition();
                // Notify adapter that the data set has changed to refresh the RecyclerView
                notifyDataSetChanged();
                clickListener.onClick(selInterest);
                Log.i("Ash3",holder.itemView.findViewById(R.id.editInterest).toString());
                holder.itemView.setBackgroundColor(Color.GREEN);

            }

        });

        // Change background color based on the selected item
        if (position == selectedItem) {
            Log.i("Ash3",holder.itemView.toString());
            holder.itemView.setBackgroundColor(Color.GREEN); // Set selected item background color to green
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT); // Set other item background color to transparent
        }

    }

    @Override
    public int getItemCount() {
        return interestsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private InterestBinding binding;
        private Clickable clickListener;

        public ViewHolder(@NonNull InterestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("ResourceAsColor")
        public void bindCard(String interest) {

//            if (background instanceof GradientDrawable) {
//                binding.editInterest.setBackgroundResource(R.drawable.ic_launcher_background);
//            } else {
//                binding.editInterest.setBackgroundResource(R.drawable.gradient_with_stroke);
//            }

//            binding.editInterest.setBackgroundResource(R.drawable.ic_launcher_background);

            binding.editInterest.setText(interest);

            binding.editInterest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Drawable background = binding.editInterest.getBackground();
                    Log.i("Ash4",chosenInterests.toString());
                    if (background instanceof GradientDrawable) {
                        if (chosenInterests.size() > 2){
                            Toast.makeText(itemView.getContext(), "Please Select Only Upto 10", Toast.LENGTH_LONG).show();
                            return;
                        }
                        chosenInterests.add(interest);
                        binding.editInterest.setBackgroundResource(R.drawable.lime_selected);
                    } else {
//                        chosenInterests.remove(interest);
                        binding.editInterest.setBackgroundResource(R.drawable.gradient);
                        chosenInterests.remove(interest);
                    }
                }
            });
        }
    }
}
