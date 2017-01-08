package me.mathusan.parkthevalley;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Dan on 08/01/2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private Context mContext;
    private List<User> userList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, distance, name, phone, money;
        public ImageView nameIcon, phoneIcon, moneyIcon;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            distance = (TextView) view.findViewById(R.id.distance);

            name = (TextView) view.findViewById(R.id.name);
            nameIcon = (ImageView) view.findViewById(R.id.nameIcon);

            money = (TextView) view.findViewById(R.id.money);
            moneyIcon = (ImageView) view.findViewById(R.id.moneyIcon);

            phone = (TextView) view.findViewById(R.id.phone);
            phoneIcon = (ImageView) view.findViewById(R.id.phoneIcon);
        }
    }


    public CardAdapter(Context mContext, List<User> albumList) {
        this.mContext = mContext;
        this.userList = userList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        User user = userList.get(position);
        holder.name.setText(user.getName());
        holder.phone.setText(user.getPhone());
        holder.money.setText(Double.toString(user.getPrice()));
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }
}