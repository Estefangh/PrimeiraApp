package br.edu.ifsul.primeiraapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

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
import br.edu.ifsul.primeiraapp.model.Cliente;
import br.edu.ifsul.primeiraapp.model.Produto;
import br.edu.ifsul.primeiraapp.setup.AppSetup;

public class ProdutosActivity extends AppCompatActivity {

    private List<Produto> produtos;
    public ListView lvProdutos;
    private static DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

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
                GenericTypeIndicator<List<Produto>> type =
                        new GenericTypeIndicator<List<Produto>>();

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
}
