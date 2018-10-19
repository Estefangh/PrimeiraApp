package br.edu.ifsul.primeiraapp.setup;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsul.primeiraapp.model.Cliente;
import br.edu.ifsul.primeiraapp.model.Item_pedido;

public class AppSetup {
    public static Cliente cliente=null;


    public static FirebaseUser vendedor = null;
    public static FirebaseUser user = null;
    private static DatabaseReference myRef = null;
    public static List<Item_pedido> itensPedido=new ArrayList<>();

    public static List<Item_pedido> cesta = new ArrayList<>();

    public static DatabaseReference getInstance(){
        if (myRef == null){
             FirebaseDatabase database = FirebaseDatabase.getInstance();
             DatabaseReference myRef = database.getReference("vendas");

             return myRef;
        }
        return myRef;
    }

}
