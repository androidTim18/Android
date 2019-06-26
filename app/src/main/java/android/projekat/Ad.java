package android.projekat;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.io.ByteArrayInputStream;

public class Ad {
    public String adId;
    public String dateAdded;
    public String species;
    public String breed;
    public String name;
    public String birthday;
    public String sex;
    public String location;
    public String owner;
    public String info;
    public String price;
    public int available;
    public int favorite;
    public Drawable photo;
    public ArrayList<Comment> comments;

//new Ad - fisrt time adding
    public Ad(String species, String breed, String name,
              String birthday, String sex, String location, String owner,
              String info, String price, int available, int favorite,
              byte[] imageInByte) {
        Random r = new Random();

        Integer a = (r.nextInt((9999-1000)+1)+1000);

        this.adId = a.toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
        this.dateAdded = df.format(c);
        this.species = species;
        this.breed = breed;
        this.name = name;
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(imageInByte);
        Drawable drw = Drawable.createFromStream(arrayInputStream, "photo");
        this.photo = drw;
        this.birthday = birthday;
        this.sex = sex;
        this.location = location;
        this.owner = owner;
        this.info = info;
        this.price = price;
        this.available = 1;
        this.favorite = 0;
    }
    public Ad(String species, String breed, String name,
              String birthday, String sex, String location, String owner,
              String info, String price, int available, int favorite,
              Drawable drw) {
        Random r = new Random();

        Integer a = (r.nextInt((9999 - 1000) + 1) + 1000);

        this.adId = a.toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
        this.dateAdded = df.format(c);
        this.species = species;
        this.breed = breed;
        this.name = name;
        this.photo = drw;
        this.birthday = birthday;
        this.sex = sex;
        this.location = location;
        this.owner = owner;
        this.info = info;
        this.price = price;
        this.available = available;
        this.favorite = favorite;
    }
    //Constructor with all param - imageInBytes
    public Ad(String species, String breed, String name,
              String birthday, String sex, String location, String owner,
              String info, String price, int available, int favorite,
              byte[] imageInByte, String adId, String dateAdded) {

        this.adId = adId;
        this.dateAdded = dateAdded;
        this.species = species;
        this.breed = breed;
        this.name = name;
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(imageInByte);
        Drawable drw = Drawable.createFromStream(arrayInputStream, "photo");
        this.photo = drw;
        this.birthday = birthday;
        this.sex = sex;
        this.location = location;
        this.owner = owner;
        this.info = info;
        this.price = price;
        this.available = available;
        this.favorite = favorite;
    }
    //Constructor with all param - drawable photo
    public Ad(String species, String breed, String name,
              String birthday, String sex, String location, String owner,
              String info, String price, int available, int favorite,
              Drawable drw, String adId, String dateAdded) {

        this.adId = adId;
        this.dateAdded = dateAdded;
        this.species = species;
        this.breed = breed;
        this.name = name;
        this.photo = drw;
        this.birthday = birthday;
        this.sex = sex;
        this.location = location;
        this.owner = owner;
        this.info = info;
        this.price = price;
        this.available = available;
        this.favorite = favorite;
    }
    public Ad(String species, String breed, String name, String date, String sex, String user, String info, String price, String favorite, String string, String jsonObjectString, String available, byte[] images){
    };

    public String getAdId() {
        return adId;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getPhoto() {
        return photo;
    }

    public void setPhoto(Drawable photo) {
        this.photo = photo;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int isAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int isFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
