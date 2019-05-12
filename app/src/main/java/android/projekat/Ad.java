package android.projekat;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

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
    public boolean available;
    public boolean favorite;
    public Drawable photo;


    public Ad(String adId, String dateAdded, String species, String breed, String name,
              String birthday, String sex, String location, String owner,
              String info, String price, boolean available, boolean favorite,
              Drawable photo) {
        this.adId = adId;
        this.dateAdded = dateAdded;
        this.species = species;
        this.breed = breed;
        this.name = name;
        this.photo = photo;
        this.birthday = birthday;
        this.sex = sex;
        this.location = location;
        this.owner = owner;
        this.info = info;
        this.price = price;
        this.available = available;
        this.favorite = favorite;
    }

    public Ad(){
    };

    public String getAdId() {
        return adId;
    }

    public void setAdId(String userId) {
        this.adId = userId;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }


}
