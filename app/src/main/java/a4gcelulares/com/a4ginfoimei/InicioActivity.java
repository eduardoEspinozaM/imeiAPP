package a4gcelulares.com.a4ginfoimei;


import android.*;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import a4gcelulares.com.a4ginfoimei.view.fragment.DesbloqueoFragment;
import a4gcelulares.com.a4ginfoimei.view.fragment.RegistroImeiFragment;

public class InicioActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    TelephonyManager tel;
    TextView imei;
    Button boton;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        int permissionCheck = ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.READ_PHONE_STATE );
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso.");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE }, 225);
        } else {
            Log.i("Mensaje", "Se tiene permiso!");
        }


        boton=(Button) findViewById(R.id.siguiente);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                startActivity(new Intent(InicioActivity.this, Registro_imei.class));
            }
        });







        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (item.isChecked()) item.setChecked(false);
                        else item.setChecked(true);
                        drawerLayout.closeDrawers();

                        switch (item.getItemId()) {
                            case R.id.desbloquear:
                                setFragment(0);
                                break;
                            case R.id.registrar:
                                setFragment(1);
                                break;

                            case R.id.registrar_imei:
                                setFragment(2);
                                break;
                        }
                        return false;
                    }
                });
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }
                };
        

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
    public void setFragment(int pos) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (pos) {
            case 0:
                DesbloqueoFragment fragmentDesbloqueo = new DesbloqueoFragment();
                transaction.replace(R.id.fragment, fragmentDesbloqueo);
                transaction.commit();
                break;
            case 1:
                RegistroImeiFragment fragmentRegistrar = new RegistroImeiFragment();
                transaction.replace(R.id.fragment, fragmentRegistrar);
                transaction.commit();
                break;
            case 2:
                fragmentRegistrar = new RegistroImeiFragment();
                transaction.replace(R.id.fragment, fragmentRegistrar  );
                transaction.commit();
                break;
            case 3:
//                RegistroImeiFragment fragmentRegistrar = new RegistroImeiFragment();
//                transaction.replace(R.id.fragment,fragmentRegistrar);
//                transaction.commit();
                break;
        }

    }



    private String getPhoneNumber() {
        TelephonyManager mTelephonyManager;
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return mTelephonyManager.getLine1Number();
        }
        return mTelephonyManager.getLine1Number();
    }
}

