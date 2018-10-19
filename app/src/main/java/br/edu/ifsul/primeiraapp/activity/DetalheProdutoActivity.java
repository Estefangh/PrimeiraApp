package br.edu.ifsul.primeiraapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.ifsul.primeiraapp.R;
import br.edu.ifsul.primeiraapp.model.Item_pedido;
import br.edu.ifsul.primeiraapp.model.Produto;
import br.edu.ifsul.primeiraapp.setup.AppSetup;

public class DetalheProdutoActivity extends AppCompatActivity {

    private static final String TAG = "detalheProdutoActivity";
    private Produto produto;
    private EditText etQuantidade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //myRef.setValue("Hello, World!");
        //Log.d(TAG, "Conectou com o banco" + myRef.toString());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto);

         etQuantidade = findViewById(R.id.etQuantidade);


        produto = new Produto();
        //código para criar um objeto no BD
         /*produto.setCodigoDeBarras(2L);//código de barras do produto
         produto.setNome("mouse");
         produto.setDescricao("mouse usb");
         produto.setValor(35.00);
         produto.setQuantidade(new Integer ( 100));//como estou usando o Double posso criar um construtor para informar o valor
         myRef.child("produtos").child(String.valueOf(produto.getCodigoDeBarras())).setValue(produto);*/

        produto = (Produto) getIntent().getSerializableExtra("produto");
        atualizarView();





        Button btComprar = findViewById(R.id.btComprarProduto);
        btComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppSetup.cliente == null){
                    Toast.makeText(DetalheProdutoActivity.this, "Selecione um cliente para venda.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetalheProdutoActivity.this, ClienteActivity.class));
                }else{
                    if(!etQuantidade.getText().toString().isEmpty()){
                        if(Integer.parseInt(etQuantidade.getText().toString()) <= produto.getQuantidade().intValue()){
                            //cria o item vendido e o adicona no carrinho
                            Item_pedido item_pedido = new Item_pedido();
                            item_pedido.setQuantidadePedido(Integer.valueOf(etQuantidade.getText().toString()));
                            item_pedido.setProduto(produto);
                            item_pedido.getProduto().setSituacao(false);
                            item_pedido.setTotalItem(produto.getValor()*item_pedido.getQuantidadePedido());
                            AppSetup.cesta.add(item_pedido);
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


    }

    private void atualizarView() {
        TextView tvNome = findViewById(R.id.tvNomeProdutoAdapter);
        tvNome.setText(produto.getNome());
        TextView tvDescricao= findViewById(R.id.tvDescricaoProduto);
        tvDescricao.setText (produto.getDescricao());
        TextView tvValor = findViewById(R.id.tvValorProduto);
        tvValor.setText(produto.getValor().toString());
        TextView tvQuantidade = findViewById(R.id.tvQuantidadeProduto);
        tvQuantidade.setText(produto.getQuantidade().toString());
    }


}



