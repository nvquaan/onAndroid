package com.example.onandroidnoimage;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class TicketCursorAdapter extends ResourceCursorAdapter {
    public TicketCursorAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtGadi =(TextView)view.findViewById(R.id.textViewGadi);
        TextView txtGaden =(TextView)view.findViewById(R.id.textViewGaden);
        TextView txtChieu =(TextView)view.findViewById(R.id.textViewChieu);
        TextView txtDonGia =(TextView)view.findViewById(R.id.textViewDonGia);

        int chieu = cursor.getInt(cursor.getColumnIndex(DpHelper.getCHIEU()));
        long giaTien = cursor.getLong(cursor.getColumnIndex(DpHelper.getDONGIA()));
        txtGadi.setText(cursor.getString(cursor.getColumnIndex(DpHelper.getGADI())) +"               -> ");
        txtGaden.setText(cursor.getString(cursor.getColumnIndex(DpHelper.getGADEN())));
        if(chieu==1){
            txtChieu.setText("Khứ hồi");
            giaTien = (giaTien*2*95)/100;
        }
        else {
            txtChieu.setText("Một chiều");
        }


        txtDonGia.setText("$"+String.valueOf(giaTien));
    }
}
