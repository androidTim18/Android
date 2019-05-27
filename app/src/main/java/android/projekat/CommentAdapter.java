package android.projekat;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CommentAdapter extends BaseAdapter {
    protected Activity mActivity;
    protected Context mContext;
    protected ArrayList<Comment> mComments;
/*
    public CommentAdapter(Context context, Comment[] comments) {
        super(context, 0, comments);

    }*/
    public CommentAdapter(Context context) {
        mContext = context;
        mComments = new ArrayList<Comment>();
    }

    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public Comment getItem(int position) {
        Comment rv = null;
        try {
            rv = mComments.get(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return rv;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(Comment[] comments) {
        mComments.clear();
        if(comments != null) {
            for(Comment comment : comments) {
                mComments.add(comment);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.comment, null);
            ViewHolder holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.comment_text);
            holder.date = (TextView) view.findViewById(R.id.comment_date);
            view.setTag(holder);
        }

        Comment comment= (Comment) getItem(position);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(comment.text);
        holder.date.setText(comment.date);

        return view;
    }

    private class ViewHolder {
        public TextView text = null;
        public TextView date = null;
    }
}
