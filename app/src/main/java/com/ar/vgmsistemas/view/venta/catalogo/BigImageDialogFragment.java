package com.ar.vgmsistemas.view.venta.catalogo;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.view.dialogs.BaseDialogFragment;

import java.io.File;

public class BigImageDialogFragment extends BaseDialogFragment {

    static BigImageDialogFragment newInstance() {

        return new BigImageDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_big_image, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.imagenCatalogo);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String articulo = bundle.getString("item");
            File imgFile;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imgFile = new File(getContext().getExternalFilesDir(null) + "" +
                        File.separator + Preferencia.getPathImages() + "/Thumb_" + articulo + ".JPG");
            } else {
                imgFile = new File(Environment.getExternalStorageDirectory() +
                        File.separator + Preferencia.getPathImages() + "/Thumb_" + articulo + ".JPG");
            }
            if (imgFile.exists()) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
            } else {
                imageView.setImageResource(R.drawable.imgnodisponible);
            }
        }

        return view;
    }
}
