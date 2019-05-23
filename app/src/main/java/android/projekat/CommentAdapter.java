package android.projekat;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CommentAdapter extends ArrayAdapter<Comment> {
    private Activity mActivity;

    private Context mContext;
    private ArrayList<Comment> mComments;

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        super(context, 0, comments);

    }

    public void addComment(Comment comment) {
        mComments.add(comment);
        notifyDataSetChanged();
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

    public void update(ArrayList<Comment> comments) {
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
        Comment comment = (Comment) getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment, parent, false);
        }
        ViewHolder holder = new ViewHolder();
        holder.text = (TextView) convertView.findViewById(R.id.comment_text);
        holder.date = (TextView) convertView.findViewById(R.id.comment_date);

//        holder = (ViewHolder) convertView.getTag();

        holder.text.setText(comment.text);
        holder.date.setText(comment.date);

        return convertView;
    }

    private class ViewHolder {
        public TextView text = null;
        public TextView date = null;
    }
}
