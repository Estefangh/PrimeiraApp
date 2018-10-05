package br.edu.ifsul.primeiraapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifsul.primeiraapp.R;
import br.edu.ifsul.primeiraapp.model.Produto;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Produto produto = new Produto();

        produto.setNome("Notebook");
        produto.setDescricao("Acer");
        produto.setValor (1220.00);
        produto.setQuantidade(100);



        TextView tvNome = findViewById(R.id.tvNomeProduto);
        tvNome.setText(produto.getNome());


        TextView tvDesc = findViewById(R.id.tvDescProduto);
        tvDesc.setText(produto.getDescricao());

        TextView tvValor = findViewById(R.id.tvValorProduto);
        tvDesc.setText(produto.getValor().toString());

        TextView tvQuantidade = findViewById(R.id.tvQuantProduto);
        tvQuantidade.setText(produto.getQuantidade().toString());


        Button btnCompraProduto = findViewById(R.id.btnComprarProduto);
        btnCompraProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etQuantidadeProduto = findViewById(R.id.etQuantidade);
                produto.setQuantidade(Integer.valueOf(etQuantidadeProduto.getText().toString()));
                String quantidade = etQuantidadeProduto.getText().toString();
                Toast.makeText(MainActivity.this, "Para-BENS:" + produto.getQuantidade(), Toast.LENGTH_SHORT).show();

            }
        });



    }
}
