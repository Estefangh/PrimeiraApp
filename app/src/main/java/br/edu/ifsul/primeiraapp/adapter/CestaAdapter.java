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
import br.edu.ifsul.primeiraapp.activity.CestaActivity;
import br.edu.ifsul.primeiraapp.model.Cliente;
import br.edu.ifsul.primeiraapp.model.Item_pedido;
import br.edu.ifsul.primeiraapp.model.Produto;

public class CestaAdapter extends ArrayAdapter<Item_pedido> {
    private static final String TAG = "cestaAdapter";
    private Context context;
    private List<Item_pedido> itensPedido;


    public CestaAdapter(@NonNull Context context,  @NonNull List<Item_pedido> itens) {
        super(context, 0, itens);
        this.context=context;
        this.itensPedido=itens;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Item_pedido itens = itensPedido.get(position);

        if (convertView==null){
            convertView= LayoutInflater.from(context)
                    .inflate(R.layout.item_cesta_adapter, parent, false);//criar o cartão do item
        }

        ImageView ivProdutoCesta = convertView.findViewById(R.id.ivProdutoCesta);
        TextView tvNomeProdutoCesta = convertView.findViewById(R.id.tvNomeProdutoCesta);
        tvNomeProdutoCesta.setText(itens.getProduto().getNome());
        TextView tvQuantidadeProdutoCesta = convertView.findViewById(R.id.tvQuantidadeProdutoCesta);
        tvQuantidadeProdutoCesta.setText(itens.getProduto().getQuantidade());
        TextView tvValorTotalProduto = convertView.findViewById(R.id.tvValorTotalProduto);
        tvValorTotalProduto.setText(itens.getProduto().getValor().toString());


        if (itens.getProduto().getUrl_foto() != null){
           //fazer esse if
        }
        else{
            ivProdutoCesta.setImageResource(R.drawable.carrinho_de_compras);
        }
        //fazer o teste se o produto tem imagem
        return convertView;
    }
}
