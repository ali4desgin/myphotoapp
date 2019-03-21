package com.app.libandroidapp.Plugin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.libandroidapp.LocalStorage.Requests;
import com.app.libandroidapp.R;

import java.util.List;

public class FileAdapter extends BaseAdapter {


    private Context context;
    private Context context2;
    private List<FileModel> list;
    public FileAdapter(Context context, List<FileModel> list){

        this.list = list;
        this.context = context;
        this.context2 = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int x, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.file_cell, viewGroup, false);
        }


        TextView username = view.findViewById(R.id.usernameID);
        TextView filesize = view.findViewById(R.id.filesizeID);
        TextView filename = view.findViewById(R.id.filenameID);
        Button deleteBtm = view.findViewById(R.id.downloadBtn);
//        Button editBtn = view.findViewById(R.id.downloadBtn2);
        ImageView fileimage = view.findViewById(R.id.imageView);


        deleteBtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getString(R.string.alerts_dialog_title));
                builder.setMessage("are you sure ? ");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setPositiveButton("cancel",null);
                builder.setNegativeButton(R.string.ok_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       Requests.deletefile(context2,String.valueOf(list.get(x).getId()));


                       // Log.i("item id", list.get(x).getFilename());

                        Intent intent = new Intent(context2,context2.getClass());
                        context2.startActivity(intent);////
                    }
                });
                builder.show();




            }
        });



//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context,context.getClass());
//                context.startActivity(intent);
//
//            }
//        });



        username.setText(list.get(x).getUsername());
        filesize.setText(list.get(x).getFileSize());
        filename.setText(list.get(x).getFilename());
        filename.setText(list.get(x).getFilename());


        Bitmap bitmap = BitmapFactory.decodeByteArray(list.get(x).getFile(), 0, list.get(x).getFile().length);



        fileimage.setImageBitmap(bitmap);
       // username.setText(list.get(i).getUsername());


        return view;
    }



}
