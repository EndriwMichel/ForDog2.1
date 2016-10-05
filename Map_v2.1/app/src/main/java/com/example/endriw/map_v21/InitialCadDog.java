package com.example.endriw.map_v21;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class InitialCadDog extends AppCompatActivity implements ActionMode.Callback, AdapterView.OnItemLongClickListener {
    private int count;
    public String nomeTeste;
    public int imgTeste;
    public ImageView imagemteste;
    public ImageView imagemteste2;
    public ImageView[] image = new ImageView[4];
    public byte[] imageAsBytes;

    public RecyclerView rView;

    List<ItemObjectInitial> allItems = new ArrayList<ItemObjectInitial>();
    public Bitmap[] bitarray = new Bitmap[4];

    private Firebase mRef;
    public TextView txt_nenhum;
    static View v;
    ActionMode mActionMode;
    private int itemPosition;
    private int x = 0;
    // public String[] teste = {"Cadastrar Lost Dog", "cadastrar2", "cadastrar3"};
    public String [] teste = new String[4];
    public String [] teste2 = new String[4];
//    private List<ListViewMaps> custom = new ArrayList<ListViewMaps>();
    private List<ListViewInitialCad> custom = new ArrayList<ListViewInitialCad>();

    private int[] vetor = new int[]{R.drawable.bone};//, R.drawable.dogbone, R.drawable.doghouse};
   // private int[] vetor = new int[1];

    public ListView lv;

    private LinearLayoutManager lLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_cadog);

        mRef = new Firebase("https://dog-603e7.firebaseio.com/");


  /*      ArrayList<String> bora = new ArrayList<String>();

        for (int x = 0; x < teste.length; x++) {
            bora.add(x, teste[x]);
        }*/
//        ArrayAdapter<String> adaptBora = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, bora);

        lv = (ListView) findViewById(R.id.ListDog);
        imagemteste = (ImageView)findViewById(R.id.imagemTeste);
        FireB();
        //-------------------------------------------------------Recycler---------------------------------------------
/*
        List<ItemObjectInitial> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(InitialCadDog.this);

        rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);

        RecyclerInitial rcAdapter = new RecyclerInitial(InitialCadDog.this, rowListItem);
        rView.setAdapter(rcAdapter);
        */
        //-------------------------------------------------------Recycler---------------------------------------------
        lv.setOnItemLongClickListener(this);

       // populateList();
    }

    protected void onResume() {
        super.onResume();
        FireB();
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // Inflate a menu resource providing context menu items
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.inital_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                // Aqui vai a função de deletar do firebase
                Toast.makeText(this, "Shared! position:" + itemPosition, Toast.LENGTH_SHORT).show();
                mode.finish(); // Action picked, so close the CAB
                v.setSelected(false);
                return true;
            default:
               // return false;
        }
        v.setSelected(false);
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mActionMode = null;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // if actionmode is null "not started"
        if (mActionMode != null) {
            return false;
        }
        v = view;
        // Start the CAB
        itemPosition = position;
        mActionMode = this.startActionMode(this);
        v.setSelected(true);
        return true;
    }

    private class MyListViewMaps extends ArrayAdapter<ListViewInitialCad> {
        public MyListViewMaps() {
            super(InitialCadDog.this, R.layout.listviewmaps, custom);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.listviewmaps, parent, false);
            }
            ListViewInitialCad cus = custom.get(position);

            ImageView imgview = (ImageView) itemView.findViewById(R.id.img);
            imgview.setImageBitmap(cus.getImg());

            TextView textview = (TextView) itemView.findViewById(R.id.num);
            textview.setText(cus.getDex());

            return itemView;
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.caddog_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Intent intent  = new Intent(this, CadDog.class);
        int id = item.getItemId();

        if(id == R.id.action_addog){
          startActivity(intent);
        }
        else if(id == R.id.action_help){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Cadastre um cão !" +
                    "Utilize esta tela para cadastrar, alterar ou remover um cachorro perdido !");
            builder.create();
            builder.show();
        }

        return true;
    }

    public void Aparece(View view) {

        txt_nenhum = (TextView) findViewById(R.id.nenhumdog);
        txt_nenhum.setVisibility(view.VISIBLE);
    }


    public void FireB(){

        mRef = new Firebase("https://dog-603e7.firebaseio.com/");

        mRef.child("vaanhalen00@gmail@com").child("ownDog").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    count = (int) dataSnapshot.getChildrenCount();
                    Cachorro cachorro = dataSnapshot1.getValue(Cachorro.class);
                    System.out.println("dogNome: "+cachorro.getDogNome());


                        teste[x] = cachorro.getDogNome();
                    imageAsBytes = Base64.decode(cachorro.getDogFoto().getBytes(), Base64.DEFAULT);
                    // image[x].setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                        imagemteste.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                    //    imagemteste2.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

                        bitarray[x] = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

                    custom.add(new ListViewInitialCad(teste[x], bitarray[x]));
                        System.out.println("vetor teste: "+teste[x]);
                        System.out.println("vetor bit: "+bitarray[x]);

                x=+1;
                    System.out.println("valor de x: "+x);
                   // allItems.add(new ItemObjectInitial(nomeTeste, nomeTeste, R.drawable.user_ico));
                    ArrayAdapter<ListViewInitialCad> adapter = new MyListViewMaps();
                    lv.setChoiceMode(lv.CHOICE_MODE_SINGLE);
                    lv.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

  /*  private void populateList() {

        for (int x = 0; x < vetor.length; x++) {

            custom.add(new ListViewMaps(teste[x], vetor[x]));

        }
    }
*/
    private List<ItemObjectInitial> getAllItemList(){

   //    List<ItemObjectInitial> allItems = new ArrayList<ItemObjectInitial>();

            allItems.add(new ItemObjectInitial(teste[1], teste2[1], R.drawable.user_ico));

        return allItems;
    }

}