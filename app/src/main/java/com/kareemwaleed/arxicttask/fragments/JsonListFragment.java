package com.kareemwaleed.arxicttask.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kareemwaleed.arxicttask.R;
import com.kareemwaleed.arxicttask.adapters.JsonListAdapter;
import com.kareemwaleed.arxicttask.api_request.JsonParser;
import com.kareemwaleed.arxicttask.api_request.Request;
import com.kareemwaleed.arxicttask.api_request.RequestParameters;
import com.kareemwaleed.arxicttask.models.JsonListItem;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class JsonListFragment extends Fragment {

    private ListView listView;
    private JsonListAdapter jsonListAdapter;
    private RelativeLayout connectionProblemLayout;

    public JsonListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_json_list, container, false);
        listView = (ListView) frameLayout.findViewById(R.id.json_list);
        connectionProblemLayout = (RelativeLayout) frameLayout.findViewById(R.id.connection_problem);
        connectionProblemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionBasedDirector();
            }
        });
        connectionBasedDirector();
        return frameLayout;
    }

    private void prepareListView(){
        Request request = new Request();
        Map<String, Object> requestResult = new HashMap<>();
        ArrayList<JsonListItem> jsonListItems = new ArrayList<>();
        try {
            requestResult = JsonParser.parser(request.execute(new RequestParameters("https://jsonplaceholder.typicode.com/posts"
                    , "GET")).get());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Iterator setIterator = requestResult.keySet().iterator();
        while (setIterator.hasNext()){
            Map<String, Object> tempMap = (Map<String, Object>) requestResult.get(setIterator.next());
            JsonListItem tempJsonListItem = new JsonListItem();
            tempJsonListItem.setUserID(String.valueOf(tempMap.get("userId")));
            tempJsonListItem.setID(String.valueOf(tempMap.get("id")));
            tempJsonListItem.setTitle(String.valueOf(tempMap.get("title")));
            tempJsonListItem.setBody(String.valueOf(tempMap.get("body")));
            jsonListItems.add(tempJsonListItem);
        }
        jsonListAdapter = new JsonListAdapter(getActivity(), jsonListItems);
        listView.setAdapter(jsonListAdapter);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void connectionBasedDirector(){
        if(isNetworkAvailable()) {
            connectionProblemLayout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            prepareListView();
        }
        else{
            Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_LONG).show();
            listView.setVisibility(View.GONE);
            connectionProblemLayout.setVisibility(View.VISIBLE);
        }
    }
}
