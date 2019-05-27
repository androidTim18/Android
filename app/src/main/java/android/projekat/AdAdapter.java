package android.projekat;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class AdAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Ad> mAds;
    private ArrayList<Ad> mDisplayAds;

    public AdAdapter(Context context) {
        mContext = context;
        mAds = new ArrayList<Ad>();
    }

    public void addAd(Ad ad) {
        mAds.add(ad);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mAds.size();
    }

    @Override
    public Object getItem(int position) {
        Object rv = null;
        try {
            rv = mAds.get(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return rv;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(Ad[] ads) {
        mAds.clear();
        if(ads != null) {
            for(Ad ad : ads) {
                mAds.add(ad);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_element, null);
            ViewHolder holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.element_img);
            holder.name = (TextView) view.findViewById(R.id.element_name);
            holder.species = (TextView) view.findViewById(R.id.element_species);
            holder.breed = (TextView) view.findViewById(R.id.element_breed);
            holder.date = (TextView) view.findViewById(R.id.element_date);
            holder.unavailable = (TextView) view.findViewById(R.id.el_unavailable);
            view.setTag(holder);
        }

        Ad ad = (Ad) getItem(position);
        ViewHolder holder = (ViewHolder) view.getTag();
        if (ad.available == 1){
            holder.image.setImageDrawable(ad.photo);
            holder.image.setVisibility(View.VISIBLE);
            holder.unavailable.setVisibility(View.GONE);
        }
        else {
            holder.image.setVisibility(View.GONE);
            holder.unavailable.setVisibility(View.VISIBLE);
        }
        holder.name.setText(ad.name);
        holder.species.setText(ad.species);
        holder.breed.setText(ad.breed);
        holder.date.setText(ad.birthday);

        return view;
    }
    private class ViewHolder {
        public ImageView image = null;
        public TextView name = null;
        public TextView species = null;
        public TextView breed = null;
        public TextView date = null;
        public TextView unavailable = null;
    }
}
