package com.example.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private int width,height,count=0;
    private String x="X",o="O";
    private boolean xturn=true,go=true,draw=false;
    private boolean[] isFillable;
    private TextView turn;
    private ImageView[] imgView=new ImageView[9];
    private View view;
    private String[]  winHolder=new String[9];
    private int[][] winningStatus={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};


    public GridAdapter(Context context,int width,int height,TextView turn){
        this.context=context;
        this.width=width;
        this.height=height;
        this.turn=turn;
        isFillable=new boolean[9];
        startGame("O");
    }

    private void startGame(String val){
        draw=true;
       for(int i=0;i<winHolder.length;i++){
           winHolder[i]="";
           isFillable[i]=true;
       }
       if(val.equals("X"))
        turn.setText("Current Turn : O");
       else
           turn.setText("Current Turn : X");
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

       this.view=view;
        if(view==null){
           view= LayoutInflater.from(context).inflate(R.layout.grid_container,viewGroup,false);
           final ImageView  imageView=view.findViewById(R.id.playerImage);
           imageView.setImageResource(R.drawable.box);
           imageView.setTag(i);
           isFillable[i]=true;

           if(go) {
               imgView[i] = imageView;
           }
           if(i==8){go=false;}

           imageView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(isFillable[(int) view.getTag()]) {
                       if (xturn) {
                           imageView.setImageResource(R.drawable.x);
                           turn.setText("Current Turn : O");
                           winHolder[(int)view.getTag()]=x;

                       } else {
                           imageView.setImageResource(R.drawable.o);
                           turn.setText("Current Turn : X");
                           winHolder[(int)view.getTag()]=o;
                       }
                       isFillable[(int) view.getTag()]=false;
                       xturn=!xturn;
                       checked();
                   }
               }
           });

        }

        return view;
    }

    private void gameWin(String val){
        turn.setText("Game win by : "+val);

        for(int i=0;i<9;i++){
            imgView[i].setImageResource(R.drawable.box);
        }

        startGame(val);
    }

    private void popUpDialog(String val){
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("winning status");

        TextView text=dialog.findViewById(R.id.dialog_message);
        text.setText(val);

        TextView dialogButton=dialog.findViewById(R.id.ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.show();
        gameWin(val);
    }

    private void checked(){

        count=0;

       for(int[] win:winningStatus){
           if(winHolder[win[0]].equals(winHolder[win[1]])&&winHolder[win[0]].equals(winHolder[win[2]])&&!winHolder[win[0]].equals("")){
                popUpDialog(winHolder[win[0]]+" Won the game..");
           }
       }

       for(int i=0;i<winHolder.length;i++){
           if(winHolder[i].equals("")){
             count=0;
           }
           count+=1;
       }

       if(count==(winHolder.length)){
          popUpDialog(" game draw");
       }
        Log.d( "checked: ",""+count);
    }

}