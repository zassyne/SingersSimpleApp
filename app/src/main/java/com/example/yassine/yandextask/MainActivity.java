package com.example.yassine.yandextask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends Activity {


    private static final String TAG = "MainActivity";
    private static final String URL = "http://cache-nnov05.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";
    private static final String fileName_cache = "json_cache";
    private ArrayList<Artist> artistsList;


    /*
        This class performs the following background operations:
            1 - make a web request to get the JSON text.
            2 - parse the obtained JSON text into our array.
     */
    public class ArtistsGetter extends AsyncTask<Void, Void, Void> {

        private static final String TAG = "ArtistsGetter";
        private ProgressDialog progressDialog;


        public ArtistsGetter(MainActivity activity) {
            progressDialog = new ProgressDialog(activity);
        }
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading data ...");
            progressDialog.show();
        }




        @Override
        protected Void doInBackground(Void... args) {

            WebRequest wr = new WebRequest();


            String json = wr.makeWebRequest(URL);
            parseJSON(json, artistsList);


            return null;
        }

        private void parseJSON(String json, ArrayList<Artist> artists) {

            if(json == null || json.isEmpty()) {
                Log.d(TAG, "JSON text is null");

                try {
                    artistsList = (ArrayList<Artist>) CacheHelper.readObject(MainActivity.this, MainActivity.fileName_cache);
                }
                catch(IOException exception) {
                    exception.printStackTrace();
                }
                catch(ClassNotFoundException exception) {
                    Log.e(TAG, exception.getMessage());
                }

                return ;
            }
            try {

                JSONArray jsonArray = new JSONArray(json);

                String id = "", name="", small = "", big = "", description="", link="";
                int tracks, albums;
                int i=0;
                for(i = 0; i<jsonArray.length(); i++) {
                    JSONObject ob = jsonArray.getJSONObject(i);

                    id = ob.getString("id");
                    name = ob.getString("name");
                    tracks = Integer.valueOf(ob.getString("tracks"));
                    albums = Integer.valueOf(ob.getString("albums"));

                    JSONArray _genres = ob.getJSONArray("genres");
                    ArrayList<String> genres = new ArrayList<>();
                    for(int t = 0; t<_genres.length(); t++)
                        genres.add(_genres.getString(t));


                    JSONObject _covers= ob.getJSONObject("cover");
                    ArrayList<String> covers = new ArrayList<>();
                    covers.add(_covers.getString("small"));
                    covers.add(_covers.getString("big"));

                    try {
                        small = covers.get(0);
                        big = covers.get(1);
                        description = ob.getString("description");
                        link = ob.getString("link");
                    }
                    catch(Exception e) {

                    }

                    Artist artist = new Artist.ArtistBuilder(id, name)
                            .genres(genres)
                            .tracks(tracks)
                            .albums(albums)
                            .link(link)
                            .description(description)
                            .small(small)
                            .big(big).build();


                    artistsList.add(artist);
                }

            } catch(Exception e) {
                Log.e(TAG, e.getMessage());
            }
            try {
                CacheHelper.writeObject(MainActivity.this, MainActivity.fileName_cache, artistsList);

            } catch (IOException exception) {
                Log.e(TAG, exception.getMessage());
            }

        }

        @Override
        protected  void onPostExecute(Void result) {

            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            ListView ls = (ListView) MainActivity.this.findViewById(R.id.artists_list);

            ls.setAdapter(new ArtistAdapter(MainActivity.this, R.layout.activity_main_list, artistsList));


            ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Artist artist = artistsList.get(position);
                    Intent i = new Intent(MainActivity.this, DetailActivity.class);

                    i.putExtra("artist", artist);
                    startActivity(i);
                }
            });




        }
    }
    private static class CacheHelper {

        public static void writeObject(Context context, String fileName, Object object) throws IOException {

            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        }

        public static Object readObject(Context context, String fileName) throws IOException, ClassNotFoundException {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            Object object = ois.readObject();
            fileInputStream.close();
            return object;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        artistsList = new ArrayList<>();

        new ArtistsGetter(this).execute();

    }
}
