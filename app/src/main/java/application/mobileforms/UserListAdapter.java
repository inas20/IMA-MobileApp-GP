package application.mobileforms;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Inas on 08-Feb-18.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {



    public List<UserAddInformation> usersList;

    public UserListAdapter(List<UserAddInformation> usersList) {
        this.usersList = usersList;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.EmailField.setText(usersList.get(position).getemail());
        holder.PhoneField.setText(usersList.get(position).getphone());
        holder.UsernameField.setText(usersList.get(position).getUsername());
        holder.GenderFields.setText(usersList.get(position).getGender());
        holder.StatusFields.setText(usersList.get(position).getStatus());
        holder.DateFields.setText(usersList.get(position).getbirthdate());
        holder.HistoryFields.setText(usersList.get(position).getHistory());

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        private TextView EmailField;
        public TextView UsernameField;
        public TextView PhoneField;
        public TextView GenderFields;
        public TextView StatusFields;
        public TextView DateFields;
        public TextView HistoryFields;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            EmailField = (TextView) mView.findViewById(R.id.EmailField);
            UsernameField = (TextView) mView.findViewById(R.id.UsernameField);
            PhoneField = (TextView) mView.findViewById(R.id.PhoneField);
            StatusFields=(TextView) mView.findViewById(R.id.StatusField);
            GenderFields=(TextView) mView.findViewById(R.id.GenderField);
            DateFields=(TextView) mView.findViewById(R.id.DateField);
            HistoryFields=(TextView) mView.findViewById(R.id.historyField);
        }

//        public void bind(final UserAddInformation item, final OnItemClickListener listener) {
//
//
//            mView.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//
//                    listener.onItemClick(item);
//
//                }
//
//            });
//        }
    }

}