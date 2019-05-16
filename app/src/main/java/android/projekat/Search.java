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

import static android.projekat.ListAll.adDbHelper;

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
                if (rbBreed.isChecked()){
                    Ad[] ads = adDbHelper.searchBreed(s.toString());
                    adapter.update(ads);
                }
                else if (rbSpecies.isChecked())
                {
                    Ad[] ads = adDbHelper.searchSpecies(s.toString());
                    adapter.update(ads);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        Ad ad = (Ad) parent.getItemAtPosition(position);

        intent.putExtra("adId", ad.getAdId());
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
