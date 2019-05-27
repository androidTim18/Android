package android.projekat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;
import static android.projekat.ListAll.adDbHelper;

public class ListFav extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    AdAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fav_list, container, false);

        ListView listFav = rootView.findViewById(R.id.listFav);
        adapter = new AdAdapter(getActivity());
        listFav.setAdapter(adapter);

        listFav.setOnItemClickListener(this);

        Ad[] ads = adDbHelper.readFavorites();
        adapter.update(ads);

        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();

        Ad[] ads = adDbHelper.readFavorites();
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

        if (((SharedPreferences) preferences).getString("userId", null) == "administrator") {
            Ad ad = (Ad) parent.getItemAtPosition(position);
            adDbHelper.deleteAd(ad.adId);
        }
        return false;
    }
}