package com.example.guessthenumber;

import android.content.Context;

import androidx.annotation.NonNull;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>{

        List<String> nicknames;
        List<String> dates;
        List<Integer> scores;
        List<Integer> ranks;
        List<String> attempts;
        List<String> time;

    private Context context;

        public MyRecyclerAdapter(List<String> nicknames,List<String> dates, List<Integer> scores, List<Integer> ranks, List<String> attempts, List<String> time, Context context) {
            this.nicknames=nicknames;
            this.dates=dates;
            this.scores=scores;
            this.ranks=ranks;
            this.attempts=attempts;
            this.time=time;
            this.context=context;
        }

        @NonNull
        @Override
        public MyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.scoreboard_list_item,parent,false);
                ViewHolder viewHolder=new ViewHolder(view);
                return viewHolder;
                }

        @Override
        public void onBindViewHolder(@NonNull MyRecyclerAdapter.ViewHolder holder, final int position) {

                holder.nickname.setText(nicknames.get(position));
                holder.score.setText(String.valueOf(scores.get(position)));
                holder.rank.setText(String.valueOf(ranks.get(position)));
                holder.date.setText(dates.get(position));
                holder.attempt.setText(attempts.get(position));
                holder.spent.setText(time.get(position));

            if(ranks.get(position)<=10){
                holder.nickname.setTextColor(Color.parseColor("#d76d21"));
                holder.score.setTextColor(Color.parseColor("#d76d21"));
                holder.rank.setTextColor(Color.parseColor("#d76d21"));
            }else{
                holder.nickname.setTextColor(Color.parseColor("#000000"));
                holder.score.setTextColor(Color.parseColor("#000000"));
                holder.rank.setTextColor(Color.parseColor("#000000"));
            }
        }

        @Override
        public int getItemCount() {
                return nicknames.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView nickname,score, date,rank,attempt,spent;
            LinearLayout layout,info;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                nickname=itemView.findViewById(R.id.nickname_tv);
                score=itemView.findViewById(R.id.score_tv);
                date=itemView.findViewById(R.id.date_tv);
                rank=itemView.findViewById(R.id.rank_tv);
                attempt=itemView.findViewById(R.id.attempts_tv);
                spent=itemView.findViewById(R.id.timeneeded);
                layout=itemView.findViewById(R.id.layout_id);
                info=itemView.findViewById(R.id.additional_info);

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(info.getVisibility() == View.VISIBLE) {
                            info.setVisibility(View.GONE);
                        }else{
                            info.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }
}
