package com.example.onandroidnoimage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    EditText editTextFind;
    ListView listViewDS;
    FloatingActionButton fabAdd;
    DpHelper dpHelper;
    @Override
    protected void onStart() {
        super.onStart();
        dpHelper.openDB();
        //addData();
        loadRecord();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dpHelper.closeDB();
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    //Methods
    private void addControls(){
        editTextFind = (EditText)findViewById(R.id.editTextFind);
        listViewDS = (ListView) findViewById(R.id.listViewDS);
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        dpHelper = new DpHelper(MainActivity.this);
        registerForContextMenu(listViewDS);
    }
    private void addEvents(){
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                finish();
                loadRecord();
            }
        });
        xuLyTimKiem();
    }
    private void loadRecord() {

        Cursor cursor = dpHelper.getAllRecord();

        TicketCursorAdapter adapter = new TicketCursorAdapter(
                MainActivity.this,
                R.layout.activity_single_row,
                cursor,
                0
        );

        listViewDS.setAdapter(adapter);
    }
    private void addData(){
        Ticket data = new Ticket("A", "B", 2000, 0);
        Ticket data1 = new Ticket("B", "C", 2000, 0);
        Ticket data2 = new Ticket("A", "D", 5000, 1);
//        dpHelper.Insert(1, "Hai Duong", "Ha Noi", 150000, 1);
//        dpHelper.Insert(2, "Hai Phong", "Quang Ninh", 200000, 0);
//        dpHelper.Insert(3, "Lao Cai", "Ha Noi", 100000, 1);
//        dpHelper.Insert(4, "Đà Nẵng", "Ha Noi", 14500, 1);
        dpHelper.Insert(data);
        dpHelper.Insert(data1);
        dpHelper.Insert(data2);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId(); //lay id cua menu
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position; //lay vi tri item duoc click
        Cursor c = dpHelper.getAllRecord();
        c.moveToPosition(vitri); //di chuyen den row co vi tri o tren
        final int idTicket =c.getInt(0); //lay du lieu tai cot 0
        // Toast.makeText(MainActivity.this, idfood + "", Toast.LENGTH_SHORT).show();
        switch (id){
            case R.id.xoa:
                final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Bạn có muốn xoá?");
                builder.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (XoaTicket(idTicket)) {
                            Toast.makeText(MainActivity.this, "Xoá thành công" + "", Toast.LENGTH_SHORT).show();
                            loadRecord();
                        } else
                            Toast.makeText(MainActivity.this, "Xoá thất bại" + "", Toast.LENGTH_SHORT).show();
                    }
                })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.sua:
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", idTicket);
                bundle.putString("gadi", c.getString(1));
                bundle.putString("gaden", c.getString(2));
                bundle.putLong("dongia", c.getLong(3));
                bundle.putInt("chieu", c.getInt(4));
                intent.putExtra("bundleInfo", bundle);
                startActivity(intent);
                finish();
        }
        return true;

    }
    private boolean XoaTicket(int idTicket ){
        long xoa = dpHelper.Delete(idTicket);
        if (xoa != 0) {
            return true;
        } else
            return false;
    }
    private void xuLyTimKiem(){
        editTextFind.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = editTextFind.getText().toString();
                loadRecordbyID(str);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void loadRecordbyID(String gaden){
        Cursor cursor = dpHelper.getRecordbyID(gaden);

        TicketCursorAdapter adapter = new TicketCursorAdapter(
                MainActivity.this,
                R.layout.activity_single_row,
                cursor,
                0
        );
        listViewDS.setAdapter(adapter);
    }

}
