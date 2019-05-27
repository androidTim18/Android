package android.projekat;

import java.util.Random;

class Comment {
    public String text;
    public String date;
    public String adId;
    public String commentId;

    public Comment(String text, String date, String adId) {
        this.text = text;
        this.date = date;
        this.adId = adId;
        Random r = new Random();

        Integer a = (r.nextInt((9999-1000)+1)+1000);
        this.commentId = a.toString();
    }
    public Comment(String text, String date, String adId, String id) {
        this.text = text;
        this.date = date;
        this.adId = adId;
        this.commentId = id;
    }

    public Comment(){
        this.date = "";
        this.text = "";
        this.commentId="";
        this.adId="";
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}

