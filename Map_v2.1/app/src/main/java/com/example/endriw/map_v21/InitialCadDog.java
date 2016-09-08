package com.example.endriw.map_v21;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;


public class InitialCadDog extends AppCompatActivity implements ActionMode.Callback, AdapterView.OnItemLongClickListener {

    public TextView txt_nenhum;
    static View v;
    ActionMode mActionMode;
    private int itemPosition;
    public String[] teste = {"Cadastrar Lost Dog", "cadastrar2", "cadastrar3"};
    private List<ListViewMaps> custom = new ArrayList<ListViewMaps>();
    private int[] vetor = new int[]{R.drawable.bone, R.drawable.dogbone, R.drawable.doghouse};

    public ListView lv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_cadog);

        ArrayList<String> bora = new ArrayList<String>();

        for (int x = 0; x < teste.length; x++) {
            bora.add(x, teste[x]);
        }
//        ArrayAdapter<String> adaptBora = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, bora);

        lv = (ListView) findViewById(R.id.ListDog);

        populateList();

        ArrayAdapter<ListViewMaps> adapter = new MyListViewMaps();
        lv.setChoiceMode(lv.CHOICE_MODE_SINGLE);
        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(this);
    }

    private void populateList() {

        for (int x = 0; x < vetor.length; x++) {

            custom.add(new ListViewMaps(teste[x], vetor[x]));

        }
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

    private class MyListViewMaps extends ArrayAdapter<ListViewMaps> {
        public MyListViewMaps() {
            super(InitialCadDog.this, R.layout.listviewmaps, custom);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.listviewmaps, parent, false);
            }
            ListViewMaps cus = custom.get(position);

            ImageView imgview = (ImageView) itemView.findViewById(R.id.img);
            imgview.setImageResource(cus.getImg());

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

}