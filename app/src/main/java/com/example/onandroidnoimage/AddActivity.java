package com.example.onandroidnoimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    EditText editTextGadi,editTextGaden, editTextDonGia,editTextThanhTien;
    RadioButton rdoKhuHoi, rdoMotChieu;
    Button buttonDongY, buttonQuayVe;
    DpHelper dpHelper;
    private int chieu=1;
    @Override
    protected void onStart() {
        super.onStart();
        dpHelper.openDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dpHelper.closeDB();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        addControls();

        addEvents();
    }

    private void addControls(){
        editTextGadi = (EditText)findViewById(R.id.editTextGadi);
        editTextGaden = (EditText)findViewById(R.id.editTextGaden);
        editTextDonGia = (EditText)findViewById(R.id.editTextDonGia);
        editTextThanhTien = (EditText)findViewById(R.id.editTextThanhTien);
        rdoKhuHoi = (RadioButton) findViewById(R.id.rdoKhuHoi);
        rdoMotChieu = (RadioButton) findViewById(R.id.rdoMotChieu);
        buttonDongY = (Button) findViewById(R.id.buttonDongY);
        buttonQuayVe = (Button) findViewById(R.id.buttonQuayVe);
        dpHelper = new DpHelper(AddActivity.this);
        rdoKhuHoi.setChecked(true);

    }
    private void addEvents(){
        rdoMotChieu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rdoKhuHoi.setChecked(false);
                    chieu =0;
                    capNhatThanhTien();
                }
            }
        });
        rdoKhuHoi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rdoMotChieu.setChecked(false);
                    chieu =1;
                    capNhatThanhTien();
                }
            }
        });
        buttonDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kiemTra()){
                    String gaden = editTextGaden.getText().toString();
                    String gadi = editTextGadi.getText().toString();
                    long dongia = Long.parseLong(editTextDonGia.getText().toString());
                    Ticket t = new Ticket(gadi, gaden,dongia,chieu);
                    dpHelper.Insert(t);
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(AddActivity.this, "Error!", Toast.LENGTH_LONG).show();
                }
            }
        });
        buttonQuayVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        textChangeDonGia();
    }
    private boolean kiemTra(){
        if(editTextGaden.getText().toString().equals("") || editTextGadi.getText().toString().equals("") ||editTextDonGia.getText().toString().equals("")){
            return false;
        }
        else {
            return true;
        }
    }
    private void textChangeDonGia(){
        editTextDonGia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //capNhatThanhTien();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                capNhatThanhTien();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //capNhatThanhTien();
            }
        });
    }
    int dongia;
    private void capNhatThanhTien(){
        if(rdoKhuHoi.isChecked() && !editTextDonGia.getText().toString().equals("")){
            dongia = Integer.parseInt(editTextDonGia.getText().toString());
            dongia = (dongia*2*95)/100;
            editTextThanhTien.setText(String.valueOf(dongia));
        }
        else if(rdoMotChieu.isChecked() && !editTextDonGia.getText().toString().equals("")) {
            dongia = Integer.parseInt(editTextDonGia.getText().toString());
            editTextThanhTien.setText(String.valueOf(dongia));
        }
        else if(editTextDonGia.getText().toString().equals("")){
            editTextThanhTien.setText("");
        }
    }

}
