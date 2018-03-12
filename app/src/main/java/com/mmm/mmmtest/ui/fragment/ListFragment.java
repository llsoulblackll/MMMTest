package com.mmm.mmmtest.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mmm.mmmtest.R;
import com.mmm.mmmtest.data_access.dao.ProductDao;
import com.mmm.mmmtest.data_access.entity.Product;
import com.mmm.mmmtest.ui.activity.ListItemActivity;
import com.mmm.mmmtest.ui.adapter.CustomListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListFragment extends Fragment implements AdapterView.OnItemClickListener {

    String[] keys = {"id", "name", "price", "image_path"};
    int[] resources = {R.id.itemId, R.id.itemTv1, R.id.itemTv2, R.id.itemImg};

    ListView lv;

    ProductDao productDao;

    public ListFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.fragment_list, container, false);
        lv = (ListView)fragmentLayout.findViewById(R.id.lvItems);

        fillList();

        //adapter.notifyDataSetChanged();

        //getActivity().registerForContextMenu(lv);

        return fragmentLayout;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(id);
        System.out.println(view.getId());
        System.out.println(id == view.getId());

        int itemId = Integer.parseInt(((TextView)view.findViewById(R.id.itemId)).getText().toString());

        Intent i = new Intent(getActivity(), ListItemActivity.class);
        i.putExtra("itemId", itemId);

        startActivity(i);
    }

    @Override
    public void onDestroy() {
        if(productDao != null)
            productDao.close();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        fillList();
        super.onResume();
    }

    void fillList(){
        if(lv != null) {
            List<Map<String, String>> products = new ArrayList<>();
            productDao = new ProductDao(getActivity().getApplicationContext());
            Map<String, String> proMap = null;

            for (Product pro : productDao.findAll()) {
                proMap = new HashMap<>();
                proMap.put("id", Integer.toString(pro.getId()));
                proMap.put("name", pro.getName());
                proMap.put("price", "S/. " + Double.toString(pro.getPrice()));
                proMap.put("image_path", pro.getImagePath());
                products.add(proMap);
            }

            Object act = getActivity();//CORRECTLY RETURN PARENT ACTIVITY
            SimpleAdapter adapter = new CustomListAdapter(getActivity(), products, R.layout.list_item, keys, resources);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(this);
        }
    }
}
