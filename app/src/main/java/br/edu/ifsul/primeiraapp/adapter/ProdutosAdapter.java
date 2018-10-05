package br.edu.ifsul.primeiraapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsul.primeiraapp.R;
import br.edu.ifsul.primeiraapp.activity.ProdutosActivity;
import br.edu.ifsul.primeiraapp.model.Produto;

public class ProdutosAdapter extends ArrayAdapter<Produto> {

    private List<Produto> produtos;
    private Context context;

    public ProdutosAdapter(@NonNull Context  context,@NonNull List<Produto> produtos) {
        super(context, 0, produtos);
        this.produtos = produtos;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Produto produto = produtos.get(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(context)
            .inflate(R.layout.item_produtos_adapter, parent,false);
        }

        TextView tvNomeProduto = convertView.findViewById(R.id.tvNomeProdutoAdapter);
        tvNomeProduto.setText(produto.getNome());
        TextView tvEstoque = convertView.findViewById(R.id.tvEstoqueProdutoAdapter);
        tvEstoque.setText(produto.getQuantidade().toString());
        ImageView fotoProduto = convertView.findViewById(R.id.imvFotoProdutoAdapter);



        return convertView;
    }
}
