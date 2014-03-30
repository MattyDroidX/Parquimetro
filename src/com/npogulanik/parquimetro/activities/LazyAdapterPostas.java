package com.npogulanik.parquimetro.activities;

import java.util.ArrayList;
import java.util.HashMap;

import com.npogulanik.parquimetro.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapterPostas extends BaseAdapter {
    
    private Context activity;
    private ArrayList<HashMap<String,String>>data;
    private static LayoutInflater inflater=null;
    
    public LazyAdapterPostas(Context a, ArrayList<HashMap<String,String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row_postas, null);

        TextView posta = (TextView)vi.findViewById(R.id.posta); 
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image_posta); 
        
        HashMap<String, String> item = new HashMap<String, String>();
        item = data.get(position);
        
        posta.setText(item.get(PromptPosta.KEY_NUMERO));
        
        if (item.get(PromptPosta.KEY_LETRA).equals("A")){
        	thumb_image.setImageResource(R.drawable.red_button_a);
        } else if (item.get(PromptPosta.KEY_LETRA).equals("B")){
        	thumb_image.setImageResource(R.drawable.red_button_b);
        } else if (item.get(PromptPosta.KEY_LETRA).equals("C")){
        	thumb_image.setImageResource(R.drawable.red_button_c);
        }else if (item.get(PromptPosta.KEY_LETRA).equals("D")){
        	thumb_image.setImageResource(R.drawable.red_button_d);
        }
        
        return vi;
    }
}