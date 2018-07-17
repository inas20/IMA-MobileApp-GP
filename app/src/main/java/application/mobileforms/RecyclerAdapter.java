package application.mobileforms;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
//https://www.youtube.com/watch?v=puyiZKvxBa0

/**
 * Created by Inas on 16-Feb-18.
 */
class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView UsernameField;

    private OnItemClick onItemClick;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        UsernameField = (TextView) itemView.findViewById(R.id.patient_name);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    public void onClick(View v) {
        onItemClick.onClick(v, getAdapterPosition(), false);

    }

    @Override
    public boolean onLongClick(View v) {
        onItemClick.onClick(v, getAdapterPosition(), true);
        return true;
    }
}

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder>

    {
        private List<Users> usersList;
        private Context context;

        public RecyclerAdapter(List<Users> usersList, Context context) {
            this.usersList = usersList;
            this.context = context;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listitem, parent, false);
            return new RecyclerViewHolder(view);

        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {

            holder.UsernameField.setText(usersList.get(position).getName());
            holder.setItemClick(new OnItemClick() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if (isLongClick) {
                        Toast.makeText(context, "Long Click : " + usersList.get(position), Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(context, " " + usersList.get(position), Toast.LENGTH_SHORT).show();

                }
            });

        }

        @Override
        public int getItemCount() {
            return usersList.size();
        }
    }
