package com.example.endriw.map_v21;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class InitialLostDog extends AppCompatActivity implements ActionMode.Callback, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    private int count;
    private static int conta;
    public String nomeTeste;
    public String itemHash;
    public byte[] imageAsBytes;
    public RecyclerView rView;
    private ProgressDialog progress;
    List<ItemObjectInitial> allItems = new ArrayList<ItemObjectInitial>();
    public Bitmap[] bitarray = new Bitmap[4];
    private String[] desc = new String[4];

    private Firebase mRef;
    public TextView txt_nenhum;
    static View v;
    ActionMode mActionMode;
    private int itemPosition;
    private int x = 0;
    ArrayAdapter<ListViewInitialCad> adapter;
    // public String[] teste = {"Cadastrar Lost Dog", "cadastrar2", "cadastrar3"};

    public String [] teste = new String[4];
    public String [] teste_hash = new String[4];

    //    private List<ListViewMaps> custom = new ArrayList<ListViewMaps>();

    private List<ListViewInitialCad> custom = new ArrayList<ListViewInitialCad>();

    private int[] vetor = new int[]{R.drawable.bone};

    //, R.drawable.dogbone, R.drawable.doghouse};
    // private int[] vetor = new int[1];

    public ListView lv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_lostdog);

        mRef = new Firebase("https://dog-603e7.firebaseio.com/");


        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }


//        ArrayAdapter<String> adaptBora = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, bora);


        lv = (ListView) findViewById(R.id.ListDog);
        lv.setOnItemLongClickListener(this);
        lv.setOnItemClickListener(this);


        // populateList();
    }

    protected void onResume() {
        super.onResume();
        custom.clear();
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
                dogFirebase fireData = new dogFirebase();
                fireData.deleteDog("vaanhalen00@gmail@com", "lostDog", itemHash, mRef);
                Toast.makeText(this, "Deleted! position:" + itemHash, Toast.LENGTH_SHORT).show();
                custom.clear();
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
        TextView hash = (TextView)view.findViewById(R.id.hash);
        itemHash = hash.getText().toString();
        itemPosition = position;
        mActionMode = this.startActionMode(this);
        v.setSelected(true);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*        final Intent intent  = new Intent(this, UpdateMaps.class);
        TextView hash = (TextView)view.findViewById(R.id.hash);
        intent.putExtra("foto", imageAsBytes);
        intent.putExtra("hash", hash.getText().toString());
        startActivity(intent);*/
        Toast.makeText(this, "clicou !", Toast.LENGTH_SHORT);
    }

    private class MyListViewMaps extends ArrayAdapter<ListViewInitialCad> {
        public MyListViewMaps() {
            super(InitialLostDog.this, R.layout.listviewmaps, custom);
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

            TextView textView_desc = (TextView) itemView.findViewById(R.id.data);
            textView_desc.setText(cus.getData());

            TextView textView_hash = (TextView) itemView.findViewById(R.id.hash);
            textView_hash.setText(cus.getHash());

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
        //  final Intent intent  = new Intent(this, CadDog.class);
        int id = item.getItemId();

      /*  if(id == R.id.action_addog){
          startActivity(intent);
        }
        else */
        if(id == R.id.action_help){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Cadastre um cão !" +
                    "Utilize esta tela para cadastrar, alterar ou remover um cachorro perdido !");
            builder.create();
            builder.show();
        }

        return true;
    }

/*    public void Aparece(View view) {

        txt_nenhum = (TextView) findViewById(R.id.nenhumdog);
        txt_nenhum.setVisibility(view.VISIBLE);
    }
*/

    public void FireB() {
        progress = ProgressDialog.show(this, "Por favor aguarde!", "Carregando dogs...", false, true);
        new Thread(new Runnable() {
            @Override
            public void run() {

                //começa o run--------------------------------------------------------------------------

                String email = MapsActivity.accountName.replace(".", "@");

                mRef = new Firebase("https://dog-603e7.firebaseio.com/");

                mRef.child(email).child("lostDog").addValueEventListener(new ValueEventListener() {

                    String valiDog;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            count = (int) dataSnapshot.getChildrenCount();
                            conta = count;
                            Cachorro cachorro = dataSnapshot1.getValue(Cachorro.class);
                            System.out.println("dogNome: " + cachorro.getDogNome());
                            valiDog = cachorro.getDogNome();
                            teste_hash[x] = cachorro.getDogHash();
                            teste[x] = cachorro.getDogData();
                            desc[x] = cachorro.getDogDesc();
                            imageAsBytes = Base64.decode(cachorro.getDogFoto().getBytes(), Base64.DEFAULT);
                            bitarray[x] = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

                            custom.add(new ListViewInitialCad(teste[x], teste_hash[x], bitarray[x], desc[x]));

                            //       x=+x;

                        }

                        adapter = new MyListViewMaps();
                        lv.setChoiceMode(lv.CHOICE_MODE_SINGLE);
                        if(adapter.getCount() != 0){
                            lv.setAdapter(adapter);
                        }else{
                            Toast.makeText(InitialLostDog.this, "Não ha cadastros !", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                //aqui termina o run------------------------------------------------------------------
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                    }
                });

            }
        }).start();

    }

}
