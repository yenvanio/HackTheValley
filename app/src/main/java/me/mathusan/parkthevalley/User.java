package me.mathusan.parkthevalley;

import com.google.android.gms.maps.model.Marker;

import java.util.Collection;
import java.util.List;

/**
 * Created by Mathu on 2017-01-07.
 */

public class User  implements Serializable{

    private String email;
    private String name;
    private String phone;
    private double price;
    private List<Spot> spots;
//    private List<Marker> markers;


    public User() {
      /*Blank default constructor essential for Firebase*/
    }


    /*protected User(Parcel in) {
        email = in.readString();
        name = in.readString();
        phone = in.readString();
        price = in.readDouble();
        spots = in.readList(spots, new SpotCreator());
    }*/

    /*public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

//    public List<Marker> getMarkers(){
//        return markers;
//    }
//
//    public void setMarkers(List<Marker> markers){
//        this.markers = markers;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public class SpotCreator extends ClassLoader {


    }
}
