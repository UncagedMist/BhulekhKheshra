package tbc.uncagedmist.bhulekhkheshra.Fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tbc.uncagedmist.bhulekhkheshra.Adapter.MyAdapter;
import tbc.uncagedmist.bhulekhkheshra.Database.MyDatabase;
import tbc.uncagedmist.bhulekhkheshra.MainActivity;
import tbc.uncagedmist.bhulekhkheshra.Model.Item;
import tbc.uncagedmist.bhulekhkheshra.R;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Item> itemArrayList = new ArrayList<>();

    View myFragment;

    Context context;

    private static HomeFragment INSTANCE = null;

    public static HomeFragment getInstance()    {

        if (INSTANCE == null)   {
            INSTANCE = new HomeFragment();
        }
        return INSTANCE;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        recyclerView = myFragment.findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new GridLayoutManager(context,2));

        Cursor cursor = new MyDatabase(context).getAllStateData();

        while (cursor.moveToNext()) {
            Item item = new Item(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            itemArrayList.add(item);
        }

        MyAdapter adapter = new MyAdapter(context,itemArrayList);
        recyclerView.setAdapter(adapter);

        return myFragment;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setQueryHint("Enter State Name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Item> itemList = new ArrayList<>();

                for (Item stateName : itemArrayList)   {
                    if (stateName.getStateName().toLowerCase().contains(newText.toLowerCase()))  {
                        Cursor cursor = new MyDatabase(context).getStateByNames(
                                newText.toLowerCase());

                        while (cursor.moveToNext()) {
                            Item item = new Item(
                                    cursor.getString(0),
                                    cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getString(3)
                            );
                            itemList.add(item);
                        }
                    }
                }
                MyAdapter adapter = new MyAdapter(context,itemList);
                recyclerView.setAdapter(adapter);

                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}