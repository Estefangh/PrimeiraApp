package br.edu.ifsul.primeiraapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.text.NumberFormat;
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
    private TextView tvTotalCesta, tvNomeClienteCesta, tvNomeVendedorCesta;
    private Double totalPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesta);

        lvCesta = findViewById(R.id.lvListaCesta);
        tvTotalCesta = findViewById(R.id.tvTotalCesta);
        tvNomeClienteCesta = findViewById(R.id.tvNomeClienteCesta);
        tvNomeVendedorCesta = findViewById(R.id.tvNomeVendedorCesta);

        //trata o click longo no cartão da Listview
        lvCesta.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialogExcluirItem("Atenção", "Você realmente deseja excluir este item?", position);

                return true;
            }
        });

        //trata o click curto no cartão da Listview
        lvCesta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CestaActivity.this, DetalheProdutoActivity.class);
                intent.putExtra("produto", AppSetup.cesta.get(position).getProduto());
                startActivity(intent);
                AppSetup.cesta.remove(position);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_cesta, menu);

        return true;
    }



    @Override
    protected void onResume() {
        super.onResume();
        lvCesta.setAdapter(new CestaAdapter(this, AppSetup.itensPedido));
        Double acumulador = new Double(0);
        for (Item_pedido item : AppSetup.cesta){
            acumulador += item.getTotalItem();
        }
        tvTotalCesta.setText(acumulador.toString());
        tvNomeClienteCesta.setText(AppSetup.cliente.getNome()+" " +AppSetup.cliente.getSobrenome());
        tvNomeVendedorCesta.setText(AppSetup.vendedor.getEmail());

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuitem_salvar:{
                alertDialogSalvarPedido("Quase lá", "\nTotal do Pedido = " + NumberFormat.getCurrencyInstance().format(tvTotalCesta) + " . Confirmar?");
                break;
            }
            case R.id.menuitem_cancelar:{
                alertDialogCancelarPedido("Ops! Vai cancelar?", "Tem certeza que quer cancelar este pedido?");
                break;
            }
        }
        return true;
    }

    private void atualizarView() {
        lvCesta.setAdapter(new CestaAdapter(CestaActivity.this, AppSetup.cesta));
        //totaliza o pedido
        totalPedido = new Double(0);
        for(Item_pedido Item_pedido : AppSetup.cesta){
            totalPedido += Item_pedido.getTotalItem();
        }
        tvTotalCesta.setText(NumberFormat.getCurrencyInstance().format(totalPedido));
    }

    private void alertDialogExcluirItem(String titulo, String mensagem, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //add the title and text
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        //add the buttons
        builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppSetup.cesta.remove(position); //remove do carrinho
                Toast.makeText(CestaActivity.this, "Produto removido.", Toast.LENGTH_SHORT).show();
                atualizarView();
            }
        });
        builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CestaActivity.this, "Operação cancelada.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    private void alertDialogSalvarPedido(String titulo, String mensagem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //add the title and text
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        //add the buttons
        builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CestaActivity.this, "Ótimo! Vendido.", Toast.LENGTH_SHORT).show();
                /*
                 *  persistir a o pedido no Firebase aqui!!!!!!!!!!!! Lembrar de atualizar o estoque
                 *  e controlar as exceções.
                 */
                AppSetup.cesta.clear();
                AppSetup.cliente = null;
                finish();
            }
        });
        builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CestaActivity.this, "Ótimo.Operação cancelada.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    private void alertDialogCancelarPedido(String titulo, String mensagem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //add the title and text
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        //add the buttons
        builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CestaActivity.this, "Que pena! Pedido cancelado.", Toast.LENGTH_SHORT).show();
                AppSetup.cesta.clear();
                AppSetup.cliente = null;
                finish();
            }
        });
        builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CestaActivity.this, "Ótimo! Operação cancelada.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }
}
