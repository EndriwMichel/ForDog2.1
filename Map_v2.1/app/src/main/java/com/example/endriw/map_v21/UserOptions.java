package com.example.endriw.map_v21;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Endriw on 02/09/2016.
 */
public class UserOptions extends AppCompatActivity {
    ArrayAdapter<String> delay_adapter;
    private TextView tx_email;
    private TextView sn;
    private TextView apelido;
    private TextView numero;
    private String email = MapsActivity.accountName;
    private Switch aSwitch;
    private Spinner userSpinner;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_user);

        userSpinner = (Spinner)findViewById(R.id.UserSpinner);
        aSwitch = (Switch)findViewById(R.id.dogSwitch);
        apelido = (TextView)findViewById(R.id.user_apelido);
        numero = (TextView)findViewById(R.id.user_numero);
        sn = (TextView)findViewById(R.id.simNao);
        tx_email = (TextView)findViewById(R.id.user_email);
        tx_email.setText("Olá "+email);

        if(!aSwitch.isChecked()){
            userSpinner.setEnabled(false);
        }else{
            userSpinner.setEnabled(false);
        }

        //Codigo Spinner-----------------------------------------------------------------------------------------------------
            delay_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("Selecione o tempo");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }

        };
        delay_adapter.add("1 minuto");
        delay_adapter.add("5 minutos");
        delay_adapter.add("10 minutos");
        delay_adapter.add("30 minutos");
        delay_adapter.add("Selecione o tempo");

        userSpinner.setAdapter(delay_adapter);
        userSpinner.setSelection(delay_adapter.getCount());


        //Codigo Switvh---------------------------------------------------------
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(aSwitch.isChecked()){
                    userSpinner.setEnabled(true);
                    sn.setText("Sim");
                }else{
                    userSpinner.setEnabled(false);
                    sn.setText("Não");
                }
            }
        });
    }

    public void Cancelar(View view) {
        this.finish();
    }

    public void Salvar(View view) {

        apelido.getText();
        numero.getText();

        System.out.println(apelido+" : "+numero);

    }



}
