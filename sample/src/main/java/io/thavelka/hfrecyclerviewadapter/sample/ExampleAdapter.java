package io.thavelka.hfrecyclerviewadapter.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.thavelka.hfrecyclerviewadapter.HFRecyclerViewAdapter;

public class ExampleAdapter extends HFRecyclerViewAdapter {

    private List<Item> items = new ArrayList<>();

    public ExampleAdapter(Context context, List<Item> items) {
        super(context);
        this.items = items;
        refresh();
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).setItem(item);
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private Item item;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.list_item_text);
            ImageButton closeButton = itemView.findViewById(R.id.list_item_btn_delete);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item != null) items.remove(item);
                    refresh();
                }
            });
        }

        public void setItem(Item item) {
            this.item = item;
            textView.setText(item.getText());
        }
    }
}
