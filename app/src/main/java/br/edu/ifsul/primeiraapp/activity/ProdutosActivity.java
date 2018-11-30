package br.edu.ifsul.primeiraapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import br.edu.ifsul.primeiraapp.R;
import br.edu.ifsul.primeiraapp.adapter.ProdutosAdapter;
import br.edu.ifsul.primeiraapp.barcode.BarcodeCaptureActivity;
import br.edu.ifsul.primeiraapp.model.Cliente;
import br.edu.ifsul.primeiraapp.model.Produto;
import br.edu.ifsul.primeiraapp.setup.AppSetup;

public class ProdutosActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final int RC_BARCODE_CAPTURE = 0;
    private static final String TAG = "produtosActivity";
    private List<Produto> produtos;
    public ListView lvProdutos;
    private static DatabaseReference myRef;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        lvProdutos = findViewById(R.id.lvProdutos);
        lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              Intent intent = new Intent(ProdutosActivity.this, DetalheProdutoActivity.class);
              intent.putExtra("position", position);
              startActivity(intent);
            }
        });

        produtos = new ArrayList<>();
       // FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef a= database.getReference();

        //busca a referência para o database usando Singleton
        DatabaseReference myRef = AppSetup.getInstance();
        // Lê do database
        myRef.child("produtos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //um log para ver como os dados são formatados pelo Firebase
                //Log.d(TAG, "Value is: \n" + dataSnapshot);
                if(dataSnapshot.getValue() != null){
                    //cria um tipo genérico do tipo Map
                    GenericTypeIndicator<Map<String, Produto>> type = new GenericTypeIndicator<Map<String, Produto>>() {};
                    List<String> keysProdutos = new ArrayList<>(dataSnapshot.getValue(type).keySet());
                    //converte o Map para List e armazena a lista das chaves dos produtos no Firebase (o UUID do produto no Firebase)
                    AppSetup.produtos = new ArrayList<>(dataSnapshot.getValue(type).values());


                    Log.d(TAG, "Produtos no Firebase (sem ordenação): \n" + AppSetup.produtos);
                    //insere as keys dos produtos na lista de produtos da app. Isto serve para controlar o estoque.
                    for (int i = 0; i < AppSetup.produtos.size(); i++) {
                        AppSetup.produtos.get(i).setKey(keysProdutos.get(i));
                    }
                    //ordena a List pelo nome do produto
                    Collections.sort(AppSetup.produtos, new Comparator<Produto>() {
                        @Override
                        public int compare(Produto o1, Produto o2) {
                            if (o1.getNome().compareToIgnoreCase(o2.getNome()) < 0) return -1;
                            else if (o1.getNome().compareToIgnoreCase(o2.getNome()) > 0) return +1;
                            else return 0;
                        }
                    });
                    //Log.d(TAG, "Produtos ordenados pelo nome com a key do Firebase: \n" + AppSetup.produtos);
                    atualizarView();
                }else{
                    Toast.makeText(ProdutosActivity.this, R.string.toast_nao_ha_dados_cadastrados, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public void onBackPressed() {

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(!AppSetup.cesta.isEmpty()){
                alertDialogSimNao("Atenção","Se você sair os dados serão perdidos. Deseja sair mesmo assim?");
            }
            else{
                finish();
            }
        }

    }

    private void alertDialogSimNao(String titulo, String mensagem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //add the title and text
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        //add the buttons
        builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ProdutosActivity.this, "Operação cancelada.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    private void atualizarView() {
        lvProdutos.setAdapter(new ProdutosAdapter(this, AppSetup.produtos));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_activity_produtos, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuitem_pesquisar).getActionView();
        searchView.setQueryHint("Digite o nome do produto:");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Produto> produtosFilter = new ArrayList<>();


                for (Produto produto : produtos) {
                    if (produto.getNome().contains(newText)){
                        produtosFilter.add(produto);
                    }
                }

                lvProdutos.setAdapter(new ProdutosAdapter(ProdutosActivity.this, produtosFilter));
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuitem_barcode: {
                Intent intent = new Intent(this, BarcodeCaptureActivity.class);
                intent.putExtra(BarcodeCaptureActivity.AutoFocus, true); //liga o autofocus
                intent.putExtra(BarcodeCaptureActivity.UseFlash, false); //liga a lanterna
                startActivityForResult(intent, RC_BARCODE_CAPTURE);
                break;
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    //Toast.makeText(this, barcode.displayValue, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                    //localiza o produto na lista (ou não)
                    boolean flag = true;
                    for (Produto produto : produtos){
                        if(String.valueOf(produto.getCodigoDeBarras()).equals(barcode.displayValue)){
                            flag = false;
                            Intent intent = new Intent(ProdutosActivity.this, DetalheProdutoActivity.class);
                            intent.putExtra("produto", produto);
                            startActivity(intent);
                            break;
                        }
                    }
                    if(flag){
                        Toast.makeText(this, "Produto não cadastrado.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, R.string.barcode_failure, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                Toast.makeText(this, String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)), Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_carrinho:{
                if (AppSetup.cesta.isEmpty()){
                    Toast.makeText(this, "O carrinho está vazio", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(this, CestaActivity.class));
                }
                break;
            }
            case R.id.nav_clientes:{
                startActivity(new Intent(this, ClienteActivity.class));
                break;
            }
            case R.id.nav_produto_adminstracao:{
                startActivity(new Intent(this, ProdutoAdminActivity.class));
                break;
            }
          /*  case R.id.nav_cliente_administracao:{
                startActivity(new Intent(this, ClienteAdminActivity.class));
                break;
            }*/

            case R.id.nav_sair:{
                if (!AppSetup.cesta.isEmpty()) {
                    alertDialogSimNao(getString(R.string.titulo_alertDialogSimNao), getString(R.string.mensagem_se_voce_sair));
                } else {
                    closeDrawer();
                    finish();
                }
                break;
            }

        }
        // Handle navigation view item clicks here.
      //  drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        closeDrawer();
        return true;
    }

    private void closeDrawer() {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

    }
}
