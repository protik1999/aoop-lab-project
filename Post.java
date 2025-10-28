package com.teamvoid.gocircle;

import javafx.geometry.Pos;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post  {
    private String postID;
    private String content;
    private String Username;
    private Blob blob;
    private String time;

    public Post(String postID, String content, String username, Blob blob,String  time) {
        this.postID = postID;
        this.content = content;
        Username = username;
        this.blob = blob;
        this.time =time;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }


//    @Override
//    public int compareTo(Post o) {
//        LocalDateTime thisObjTime = LocalDateTime.parse(this.time,DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
//        LocalDateTime ObjTime = LocalDateTime.parse(this.time,DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
//        if(thisObjTime.isAfter(ObjTime))
//        {
//            return 1;
//        }
//        else
//        {
//            return -1;
//        }
//    }
}
