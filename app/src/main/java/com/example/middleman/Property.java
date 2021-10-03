package com.example.middleman;

import java.util.UUID;

public class Property {
    public String document_id;
    public String userID;
    public Double latitude,longitude;
    public String property_name,property_email_id,property_BHK,property_area,property_price,property_address,property_owner_name,property_locality,property_phone_number;
    public String upload_front_image_uri,upload_first_image_uri,upload_second_image_uri,upload_third_image_uri,upload_fourth_image_uri;
    public Property(){

    }

    public Property(String document_id,String userID,String property_name,String property_email_id,String property_BHK,String property_area,String property_price,String property_address,String property_owner_name,String property_locality,String property_phone_number ,Double latitude,Double longitude,String upload_front_image_uri,String upload_first_image_uri,String upload_second_image_uri,String upload_third_image_uri,String upload_fourth_image_uri){
        this.document_id=document_id;
        this.userID = userID;
        this.property_email_id = property_email_id;
        this.property_address = property_address;
        this.property_area = property_area;
        this.property_BHK = property_BHK;
        this.property_locality = property_locality;
        this.property_name = property_name;
        this.property_owner_name = property_owner_name;
        this.property_price = property_price;
        this.property_phone_number = property_phone_number;
        this.latitude = latitude;
        this.longitude = longitude;
        this.upload_front_image_uri = upload_front_image_uri;
        this.upload_first_image_uri = upload_first_image_uri;
        this.upload_second_image_uri = upload_second_image_uri;
        this.upload_third_image_uri = upload_third_image_uri;
        this.upload_fourth_image_uri = upload_fourth_image_uri;
    }
}
