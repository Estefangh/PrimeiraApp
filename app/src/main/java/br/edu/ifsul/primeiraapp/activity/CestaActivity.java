package br.edu.ifsul.primeiraapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import br.edu.ifsul.primeiraapp.R;
import br.edu.ifsul.primeiraapp.adapter.CestaAdapter;
import br.edu.ifsul.primeiraapp.model.Item_pedido;
import br.edu.ifsul.primeiraapp.model.Pedido;
import br.edu.ifsul.primeiraapp.setup.AppSetup;

public class CestaActivity extends AppCompatActivity {


    public ListView lvCesta;
    private DatabaseReference myRef;

    private static final String TAG = "cestaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesta);

        lvCesta = findViewById(R.id.lvListaCesta);

    }

    @Override
    protected void onResume() {
        super.onResume();
        lvCesta.setAdapter(new CestaAdapter(this, AppSetup.itensPedido));
    }


}
