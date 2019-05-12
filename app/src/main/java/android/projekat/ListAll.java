package android.projekat;

import android.content.Intent;
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

public class ListAll extends Fragment implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_list, container, false);

        ListView listAll = rootView.findViewById(R.id.listAll);
        AdAdapter adapter = new AdAdapter(getActivity());
        Ad ad1 = new Ad();
        ad1.name = "Primer1";
        ad1.breed = "Primer1";
        ad1.species = "Primer1";
        ad1.birthday = "01/01/2019";
        ad1.photo = getResources().getDrawable(R.drawable.icon_paw);
        adapter.addAd(ad1);
        listAll.setAdapter(adapter);
        listAll.setOnItemClickListener(this);
        listAll.setOnItemLongClickListener(this);

        Intent i = getActivity().getIntent();
        if(i.hasExtra("species")){
            Ad ad = new Ad();
            ad.name = i.getStringExtra("name");
            ad.breed = i.getStringExtra("breed");
            ad.species = i.getStringExtra("species");
            ad.birthday = i.getStringExtra("birthday");
            ad.sex = i.getStringExtra("sex");
            ad.location = i.getStringExtra("location");
            ad.owner = i.getStringExtra("owner");
            ad.info = i.getStringExtra("info");
            ad.price = i.getStringExtra("price");
            adapter.addAd(ad);
            adapter.notifyDataSetChanged();
        }
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

        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //if user == admin   -> delete ad
        return false;
    }
}
