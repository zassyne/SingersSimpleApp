package com.example.yassine.singers;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yassine.singers.Artist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by yassine on 16.04.16.
 */

public class ArtistAdapter extends ArrayAdapter<Artist> {

    private final Context context;
    private final ArrayList<Artist> artists;

    public ArtistAdapter(Context context, int id, ArrayList<Artist> artists) {
        super(context, id, artists);

        this.context = context;
        this.artists = artists;
    }

    private void contentTextViewSetter(TextView textView, int position) {

        Artist artist = artists.get(position);

        String name = artist.getName();
        int albums = artist.getAlbums();
        int tracks = artist.getTracks();
        ArrayList<String> genres = artist.getGenres();


        SpannableString spannedName = new SpannableString(name);
        spannedName.setSpan(new RelativeSizeSpan(2f), 0, spannedName.length(), 0);

        String _genresPhrase = "";

        if(genres != null)
            for(String genre : genres) {
                _genresPhrase += genre;
                if(!genres.get(genres.size() - 1).equals(genre))
                    _genresPhrase += ", ";

            }


        textView.append(spannedName);
        textView.append("\n");
        textView.append(_genresPhrase);
        textView.append("\n\n\n\n\n" + String.valueOf(albums) + " альбомов, ");
        textView.append(tracks + " песен");
    }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_main_list, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);

        /*try {
            artists.get(position).getCover().setSmallCover(Picasso.with(context).load(artists.get(position).getSmall()).get());
        } catch(Exception e) {

         }*/
        //Picasso.with(context).load(artists.get(position).getSmall()).into(imageView);

         Picasso.with(context).load(artists.get(position).getSmallCover()).into(imageView);
        contentTextViewSetter(textView, position);



        return  rowView;
    }
}
