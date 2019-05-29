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
        if (ad.available = true){
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

    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDisplayAds = (ArrayList<Ad>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Ad> FilteredArrList = new ArrayList<Ad>();

                if (mAds == null) {
                    mAds = new ArrayList<Ad>(mDisplayAds); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mAds.size();
                    results.values = mAds;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mAds.size(); i++) {
                        String data = mAds.get(i).breed;
                        if (data.toLowerCase().startsWith(constraint.toString())) {


                            FilteredArrList.add(new Ad(mAds.get(i).species,
                                    mAds.get(i).breed, mAds.get(i).name, mAds.get(i).birthday, mAds.get(i).sex,
                                    mAds.get(i).location, mAds.get(i).owner, mAds.get(i).info, mAds.get(i).price,
                                    mAds.get(i).available, mAds.get(i).favorite, mAds.get(i).photo,
                                    mAds.get(i).adId, mAds.get(i).dateAdded ));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
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
