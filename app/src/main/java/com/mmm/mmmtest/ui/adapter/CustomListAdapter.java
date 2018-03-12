package com.mmm.mmmtest.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mmm.mmmtest.R;

import java.util.List;
import java.util.Map;

public class CustomListAdapter extends SimpleAdapter {

    Context mContext;
    List<? extends Map<String, ?>> items;
    String[] keys;

    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public CustomListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        items = data;
        keys = from;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentRow = super.getView(position, convertView, parent);
        //TextView tvName = (TextView)currentRow.findViewById(R.id.itemTv1);
        //TextView tvPrice = (TextView)currentRow.findViewById(R.id.itemTv1);
        //tvName.setText(items.get(position).get(keys[position]).toString());
        ImageView iv = (ImageView)currentRow.findViewById(R.id.itemImg);
        iv.setImageResource(R.drawable.bear_logo);
        return currentRow;
    }
}
