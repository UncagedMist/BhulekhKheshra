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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import tbc.uncagedmist.bhulekhkheshra.Adapter.MyAdapter;
import tbc.uncagedmist.bhulekhkheshra.Database.MyDatabase;
import tbc.uncagedmist.bhulekhkheshra.MainActivity;
import tbc.uncagedmist.bhulekhkheshra.Model.Item;
import tbc.uncagedmist.bhulekhkheshra.R;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Item> itemArrayList = new ArrayList<>();
    EditText edtState;

    Context context;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myFragment = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = myFragment.findViewById(R.id.recyclerState);
        edtState = myFragment.findViewById(R.id.edtState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        edtState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Item> itemList = new ArrayList<>();

                String edtStateName = edtState.getText().toString().trim();

                for (Item stateName : itemArrayList)   {
                    if (stateName.getStateName().toLowerCase().contains(edtStateName.toLowerCase()))  {
                        Cursor cursor = new MyDatabase(context).getStateByNames(
                                edtStateName.toLowerCase());

                        while (cursor.moveToNext()) {
                            Item item = new Item(
                                    cursor.getString(0),
                                    cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getString(3),
                                    cursor.getString(4)
                            );
                            itemList.add(item);
                        }
                    }
                }
                MyAdapter adapter = new MyAdapter(context,itemList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        getStateDetails();

        return myFragment;
    }

    private void getStateDetails() {
        Cursor cursor = new MyDatabase(context).getAllStateData();

        while (cursor.moveToNext()) {
            Item item = new Item(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            itemArrayList.add(item);
        }

        MyAdapter adapter = new MyAdapter(context,itemArrayList);
        recyclerView.setAdapter(adapter);
    }
}