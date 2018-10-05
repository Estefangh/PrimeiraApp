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
import br.edu.ifsul.primeiraapp.adapter.ClientesAdapter;
import br.edu.ifsul.primeiraapp.model.Cliente;
import br.edu.ifsul.primeiraapp.setup.AppSetup;

public class ClienteActivity extends AppCompatActivity {

    private List<Cliente> clientes;
    public ListView lvclientes;
    private static DatabaseReference myRef;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_cliente);

       lvclientes = findViewById(R.id.lvClientes);
       lvclientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(ClienteActivity.this, DetalheClienteActivity.class);
               intent.putExtra("cliente", clientes.get(position));
               startActivity(intent);

           }
       });

clientes = new ArrayList<>();
      // FirebaseDatabase database = FirebaseDatabase.getInstance();
       //DatabaseReference myRef = database.getReference();

       myRef= AppSetup.getInstance();
       // Read from the database
       myRef.child("clientes").addValueEventListener(new ValueEventListener() {

           public static final String TAG ="ClienteActivity" ;

           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               // This method is called once with the initial value and again
               // whenever data at this location is updated.
               GenericTypeIndicator<List<Cliente>> type =
                       new GenericTypeIndicator<List<Cliente>>();

               clientes = dataSnapshot.getValue(type);
               clientes.remove(null);
               //Cliente cliente = dataSnapshot.getValue(Cliente.class);
             //  Log.d(TAG, "Nome do cliente: " + clientes.getNome());
               Log.d(TAG, "Nome do cliente: " + clientes);
           }

           @Override
           public void onCancelled(DatabaseError error) {
               // Failed to read value
               Log.w(TAG, "erro, não deu pra ler", error.toException());
           }
       });

   }

private void atualizarView(){
       lvclientes.setAdapter(new ClientesAdapter(this, clientes));
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       getMenuInflater().inflate(R.menu.menu_activity_clientes, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuitem_pesquisar).getActionView();
        searchView.setQueryHint("Digite o nome do cliente:");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Cliente> clientesFilter = new ArrayList<>();

                for (Cliente cliente : clientes){
                    if (cliente.getNome().contains(newText)){
                        clientesFilter.add(cliente);
                    }
                }

                lvclientes.setAdapter(new ClientesAdapter( ClienteActivity.this, clientesFilter));
                return true;
            }
        });

        return true;
    }
}
