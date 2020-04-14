package com.example.aasystem.company.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aasystem.R;
import com.example.aasystem.company.model.PendingUserModel;

import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.ViewHolder>
{
    private List<PendingUserModel> usersList;
    private LayoutInflater mInflater;
     static ClickListener clickListener;
    Context context;

    // data is passed into the constructor
    public PendingAdapter(Context context, List<PendingUserModel> data, ClickListener listener)
    {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.clickListener = listener;
        this.usersList = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public PendingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.pending_user_item, parent, false);
        return new PendingAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(PendingAdapter.ViewHolder holder, int position)
    {
        final PendingUserModel model = usersList.get(position);

        holder.name.setText("" + model.getName());
        holder.email.setText("" + model.getEmail());
        holder.image.setImageResource(R.drawable.default_avatar);
    }

    @Override
    public int getItemCount()
    {
        return usersList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
        LinearLayout userLayout;
        TextView name, email;
        CircleImageView image;

        ViewHolder(View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.pending_name);
            email = itemView.findViewById(R.id.pending_email);
            image = itemView.findViewById(R.id.pending_image);
            itemView.setOnClickListener(this);
            userLayout = itemView.findViewById(R.id.pending_layout);
        }

            @Override
            public void onClick(View v) {
                clickListener.onItemClick(usersList.get(getAdapterPosition()));
            }

    }

    public void setOnItemClickListener(ClickListener clickListener) {
        PendingAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(PendingUserModel model);
    }

    PendingUserModel getItem(int id)
    {
        return usersList.get(id);
    }
}
