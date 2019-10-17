package com.example.ttt.jukejam;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView listView;
    private Search_Adapter adapter;
    private ImageButton searchBtn;
    private EditText searchET;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SpotifyViewModel model;
    private String ACCESS_TOKEN;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new Search_Adapter(getContext(), (ArrayList<SongModel>)dummyData());

        Log.d("Got here", "got here search fragment oncreate: ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //String roomName = getArguments().getString("Room Name");
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        setupUI(v);

        return v;
    }

    public void setupUI(View rootView){
        listView = rootView.findViewById(R.id.searchLV);
        listView.setAdapter(adapter);
        searchBtn = rootView.findViewById(R.id.searchBtn);
        searchET = rootView.findViewById(R.id.searchET);
        setupListeners();
    }

    public void setupListeners(){
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SearchActivity", "SearchBtn onClick: ");
                String searchResultsString = "";//runSearchQuery(searchET.getText().toString());
                List<SongModel> searchResults = extractSearchQueryResults(searchResultsString);
                Log.d("SearchActivity", "onClick: searchResults.size = "+searchResults.size());
                adapter = new Search_Adapter(getContext(), (ArrayList<SongModel>)searchResults);
                listView.setAdapter(adapter);
                listView.invalidate();


            }
        });
    }

    private List<SongModel> dummyData(){
        List<SongModel> retVal = new ArrayList<SongModel>();
        SongModel s = new SongModel("Hey ya!","Outkast",null);
        for(int i=0;i<10;i++) s.upVote();
        retVal.add(s);
        s = new SongModel("Broken Arrows","Avicii",null);
        for(int i=0;i<5;i++) s.upVote();

        retVal.add(s);
        s = new SongModel("All Star","Smash Mouth",null);
        for(int i=0;i<3;i++) s.upVote();

        retVal.add(s);
        Log.d("PartyFragment", "got here: dummyData: ");
        return  retVal;
    }

    public String runSearchQuery(String input){
        //input: user search bar input, String
        //output: the json string returned by calling
        String retVal="";
        model = ViewModelProviders.of(getActivity()).get(SpotifyViewModel.class);
        ACCESS_TOKEN = model.getToken();
        Log.d("runSearchQuery", "token: "+ACCESS_TOKEN);
        OkHttpClient client = new OkHttpClient();
        //TODO convert input into url encoding(replaces spaces with %20) and insert into url
        String url = "https://api.spotify.com/v1/search?q=hey%20ya&type=track"; //-H \"Authorization: Bearer "+ACCESS_TOKEN+"\"";
        Log.d("URL", url);
        Headers mAuthHeader = Headers.of("Authorization", "Bearer " + ACCESS_TOKEN);
        Request request = new Request.Builder().get().headers(mAuthHeader).url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("Got here", "got here response failed");

                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()){
                    String searchResults = response.body().string();
                    Log.d("Got here", "got here response successful");
                    Log.d("Search Results", searchResults);
                }
                else{
                    Log.d("Got here", "got here response NOT successful");
                    String searchResults = response.body().string();
                    Log.d("Search Results", searchResults);

                }
            }
        });
        //TESTING
        //return retVal;
        return "";
    }

    public List<SongModel> extractSearchQueryResults(String jsonString){
        List<SongModel> retVal = new ArrayList<SongModel>();
        Log.d("SearchActivity", "extractSearchQueryResults: got here");
        //TESTING
        jsonString = loadFromJson();
        //TESTING
        Map map = new Gson().fromJson(jsonString, Map.class);
        Log.d("SearchActivity", "Map: "+map.toString());
        Map tracks = (Map)(map.get("tracks"));
        ArrayList<Map> items = (ArrayList<Map>) tracks.get("items");
        for(Map track: items){
            String name = (String)track.get("name");
            //ArrayList<Map> artists = (ArrayList<Map>) track.get("artist");
            String artist = (String)((ArrayList<Map>)(track.get("artists"))).get(0).get("name");
            String uri = (String)track.get("uri");

            SongModel model = new SongModel(name,artist,uri);
            retVal.add(model);
            Log.d("SearchActivity", "extractSearchQueryResults: added song: "+name+" by "+" uri: "+uri);
        }
        Log.d("SearchActivity", "tracks: "+tracks);

        return retVal;
    }

    public String loadFromJson(){
        String json;
        try{
            InputStream is = getActivity().getAssets().open("heyya.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            json = new String(buffer,"UTF-8");
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
