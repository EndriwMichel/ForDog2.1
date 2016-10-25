package com.example.endriw.map_v21;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

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
    private Firebase mRef;
    private static final String mask11 = "(##) #####-####";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_user);

        aSwitch = (Switch)findViewById(R.id.dogSwitch);
        apelido = (TextView)findViewById(R.id.user_apelido);
        numero = (TextView)findViewById(R.id.user_numero);
        sn = (TextView)findViewById(R.id.simNao);
        tx_email = (TextView)findViewById(R.id.user_email);
        tx_email.setText("Olá " + email);

    //    PhoneNumberUtils.formatNumber(numero.getText().toString());

        fireB();

        //Codigo Switch---------------------------------------------------------
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(aSwitch.isChecked()){
                    sn.setText("Sim");
                }else{
                    sn.setText("Não");
                }
            }
        });

        numero.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void Cancelar(View view) {
        this.finish();
    }

    public void Salvar(View view) {

        String nick = String.valueOf(apelido.getText());
        String cel = String.valueOf(numero.getText());
        String notify = String.valueOf(sn.getText());

        String email = MapsActivity.accountName.replace(".", "@");
        dogFirebase fireData = new dogFirebase();

        fireData.graveUser(email, nick, cel, notify);

        Toast.makeText(this, "Cadastro efetuado",
                Toast.LENGTH_LONG).show();
        this.finish();
    }


    public void fireB() {

        mRef = new Firebase("https://dog-603e7.firebaseio.com/");
        mRef.child("emails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                for (DataSnapshot dataSnapshot : data.getChildren()) {

                    Emails email = dataSnapshot.getValue(Emails.class);
                    String stEmail = email.getEmail();
                    String account_email = MapsActivity.accountName.replace(".", "@");

                    mRef.child(account_email).child("userDog").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override

                        public void onDataChange(DataSnapshot dataSnapshots) {

                            final Cachorro cachorro = dataSnapshots.getValue(Cachorro.class);
                            System.out.println("dados do usuario: "+cachorro.getDogNick()+" / "+cachorro.getDogCel()+" / "+cachorro.getDogNotify());

                            if(cachorro.getDogNick() != null){
                                apelido.setText(cachorro.getDogNick());
                            }
                            if(cachorro.getDogCel() != null){
                                numero.setText(cachorro.getDogCel());
                            }
                            if(cachorro.getDogNotify() != null) {
                                if (cachorro.getDogNotify().equals("Sim")){
                                    aSwitch.setChecked(true);
                                }else if(cachorro.getDogNotify().equals("Não")) {
                                    aSwitch.setChecked(false);
                                }
                            }
                        }


                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


}
