package com.mmm.mmmtest.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mmm.mmmtest.R;
import com.mmm.mmmtest.data_access.dao.ProductDao;
import com.mmm.mmmtest.data_access.entity.Product;

public class ListItemActivity extends AppCompatActivity {

    TextView nameTextView;
    TextView priceTextView;
    FloatingActionButton deleteFab;

    ProductDao productDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        nameTextView = (TextView)findViewById(R.id.tvName);
        priceTextView = (TextView)findViewById(R.id.tvPrice);

        deleteFab = (FloatingActionButton)findViewById(R.id.fabDelete);

        productDao = new ProductDao(this);

        final Product product = productDao.find(getIntent().getExtras().get("itemId"));

        nameTextView.setText(product.getName());
        priceTextView.setText(Double.toString(product.getPrice()));

        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productDao.delete(product.getId());
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        if(productDao != null)
            productDao.close();
        super.onDestroy();
    }
}
