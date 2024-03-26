package com.ar.vgmsistemas.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ar.vgmsistemas.R;

public class VgmInicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.lyt_vgm_inicio);

        // Ocultar la Action Bar
        //getSupportActionBar().hide();

        //agregar animacion
        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);

        ImageView imgLogoGema = findViewById(R.id.imgLogoUltraGema);
        ImageView imgLogoVgm = findViewById(R.id.imgLogoVgm);
        ImageView imgBy = findViewById(R.id.imgBy);

        imgLogoGema.setAnimation(animacion1);

        imgLogoVgm.setAnimation(animacion2);
        imgBy.setAnimation(animacion2);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(VgmInicioActivity.this, FrmLogin.class);
            startActivity(intent);
            finish();
        }, 4000);


    }
}