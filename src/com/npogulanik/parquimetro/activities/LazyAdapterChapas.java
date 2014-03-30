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

public class LazyAdapterChapas extends BaseAdapter {
    
    private Context activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    
    public LazyAdapterChapas(Context a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.list_row_chapas, null);

        TextView chapa = (TextView)vi.findViewById(R.id.chapa); 
        TextView credito = (TextView)vi.findViewById(R.id.credito); 
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); 
        
        HashMap<String, String> item = new HashMap<String, String>();
        item = data.get(position);
        
        // Setting all values in listview
        chapa.setText(item.get(PromptChapa.KEY_CHAPA));
        credito.setText("Credito: $ " + item.get(PromptChapa.KEY_CREDITO));
        switch (position){
        	case 0:
        		thumb_image.setImageResource(R.drawable.blue_button_1);
        		break;
        	case 1:
        		thumb_image.setImageResource(R.drawable.blue_button_2);
        		break;
        	case 2:
        		thumb_image.setImageResource(R.drawable.blue_button_3);
        		break;
        	case 3:
        		thumb_image.setImageResource(R.drawable.blue_button_4);
        		break;
        	case 4:
        		thumb_image.setImageResource(R.drawable.blue_button_5);
        		break;
        	case 5:
        		thumb_image.setImageResource(R.drawable.blue_button_6);
        		break;
        	case 6:
        		thumb_image.setImageResource(R.drawable.blue_button_7);
        		break;
        	case 7:
        		thumb_image.setImageResource(R.drawable.blue_button_8);
        		break;
        	case 8:
        		thumb_image.setImageResource(R.drawable.blue_button_9);
        		break;
        	default:
        		break;
        }
        
        return vi;
    }
}