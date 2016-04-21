package com.example.yassine.singers;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by yassine on 21.04.16.
 */
public class DetailFragment extends Fragment {

    private Artist artist;
    TextView textView;
    ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {


        artist = (Artist) getArguments().getSerializable("artist");
        View v = inflater.inflate(R.layout.fragment_detail, parent, false);

        textView = (TextView) v.findViewById(R.id.detail_biography);
        imageView = (ImageView) v.findViewById(R.id.detail_image);

        textView.setText(artist.getDescription());
        Picasso.with(parent.getContext()).load(artist.getBigCover()).into(imageView);

        return v;

    }

    public void setData(Artist artist) {
        this.artist = artist;

        if(!this.isDetached()) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}
