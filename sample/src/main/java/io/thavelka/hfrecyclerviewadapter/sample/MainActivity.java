package io.thavelka.hfrecyclerviewadapter.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExampleAdapter adapter;
    private List<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        Button addItemButton = findViewById(R.id.btn_add_item);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.add(new Item("List item"));
                adapter.refresh();
            }
        });

        adapter = new ExampleAdapter(this, items);
        recyclerView.setAdapter(adapter);

        // Add some headers
        View header1 = getLayoutInflater().inflate(R.layout.header1, recyclerView, false);
        View header2 = getLayoutInflater().inflate(R.layout.header2, recyclerView, false);
        adapter.addHeaderView(header1);
        adapter.addHeaderView(header2);

        // Add footer and set as empty view
        View emptyView = getLayoutInflater().inflate(R.layout.footer_empty, recyclerView, false);
        adapter.addFooterView(emptyView);
        adapter.setEmptyView(emptyView);
    }
}
