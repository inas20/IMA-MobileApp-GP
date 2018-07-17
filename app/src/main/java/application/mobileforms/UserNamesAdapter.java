package application.mobileforms;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inas on 09-Mar-18.
 */

public class UserNamesAdapter extends RecyclerView.Adapter<UserNamesAdapter.ViewHolder> {



    private ArrayList<Users> usersList;

    public UserNamesAdapter(ArrayList<Users> usersList) {
        this.usersList = usersList;


    }

    @Override
    public UserNamesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usersnames, parent, false);
        return new UserNamesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserNamesAdapter.ViewHolder holder, int position) {
        holder.name.setText(usersList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        private TextView name;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            name = (TextView) mView.findViewById(R.id.patient_name);

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