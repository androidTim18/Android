package android.projekat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class Search extends Fragment implements AdapterView.OnItemClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search, container, false);

        ListView listSearch = rootView.findViewById(R.id.listSearch);
        AdAdapter adapter = new AdAdapter(getActivity());
        Ad ad1 = new Ad();
        ad1.name = "Primer1";
        ad1.breed = "Primer1";
        ad1.species = "Primer1";
        ad1.birthday = "01/01/2019";
        ad1.photo = getResources().getDrawable(R.drawable.icon_paw);
        adapter.addAd(ad1);
        listSearch.setAdapter(adapter);
        listSearch.setOnItemClickListener(this);

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
        intent.putExtra("availabile", ad.availabile);
        intent.putExtra("info", ad.info);
        intent.putExtra("dateAdded", ad.dateAdded);
        intent.putExtra("favorite", ad.favorite);
        intent.putExtra("owner", ad.owner);
        intent.putExtra("location", ad.location);

        intent.putExtra("position", position);
        startActivity(intent);
    }
}
