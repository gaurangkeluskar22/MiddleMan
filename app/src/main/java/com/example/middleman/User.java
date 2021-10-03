package com.example.middleman;

public class User {
    public String full_name, email_id,phone_number,user_address,profile_image_url;

    public User(){

    }

    public User(String full_name,String email_id,String phone_number,String user_address,String profile_image_url){
        this.full_name = full_name;
        this.email_id = email_id;
        this.phone_number = phone_number;
        this.user_address = user_address;
        this.profile_image_url = profile_image_url;
    }

}
