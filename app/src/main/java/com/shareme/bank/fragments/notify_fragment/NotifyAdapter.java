package com.shareme.bank.fragments.notify_fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shareme.bank.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.notifyViewHolder> {


    private final Context context;
    private List<Datum> notifyModelList;
    private onItemClickListner onItemClickedListner;

    public NotifyAdapter(Context context) {
        this.context = context;
    }


    public void sendDataToAdapter(List<Datum> notifyModelList) {
        this.notifyModelList = notifyModelList;
    }


    public interface onItemClickListner {
        void onClick(Datum notificationsModel);//pass your object types.
    }

    public void onItemClickedListner(NotifyAdapter.onItemClickListner onItemClickListner) {
        this.onItemClickedListner = onItemClickListner;
    }


    @NonNull
    @Override
    public notifyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.rv_notify, parent, false);
        return new notifyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull notifyViewHolder holder, int position) {

        Datum notifyModel = notifyModelList.get(position);

        holder.text.setText(notifyModel.getTitle());
        holder.date.setText(notifyModel.getCreatedAt());

        int r = notifyModel.getPivot().getIsRead();
        if (r == 0) {
            Picasso.get().load(R.mipmap.notify).into(holder.img);

        } else {
            Picasso.get().load(R.mipmap.notify2).into(holder.img);

        }

    }

    @Override
    public int getItemCount() {
        return notifyModelList != null ? notifyModelList.size() : 0;
    }




    class notifyViewHolder extends RecyclerView.ViewHolder {

        TextView text , date;
        ImageView img;

        public notifyViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.notify_text);
            date = itemView.findViewById(R.id.notify_date);
            img = itemView.findViewById(R.id.notify_img);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Datum notificationsModel = notifyModelList.get(NotifyAdapter.this.getItemViewType(getAdapterPosition()));
                    onItemClickedListner.onClick(notificationsModel);

                }
            });


        }
    }


}
