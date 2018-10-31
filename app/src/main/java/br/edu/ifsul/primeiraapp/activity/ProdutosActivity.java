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
import java.util.List;

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
                //notepad.pw/detalheproduto tem essa classe
              Intent intent = new Intent(ProdutosActivity.this, DetalheProdutoActivity.class);
              getIntent().putExtra("produto", produtos.get(position));
              startActivity(intent);
            }
        });
    /*    ListView lvProdutos = findViewById(R.id.lvProdutos);
        List<Produto> produtos= new ArrayList<>();
        Produto produto = new Produto();
        produto.setNome("Meu produto");
        produto.setQuantidade(100);
        produtos.add(produto);
        lvProdutos.setAdapter(new ProdutosAdapter(this, produtos));
*/
        produtos = new ArrayList<>();
       // FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference();

        myRef= AppSetup.getInstance();
        // Read from the database
        myRef.child("produtos").addValueEventListener(new ValueEventListener() {

            public static final String TAG ="ProdutosActivity" ;



            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<List<Produto>> type = new GenericTypeIndicator<List<Produto>>(){};

                produtos = dataSnapshot.getValue(type);
                produtos.remove(null);
                Log.d(TAG, "Nome do produto: " + produtos);
                atualizarView();
            }



            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "erro, não deu pra ler", error.toException());
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
        lvProdutos.setAdapter(new ProdutosAdapter(this, produtos));
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

        }
        // Handle navigation view item clicks here.


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
