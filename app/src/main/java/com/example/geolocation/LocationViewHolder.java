package com.example.geolocation;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class LocationViewHolder extends RecyclerView.ViewHolder {
    public TextView addrLabel;
    public TextView latLabel;
    public LinearLayout noteItemLayout;
    public TextView longLabel;

    public CardView cardView;

    public LocationViewHolder(View itemView) {
//        super(itemView);
        super(itemView);

        addrLabel = itemView.findViewById(R.id.addrLabel);
        noteItemLayout = itemView.findViewById(R.id.noteItemLayout);
        latLabel = itemView.findViewById(R.id.latValue);
        longLabel = itemView.findViewById(R.id.longValue);

        cardView = itemView.findViewById(R.id.mainContainer);
    }

}
