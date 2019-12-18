package com.studio.crm.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.JalobaAdapter;
import com.studio.crm.icgroup.Forms.JalobaForm;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsMainFragment extends Fragment {
    RecyclerView recyclerView;
    TextView notAnswered, ArchiveTextView, mainObjectTitle;
    List<JalobaForm> firstForm, secondForm, jalobaForms;
    JalobaAdapter adapter;
    FrameLayout progressLayout, addNewLayout;
    String type="", id;
    int chose=0, location=0;
    public CommentsMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        type=getArguments().getString("type");
        id=getArguments().getString("id");
        location=getArguments().getInt("location");
        View view=inflater.inflate(R.layout.fragment_comments_main, container, false);
        createViews(view);
        setFonttype();
        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);



        jalobaForms=new ArrayList<>();
        secondForm.addAll(jalobaForms);
        firstForm.addAll(jalobaForms);
        adapter=new JalobaAdapter(jalobaForms, false);
        recyclerView.setAdapter(adapter);

        setListeners();
        //getReq();
        setChose(0);
        return view;
    }

    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.commentsRecyclerView);
        notAnswered=(TextView) view.findViewById(R.id.notAnsweredTextView);
        ArchiveTextView=(TextView) view.findViewById(R.id.ArchiveTextView);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        addNewLayout=(FrameLayout) view.findViewById(R.id.addNewLayout);
        firstForm=new ArrayList<>();
        secondForm=new ArrayList<>();
        if (!type.equals("point")){
            addNewLayout.setVisibility(View.GONE);
        }
        else{
            addNewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment=new AddNewJalobaFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("id",id);
                    bundle.putInt("location",location);
                    fragment.setArguments(bundle);
                    ((MainActivity)getActivity()).setFragment(R.id.content_frame,fragment);
                }
            });
        }
    }
    private void setFonttype(){
        setTypeFace("it",mainObjectTitle);
        setTypeFace("demibold",notAnswered,ArchiveTextView);
        setTypeFace("light");
    }
    private void setTypeFace(String s, TextView... textViews){
        for(int i=0;i<textViews.length;i++){
            textViews[i].setTypeface((((MainActivity)getActivity()).getTypeFace(s)));
        }
    }
    private void setListeners(){
        notAnswered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setChose(0);
            }
        });
        ArchiveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setChose(1);
            }
        });
    }
    private void getReq(int a){
        progressLayout.setVisibility(View.VISIBLE);
        String s="&";
        if(a==0){
            s+="is_reply=False";
        }
        else{
            s+="is_reply=True";
        }
        String url=((MainActivity)getActivity()).MAIN_URL+"complaints/?"+type+"="+id+s;
        JsonArrayRequest objectRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressLayout.setVisibility(View.GONE);
            setInfo(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT " + ((MainActivity) getActivity()).token);
                return headers;
            }
        };
        ((MainActivity)getActivity()).requestQueue.add(objectRequest);
    }
    public void setChose(int a){
        chose=a;
        jalobaForms.clear();
        switch (a){
            case 0:

                //jalobaForms.addAll(firstForm);
                adapter.setAnswer(true);
                setBlackTextView(notAnswered);
                setGreyTextView(ArchiveTextView);
                //adapterChanged();
                break;
                default:
                    //jalobaForms.addAll(secondForm);
                    adapter.setAnswer(false);
                    setBlackTextView(ArchiveTextView);
                    setGreyTextView(notAnswered);
                    //adapterChanged();
                    break;
        }
        getReq(a);
    }
    private void adapterChanged(){
        adapter.notifyDataSetChanged();
    }
    private void setBlackTextView(TextView t){
        t.setTextColor(getActivity().getResources().getColor(R.color.black));
    }
    private void setGreyTextView(TextView t){
        t.setTextColor(getActivity().getResources().getColor(R.color.darkgrey));
    }
    private void setInfo(JSONArray array){
        try{

            jalobaForms.clear();
            secondForm.clear();
            firstForm.clear();
            for(int i=0;i<array.length();i++){

                JSONObject object=array.getJSONObject(i);
                JSONObject author=object.getJSONObject("author");
                if(!object.isNull("defendant")) {
                    JSONObject defendant = object.getJSONObject("defendant");
                }
                String content=object.getString("content");
                String id=object.getString("id");
                String authorrole=((MainActivity)getActivity()).positions.get(author.getString("role"));
                String name=author.getString("fullname");
                String created_at=object.getString("created_at");
                String created=((MainActivity)getActivity()).getdate(created_at);
                created=created.substring(0,created.length()-6);
                boolean arch=!object.isNull("reply_comment");
                JalobaForm jalobaForm=new JalobaForm(created,authorrole,name, authorrole, content);
                jalobaForm.setId(id);
                if(arch){
                    jalobaForm.setAnswered(true);
                }
                jalobaForms.add(jalobaForm);
            }
            adapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
