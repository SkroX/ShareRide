package com.example.rajeev.shareride.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajeev.shareride.R;
import com.example.rajeev.shareride.models.Post;

import org.w3c.dom.Text;

import java.util.Arrays;

/**
 * Created by rajeev on 4/3/18.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public TextView mDateView;
    public TextView mTimeView;
    public TextView mPerson1View;
    public TextView mPerson2View;
    public TextView mPerson3View;
    public TextView mPerson4View;
    public TextView mPerson5View;
    public TextView mContact1View;
    public TextView mContact2View;
    public TextView mContact3View;
    public TextView mContact4View;
    public TextView mContact5View;

    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.post_title);
        authorView = itemView.findViewById(R.id.post_author);
        mDateView = itemView.findViewById(R.id.text_date);
        mTimeView = itemView.findViewById(R.id.text_time);
        mPerson1View = itemView.findViewById(R.id.person_1_text);
        mPerson2View = itemView.findViewById(R.id.person_2_text);
        mPerson3View = itemView.findViewById(R.id.person_3_text);
        mPerson4View = itemView.findViewById(R.id.person_4_text);
        mPerson5View = itemView.findViewById(R.id.person_5_text);
        mContact1View = itemView.findViewById(R.id.contact_1_text);
        mContact2View = itemView.findViewById(R.id.contact_2_text);
        mContact3View = itemView.findViewById(R.id.contact_3_text);
        mContact4View = itemView.findViewById(R.id.contact_4_text);
        mContact5View = itemView.findViewById(R.id.contact_5_text);
    }

    public void bindToPost(Post post) {

        titleView.setText(post.title);
        mDateView.setText(post.dateOfJourney);
        mTimeView.setText(post.timeOfJourney);

        String[] displayBody = post.body.split("@");
        mPerson1View.setText(displayBody[0].split("#")[0]);
        mContact1View.setText(displayBody[0].split("#")[1]);
        

        if(displayBody[1] != "#"){
            if (displayBody[1].split("#").length > 1 ){
                mPerson2View.setText(displayBody[1].split("#")[0]);
                mContact2View.setText(displayBody[1].split("#")[1]);
            }
            else{
                mPerson2View.setText(displayBody[1].replace("#", ""));
                mContact2View.setText("");
            }
        }
        if(displayBody[2] != "#"){
            if (displayBody[2].split("#").length > 1 ){
                mPerson3View.setText(displayBody[2].split("#")[0]);
                mContact3View.setText(displayBody[2].split("#")[1]);
            }
            else{
                mPerson3View.setText(displayBody[2].replace("#", ""));
                mContact3View.setText("");
            }
        }
        if (displayBody[3] != "#") {
            if (displayBody[3].split("#").length > 1 ){
                mPerson4View.setText(displayBody[3].split("#")[0]);
                mContact4View.setText(displayBody[3].split("#")[1]);
            }
            else{
                mPerson4View.setText(displayBody[3].replace("#", ""));
                mContact4View.setText("");
            }
        }
        if (displayBody[4] != "#") {
            if (displayBody[4].split("#").length > 1 ){
                mPerson5View.setText(displayBody[4].split("#")[0]);
                mContact5View.setText(displayBody[4].split("#")[1]);
            }
            else{
                mPerson5View.setText(displayBody[4].replace("#", ""));
                mContact5View.setText("");
            }
        }
//        int c = 1;
//        for (int i = 1; i<displayBody.length ; i++)
//        {
//            if(displayBody[i].equalsIgnoreCase("#")){
//                continue;
//            }
//            else{
//                if (displayBody[i].split("#").length > 1){
//                    textViewsPerson[c].setText(displayBody[i].split("#")[0]);
//                    textViewsContact[c].setText(displayBody[i].split("#")[1]);
//                }
//                else {
//                    textViewsPerson[c].setText(displayBody[i].replace("#", ""));
//                    textViewsContact[c].setText("");
//                }
//                c++;
//            }
//        }
//        c = 0;
    }
}
