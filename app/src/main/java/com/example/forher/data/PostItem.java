package com.example.forher.data;

public class PostItem {
    String userName;
    int userImage;
    String discription;
    int postImage;
    int likes;

    public PostItem(String userName, int userImage, String discription, int postImage, int likes) {
        this.userName = userName;
        this.userImage = userImage;
        this.discription = discription;
        this.postImage = postImage;
        this.likes = likes;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserImage() {
        return userImage;
    }

    public String getDiscription() {
        return discription;
    }

    public int getPostImage() {
        return postImage;
    }

    public int getLikes() {
        return likes;
    }
}
