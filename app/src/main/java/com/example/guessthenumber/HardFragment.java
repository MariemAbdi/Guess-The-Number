package com.example.guessthenumber;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class HardFragment extends Fragment {

    RecyclerView recyclerView;
    List<Score>list=new ArrayList<Score>();
    List<String>nicknames=new ArrayList<String>();
    List<Integer>scores=new ArrayList<Integer>();
    List<String>dates=new ArrayList<String>();
    List<Integer>ranks=new ArrayList<Integer>();
    List<String>attempts=new ArrayList<String>();
    List<String>spent=new ArrayList<String>();

    DatabaseHandler db;
    int rank=1;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HardFragment() {
        // Required empty public constructor
    }


    public static HardFragment newInstance(String param1, String param2) {
        HardFragment fragment = new HardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        db = new DatabaseHandler(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_hard, container, false);

        get_Data();

        recyclerView = view.findViewById(R.id.recyclerview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(new MyRecyclerAdapter(nicknames,dates,scores,ranks,attempts,spent,getActivity().getApplicationContext()));
        //set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    public void get_Data(){

        //db.deleteAll();
        list = db.getAllScores_HD();

        if(list.size()==0){
            Toast.makeText(getActivity().getApplicationContext(),"Nothing To Show Yet.",Toast.LENGTH_LONG).show();
        }else{
            ranks.add(0,rank);
            for(int i=1;i<list.size();i++){
                int current_score=list.get(i).getScore();
                int current_att=Integer.parseInt(list.get(i).getAttempts());
                String current_timeSpent=list.get(i).getTime_spent();

                int prev_score=list.get(i-1).getScore();
                int prev_att=Integer.parseInt(list.get(i-1).getAttempts());
                String prev_timeSpent=list.get(i-1).getTime_spent();

                if(current_score<prev_score){
                    rank++;
                }
                if(current_score==prev_score && current_att==prev_att && !current_timeSpent.equals(prev_timeSpent)){
                    rank++;
                }
                else if(current_score==prev_score && current_att!=prev_att){
                    rank++;
                }

                nicknames.add(i-1, list.get(i-1).getNickname());
                scores.add(i-1, list.get(i-1).getScore());
                dates.add(i-1,list.get(i-1).getDateTime());
                attempts.add(i-1,list.get(i-1).getAttempts());
                spent.add(i-1,list.get(i-1).getTime_spent());
                ranks.add(i,rank);

                if(i==list.size()-1){
                    nicknames.add(i, list.get(i).getNickname());
                    scores.add(i, list.get(i).getScore());
                    dates.add(i,list.get(i).getDateTime());
                    attempts.add(i,list.get(i).getAttempts());
                    spent.add(i,list.get(i).getTime_spent());
                    ranks.add(i,rank);
                }

            }
        }

    }
}