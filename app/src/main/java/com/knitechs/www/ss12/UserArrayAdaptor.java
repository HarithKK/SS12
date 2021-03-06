package com.knitechs.www.ss12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koli on 7/30/2016.
 */
public class UserArrayAdaptor extends ArrayAdapter {

    private List<UserDetails> userDetailsList = new ArrayList<>();

    /**
     * list view item
     * @param context context
     * @param resource user view box
     */
    public UserArrayAdaptor(Context context, int resource) {
        super(context, resource);
    }

    public void add(UserDetails userDetails) {
        userDetailsList.add(userDetails);
        super.add(userDetails);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.user_view_box, parent, false);
        }

        ImageView img = (ImageView) row.findViewById(R.id.icon_user);
        TextView name = (TextView)row.findViewById(R.id.txtusername);
        TextView dist = (TextView)row.findViewById(R.id.txtdistance);
        // user details
        UserDetails userDetails = (UserDetails)this.userDetailsList.get(position);
        img.setImageBitmap(userDetails.getImage());
        name.setText(userDetails.getName());
        dist.setText(userDetails.getDiatanceString()+" Km away from you");
        return row;
    }

    //tempory

    public void addTempory(){
        UserDetails usd= new UserDetails(super.getContext(),"Kalana Sampath",R.drawable.img5,"+947167556434","24/3,piliyandala","14");
        UserDetails usd1= new UserDetails(super.getContext(),"Yasas Sri",R.drawable.img6,"+94714522538","Kadawatha","10");

        add(usd);
        add(usd1);
    }

    public UserDetails getItem(int index){
        if(userDetailsList.size()>0){
            return userDetailsList.get(index);
        }else{
            return null;
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
