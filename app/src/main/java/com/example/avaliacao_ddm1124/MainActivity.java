package com.example.avaliacao_ddm1124;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference DB = FirebaseDatabase.getInstance().getReference();
    private EditText txtMesa;
    private EditText txtItem;
    private EditText txtProduto;
    private Button btnInserir;
    private ListView lista;
    private ItensAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMesa = findViewById(R.id.txtMesa);
        txtItem = findViewById(R.id.txtItem);
        txtProduto = findViewById(R.id.txtProduto);
        btnInserir = findViewById(R.id.btnInserir);
        lista = findViewById(R.id.lista);

        btnInserir.setOnClickListener(new btnInserirListener());

        DatabaseReference restaurante = DB.child("restaurante");
        FirebaseListOptions<Item> options = new FirebaseListOptions.Builder<Item>().setLayout(R.layout.item_lista).setQuery(restaurante, Item.class).setLifecycleOwner(this).build();
        adapter = new ItensAdapter(options);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new ListClickListener());
        lista.setOnItemLongClickListener(new ListClickListener());


    }


    private class ItensAdapter extends FirebaseListAdapter<Item> {
        public ItensAdapter(FirebaseListOptions options) {
            super(options);
        }

        @Override
        protected void populateView(View v, Item item, int position) {
            TextView lblMesa = v.findViewById(R.id.lblMesa);
            TextView lblItem = v.findViewById(R.id.lblItem);
            TextView lblProduto = v.findViewById(R.id.lblProduto);
            TextView lblAtendido = v.findViewById(R.id.lblAtendido);
            lblMesa.setText(item.getMesa());
            lblItem.setText(item.getItem());
            lblProduto.setText(item.getProduto());
            if (item.isAtendido()) {
                lblAtendido.setText("SIM");
                lblAtendido.setTextColor(Color.GREEN);
            } else {
                lblAtendido.setText("NÃO");
                lblAtendido.setTextColor(Color.RED);
            }
        }
    }

    private class btnInserirListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String mesa = txtMesa.getText().toString();
            String i = txtItem.getText().toString();
            String produto = txtProduto.getText().toString();
            DatabaseReference restaurante = DB.child("restaurante");
            String chave = restaurante.push().getKey();
            Item item = new Item(chave, mesa, i, produto);
            restaurante.child(chave).setValue(item);
        }
    }

    private class ListClickListener implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
        DatabaseReference restaurante = DB.child("restaurante");
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Item item = adapter.getItem(i);
            item.setAtendido(true);
            restaurante.child(item.getChave()).setValue(item);
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            Item item = adapter.getItem(i);
            restaurante.child(item.getChave()).setValue(null);
            return false;
        }
    }
}