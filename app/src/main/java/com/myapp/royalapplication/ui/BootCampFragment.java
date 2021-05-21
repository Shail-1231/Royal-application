package com.myapp.royalapplication.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.royalapplication.R;

import org.w3c.dom.Text;


public class BootCampFragment extends Fragment {

    TextView bootCamp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_boot_camp, container, false);
        bootCamp = rootView.findViewById(R.id.tv_boot_camp_link);
        bootCamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = bootCamp.getText().toString();
                Intent downloadIntent = new Intent();
                downloadIntent.setData(Uri.parse(url));
                startActivity(downloadIntent);
            }
        });
        return rootView;
    }
}