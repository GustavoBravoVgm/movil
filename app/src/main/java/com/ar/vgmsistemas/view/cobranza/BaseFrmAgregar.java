package com.ar.vgmsistemas.view.cobranza;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.Matematica;

public class BaseFrmAgregar extends AppCompatActivity {
    private EditText mEditText;
    private Double mMontoRestante;
    private TextView tvMontoRestante;
    private boolean controlMaxValue;

    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controlMaxValue = getIntent().getExtras().getBoolean(PagosFragment.EXTRA_MAX_CONTROL, false);
        mMontoRestante = getIntent().getExtras().getDouble(PagosFragment.EXTRA_MONTO_MAXIMO);
    }

    ;

    public void initComponents() {
        tvMontoRestante = (TextView) findViewById(R.id.tvMontoPagar);
        tvMontoRestante.setText(Formatter.formatMoney(getMontoMaximo()));
    }

    protected void setControlEditText(EditText editText, final ControlMontoListener listener) {
        mEditText = editText;
        if (!controlMaxValue) {
            mEditText.setOnKeyListener(new OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    mEditText.setError(null);
                    listener.dataOk();
                    return false;
                }
            });

        } else {
            mEditText.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable arg0) {

                    if (!isMontoValid(arg0)) {
                        mEditText.setError("Monto superado");
                        listener.dataInvalid();
                    } else {
                        mEditText.setError(null);
                        listener.dataOk();
                    }

                }
            });
        }
    }

    private boolean isMontoValid(Editable editable) {
        double monto;
        try {
            monto = Double.parseDouble(editable.toString());
        } catch (NumberFormatException exception) {
            monto = 0;
        }
        return !(monto > getMontoMaximo());
    }

    protected double getMontoMaximo() {
        return Matematica.Round(mMontoRestante, 2);
    }

    protected interface ControlMontoListener {
        void dataOk();

        void dataInvalid();
    }
}
