package com.example.aasystem.company.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aasystem.FingerPrintModel;
import com.example.aasystem.R;
import com.example.aasystem.company.activities.UserRecordsActivity;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>
{
    private List<FingerPrintModel> usersList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;

    // data is passed into the constructor
    public UsersAdapter(Context context, List<FingerPrintModel> data)
    {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.usersList = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        final FingerPrintModel model = usersList.get(position);

        holder.nameText.setText("" + model.getName());
        holder.attendText.setText("" + model.getAttend());
        holder.leftText.setText("" + model.getLeave());
        holder.checkText.setText("" + model.getStatus());
        holder.dateText.setText("" + model.getDate());

        holder.name.setText("Name");
        holder.date.setText("Date");
        holder.attend.setText("Attend at");
        holder.left.setText("Leave at");
        holder.check.setText("Attendance check");

        holder.userLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(context, UserRecordsActivity.class);
                intent.putExtra("user_id", model.getId());
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
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
        TextView nameText, attendText, leftText, checkText, dateText;
        TextView name, attend, left, check, date;

        ViewHolder(View itemView)
        {
            super(itemView);
            nameText = itemView.findViewById(R.id.tv_name);
            attendText = itemView.findViewById(R.id.tv_attendance);
            leftText = itemView.findViewById(R.id.tv_left);
            checkText = itemView.findViewById(R.id.tv_check);
            checkText = itemView.findViewById(R.id.tv_check);
            dateText = itemView.findViewById(R.id.tv_date);
            userLayout = itemView.findViewById(R.id.user_layout);

            name = itemView.findViewById(R.id.name_textview);
            attend = itemView.findViewById(R.id.attend_textview);
            left = itemView.findViewById(R.id.left_textview);
            check = itemView.findViewById(R.id.check_textview);
            date = itemView.findViewById(R.id.date_textview);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    FingerPrintModel getItem(int id)
    {
        return usersList.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}