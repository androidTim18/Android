package android.projekat;

class Comment {
    public String text;
    public String date;

    public Comment(String text, String date) {
        this.text = text;
        this.date = date;
    }
    public Comment(){
        this.date = "";
        this.text = "";
    };
}

