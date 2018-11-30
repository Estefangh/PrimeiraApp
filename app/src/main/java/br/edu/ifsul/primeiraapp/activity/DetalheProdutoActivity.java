package br.edu.ifsul.primeiraapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import br.edu.ifsul.primeiraapp.R;
import br.edu.ifsul.primeiraapp.model.Item_pedido;
import br.edu.ifsul.primeiraapp.model.Produto;
import br.edu.ifsul.primeiraapp.setup.AppSetup;

public class DetalheProdutoActivity extends AppCompatActivity {

    private static final String TAG = "detalheProdutoActivity";
    private EditText etQuantidade;
    private int position = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto);

         etQuantidade = findViewById(R.id.etQuantidade);


        //obtém o objeto produto anexado a intent
        position = getIntent().getExtras().getInt("position");
        Log.d(TAG, "Positon = " + position);
        Log.d(TAG, "Objeto selecionado = " + AppSetup.produtos.get(position));


        Button btComprar = findViewById(R.id.btComprarProduto);
        btComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppSetup.cliente == null){
                    Toast.makeText(DetalheProdutoActivity.this, "Selecione um cliente para venda.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetalheProdutoActivity.this, ClienteActivity.class));
                }else{
                    if(!etQuantidade.getText().toString().isEmpty()){
                        if(Integer.parseInt(etQuantidade.getText().toString()) <= AppSetup.produtos.get(position).getQuantidade().intValue()){
                            //cria o item vendido e o adicona no carrinho
                            Item_pedido item_pedido = new Item_pedido();
                            item_pedido.setQuantidadePedido(Integer.valueOf(etQuantidade.getText().toString()));
                            item_pedido.setProduto(AppSetup.produtos.get(position));
                            item_pedido.getProduto().setSituacao(false);
                            item_pedido.setTotalItem(AppSetup.produtos.get(position).getValor()*item_pedido.getQuantidadePedido());
                            AppSetup.cesta.add(item_pedido);
                            //atualiza o estoque no banco
                            DatabaseReference myRef = AppSetup.getInstance().child("produtos").child(AppSetup.produtos.get(position).getKey()).child("quantidade");
                            myRef.setValue(AppSetup.produtos.get(position).getQuantidade() - item_pedido.getQuantidadePedido());
                            Toast.makeText(DetalheProdutoActivity.this,"Item adicionado ao carrinho.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DetalheProdutoActivity.this, CestaActivity.class));
                            finish();
                        }else{
                            Toast.makeText(DetalheProdutoActivity.this, "Ultrapassa a quantidade em estoque.", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(DetalheProdutoActivity.this, "Digite a quantidade.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

atualizarView();
    }

    private void atualizarView() {
        TextView tvNome = findViewById(R.id.tvNomeProdutoAdapter);
        tvNome.setText(AppSetup.produtos.get(position).getNome());
        TextView tvDescricao= findViewById(R.id.tvDescricaoProduto);
        tvDescricao.setText (AppSetup.produtos.get(position).getDescricao());
        TextView tvValor = findViewById(R.id.tvValorProduto);
        tvValor.setText(AppSetup.produtos.get(position).getValor().toString());
        TextView tvQuantidade = findViewById(R.id.tvQuantidadeProduto);
        tvQuantidade.setText(AppSetup.produtos.get(position).getQuantidade().toString());
    }


}



