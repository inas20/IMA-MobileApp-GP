package application.mobileforms;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


/**
 * Created by Inas on 10-Jul-18.
 */

public class DoctorsViewHolder extends RecyclerView.Adapter<DoctorsViewHolder.ViewHolder> {
    Context context;
    List<Doctors> DoctorsList;

    public DoctorsViewHolder(Context context, List<Doctors> doctorsList) {
        this.context = context;
        DoctorsList = doctorsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctors_card_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Doctors doctors = DoctorsList.get(position);
        holder.Doc_Name.setText("Dr" +" " + doctors.getName());
        holder.Doc_Address.setText("Address"+":"+doctors.getAddress());
        String myRate=Float.toString(doctors.getRate());
        holder.Doc_rate.setText("Rate" + " "+ myRate);
        holder.Doc_desc.setText(doctors.getDescription());
       holder.Doc_time.setText("Available Time" + " :"+ doctors.getAvailable_time());
        //Loading image from Glide library.
        Glide.with(context).load(doctors.getImage()).into(holder.imageProfile);

    }
    @Override
    public int getItemCount() {

        return DoctorsList.size();
    }


    /*
        public void setDetails(Context ctx, String name , String description , String Address , String Available_time , String rate , String image)
        {   TextView Doc_Name= (TextView) mView.findViewById(R.id.DocName);
            TextView Doc_Address= (TextView) mView.findViewById(R.id.DocAddress);
            TextView Doc_rate= (TextView) mView.findViewById(R.id.DocRate);
            TextView Doc_desc= (TextView) mView.findViewById(R.id.DocDesc);
            TextView Doc_time= (TextView) mView.findViewById(R.id.DocTime);
            ImageView imageProfile=(ImageView) mView.findViewById(R.id.imageView2);
            Doc_Name.setText(name);
            Doc_Address.setText(Address);
            Doc_rate.setText(rate);
            Doc_desc.setText(description);
            Doc_time.setText(Available_time);
            //Loading image from Glide library.
            Glide.with(ctx).load(image).into(imageProfile);

        }
        */
    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Doc_Name;
        public TextView Doc_Address;
        public TextView Doc_rate;
        public TextView  Doc_desc;
        public TextView Doc_time;
        public ImageView imageProfile;

        public ViewHolder(View itemView) {
            super(itemView);

             Doc_Name= (TextView)itemView.findViewById(R.id.DocName);
             Doc_Address= (TextView) itemView.findViewById(R.id.DocAddress);
             Doc_rate= (TextView) itemView.findViewById(R.id.DocRate);
            Doc_desc= (TextView) itemView.findViewById(R.id.DocDesc);
            Doc_time= (TextView) itemView.findViewById(R.id.DocTime);
            imageProfile=(ImageView) itemView.findViewById(R.id.imageView2);
        }
    }
}
