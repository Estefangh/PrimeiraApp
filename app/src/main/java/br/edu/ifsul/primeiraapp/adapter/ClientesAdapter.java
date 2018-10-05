package br.edu.ifsul.primeiraapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsul.primeiraapp.R;
import br.edu.ifsul.primeiraapp.model.Cliente;

public class ClientesAdapter extends ArrayAdapter<Cliente> {
    private final List<Cliente> clientes;
    private final Context context;

    public ClientesAdapter(@NonNull Context context, List<Cliente> clientes) {
        super(context, 0, clientes);
        this.clientes = clientes;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       Cliente cliente = clientes.get(position);

       if(convertView==null){
           convertView= LayoutInflater.from(context)
                   .inflate(R.layout.item_clientes_adapter, parent, false);
       }

        TextView tvNomeCliente = convertView.findViewById(R.id.tvNomeClienteAdapter);
        tvNomeCliente.setText(cliente.getNome());
        TextView tvCPF = convertView.findViewById(R.id.tvCPFClienteAdapter);
        tvCPF.setText(cliente.getCpf());
        TextView tvCodigo = convertView.findViewById(R.id.tvCodigoClienteAdapter);
        tvCodigo.setText(cliente.getCodigoDeBarras().toString());
        ImageView fotoCliente = convertView.findViewById(R.id.imvFotoClienteAdapter);

        //teste se o cliente tem uma foto
        if(cliente.getUrl_foto() != null) {
            fotoCliente.setImageURI(Uri.parse(cliente.getUrl_foto()));
        }
        else {
            fotoCliente.setImageResource(R.mipmap.ic_launcher_round);
        }

        return convertView;
    }
}
