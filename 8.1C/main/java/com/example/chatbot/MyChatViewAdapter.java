package com.example.chatbot;

import static com.example.chatbot.MainActivity.allChats;
import static com.example.chatbot.MainActivity.displayChatValue;
import static com.example.chatbot.MainActivity.isBotTurn;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyChatViewAdapter extends RecyclerView.Adapter<MyChatViewAdapter.ViewHolder> {

    private List<Chat> chatList;

    public MyChatViewAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (displayChatValue){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat, parent, false);
        }
        else{
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sender_icon, parent, false);
        }

        return new ViewHolder(view);
    }





    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Chat currentChat = allChats.get(position);
        if (currentChat.isIcon){
            if (isBotTurn){
                holder.senderChatIcon.setImageResource(R.drawable.sparkle);
            }
            else{
                holder.senderChatIcon.setImageResource(R.drawable.user_icon);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.senderIconCardID.getLayoutParams();
                layoutParams.leftMargin = (int) (283 * holder.itemView.getContext().getResources().getDisplayMetrics().density);
                holder.senderIconCardID.setLayoutParams(layoutParams);

            }
            isBotTurn = !isBotTurn;
        }
        else{
            holder.chatValue.setText(currentChat.value);
            if(isBotTurn){
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.charCardID.getLayoutParams();
                layoutParams.leftMargin = (int) (127 * holder.itemView.getContext().getResources().getDisplayMetrics().density);
                holder.charCardID.setLayoutParams(layoutParams);

//                FrameLayout.LayoutParams layoutParams1 = (FrameLayout.LayoutParams) holder.charCardID.getLayoutParams();
//                layoutParams1.gravity = Gravity.END;
//                holder.charCardID.setLayoutParams(layoutParams1);
            }

        }

        displayChatValue = !displayChatValue;



    }

    @Override
    public int getItemCount() {
        return allChats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView chatValue;
        ImageView senderChatIcon;
        CardView charCardID;
        CardView senderIconCardID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            if (displayChatValue){
                chatValue = itemView.findViewById(R.id.chatValue);
                charCardID = itemView.findViewById(R.id.charCardID);
            }
            else{
                senderChatIcon = itemView.findViewById(R.id.senderChatIcon);
                senderIconCardID = itemView.findViewById(R.id.senderIconCardID);
            }


        }
    }
}

