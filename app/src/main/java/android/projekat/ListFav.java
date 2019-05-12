package android.projekat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ListFav extends Fragment implements AdapterView.OnItemClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fav_list, container, false);

        ListView listFav = rootView.findViewById(R.id.listFav);
        AdAdapter adapter = new AdAdapter(getActivity());
        listFav.setAdapter(adapter);

        listFav.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        Ad ad = (Ad) parent.getItemAtPosition(position);
        intent.putExtra("species", ad.species);
        intent.putExtra("breed", ad.breed);
        intent.putExtra("name", ad.name);
        intent.putExtra("birthday", ad.birthday);
        intent.putExtra("sex", ad.sex);
        intent.putExtra("price",ad.price);
        intent.putExtra("available", ad.available);
        intent.putExtra("info", ad.info);
        intent.putExtra("dateAdded", ad.dateAdded);
        intent.putExtra("favorite", ad.favorite);
        intent.putExtra("owner", ad.owner);
        intent.putExtra("location", ad.location);
        startActivity(intent);
    }
}