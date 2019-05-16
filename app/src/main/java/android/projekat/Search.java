package android.projekat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;

public class Search extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    RadioButton rbSpecies;
    RadioButton rbBreed;
    AdAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.search, container, false);

        ListView listSearch = rootView.findViewById(R.id.listSearch);
        EditText searchFilter = rootView.findViewById(R.id.searchFilter);
        rbSpecies = rootView.findViewById(R.id.rbSpecies);
        rbBreed = rootView.findViewById(R.id.rbBreed);
        adapter = new AdAdapter(getActivity());

        listSearch.setAdapter(adapter);

        listSearch.setOnItemClickListener(this);
        rbBreed.setOnClickListener(this);
        rbSpecies.setOnClickListener(this);

        searchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (rbBreed.isActivated()){
                    adapter.getFilter().filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbSpecies:
                break;
            case R.id.bJoin:
        }
    }
}
