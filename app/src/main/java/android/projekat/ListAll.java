package android.projekat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class ListAll extends Fragment implements AdapterView.OnItemLongClickListener,
        AdapterView.OnItemClickListener {

    public AdAdapter adapter;
    public static AdDbHelper adDbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_list, container, false);

        ListView listAll = rootView.findViewById(R.id.listAll);
        adapter = new AdAdapter(getActivity());

        listAll.setAdapter(adapter);
        listAll.setOnItemClickListener(this);
        listAll.setOnItemLongClickListener(this);

        adDbHelper = new AdDbHelper(getActivity());

        Ad[] ads = adDbHelper.readAds();
        adapter.update(ads);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        Ad[] ads = adDbHelper.readAds();
        adapter.update(ads);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        Ad ad = (Ad) parent.getItemAtPosition(position);

        intent.putExtra("adId", ad.getAdId());

        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        SharedPreferences preferences = this.getActivity().getSharedPreferences("preferences", MODE_PRIVATE);
        String user = new String();
        user = ((SharedPreferences) preferences).getString("user", null);
        if (user.equals("administrator")) {
            Ad ad = (Ad) parent.getItemAtPosition(position);
            adDbHelper.deleteAd(ad.adId);
        }
        return false;
    }
}
