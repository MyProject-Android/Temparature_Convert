package com.hqv.temparature_convert;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class ConverTemparature extends Fragment {

    SharedPreferences sharedPreferences;
    TextView txtHistory;
    RadioGroup rdgConvert;
    EditText edtInput, edtResult;
    Button btnConvert;
    RadioButton rdbCtoF, rdbFtoC;
    View view;
    String history;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_conver_temparature, container, false);
        AnhXa();
        sharedPreferences = getActivity().getSharedPreferences("ketqua", Context.MODE_PRIVATE);
        rdgConvert.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.FtoC: {
                        edtInput.setHint("°F");
                        edtResult.setHint("°C");
                        edtInput.setText("");
                        edtResult.setText("");
                        break;
                    }
                    case R.id.CtoF: {
                        edtInput.setHint("°C");
                        edtResult.setHint("°F");
                        edtInput.setText("");
                        edtResult.setText("");
                        break;
                    }
                }
            }
        });
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history = sharedPreferences.getString("history", "Lịch sử chuyển đổi");
                Double result;
                txtHistory = getActivity().findViewById(R.id.textViewHistory);
                if (edtInput.getText().toString().isEmpty() || edtInput.getText().toString().equals(".")) {
                    edtInput.setText(1 + "");
                }
                // alt + 0176 là kí hiệu nhiệt độ
                if (rdbFtoC.isChecked()) {
                    result = convertF_To_C(Double.parseDouble(String.valueOf(edtInput.getText())));
                    edtResult.setText(result.toString());
                    history = edtInput.getText() + "°F  =  " + edtResult.getText() + "°C\n" + history;
                } else if (rdbCtoF.isChecked()) {
                    result = convertC_To_F(Double.parseDouble(String.valueOf(edtInput.getText())));
                    edtResult.setText(result.toString());
                    history = edtInput.getText() + "°C  =  " + edtResult.getText() + "°F\n" + history;
                }

                txtHistory.setText(history);

                //đưa value history vào sharepreferences với key là history
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("history", history);
                editor.commit();
            }
        });

        return view;
    }

    private void AnhXa() {
//    txtHistory=getActivity().findViewById(R.id.textViewHistory);
        edtInput = view.findViewById(R.id.edittextInput);
        edtResult = view.findViewById(R.id.edittexResult);
        btnConvert = view.findViewById(R.id.buttonConvert);
        rdbCtoF = view.findViewById(R.id.CtoF);
        rdbFtoC = view.findViewById(R.id.FtoC);
        rdgConvert = view.findViewById(R.id.convert);

    }

    public double convertF_To_C(double value) {
        return (double) Math.round((value - 32.0) * 5.0 / 9 * 100) / 100;
    }

    public double convertC_To_F(double value) {
        return (double) Math.round((9.0 / 5 * value + 32) * 100) / 100;
    }
}