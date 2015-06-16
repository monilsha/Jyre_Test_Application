package com.example.monil.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.zeromq.ZMsg;
import org.zyre.ZreInterface;

/**
 * Created by monil on 6/12/15.
 */
public class ZREAsync extends AsyncTask <Void, Void, ZMsg> {
    Context context;
    public ZREAsync (Context context){
        this.context = context;
    }

    @Override
    protected ZMsg doInBackground(Void... params) {
        ZreInterface inf = new ZreInterface();
        ZMsg incoming = inf.recv();
        String event = incoming.popString();
        String peer = incoming.popString();
        ZMsg outgoing = new ZMsg();
        outgoing.add(peer);
        outgoing.add("HELLO-1");
        inf.whisper(outgoing);

        while (true) {
            incoming = inf.recv();
            if (incoming == null)
                break;
            incoming.dump(System.out);
/*            Log.e("Event", incoming.popString());
            Log.e("Peer", incoming.popString());
            Log.e("Message", incoming.popString());*/
            break;
            //incoming.destroy();
        }
        //inf.destroy();
        return incoming;
    }




    @Override
    protected void onPostExecute(ZMsg received) {
        TextView dispevent = (TextView)((Activity)context).findViewById(R.id.event);
        TextView disppeer = (TextView)((Activity)context).findViewById(R.id.peer);
        TextView dispmsg = (TextView)((Activity)context).findViewById(R.id.message);
        dispevent.setText(received.popString());
        disppeer.setText(received.popString());
        dispmsg.setText(received.popString());
/*        Log.e("Event", received.popString());
        Log.e("Peer", received.popString());
        Log.e("Message", received.popString());*/
    }
}

