
package com.example.rachit.aemjdemo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Datum {

    @SerializedName("blogId")
    @Expose
    private Integer blogId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("authorName")
    @Expose
    private String authorName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("thumbImage")
    @Expose
    private String thumbImage;
    @SerializedName("content")
    @Expose
    private List<Content> content = new ArrayList<>();
    @SerializedName("categoryName")
    @Expose
    private String categoryName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param content
     * @param categoryName
     * @param title
     * @param thumbImage
     * @param authorName
     * @param blogId
     * @param image
     * @param url
     */
    public Datum(Integer blogId, String title, String url, String authorName, String image, String thumbImage, List<Content> content, String categoryName) {
        super();
        this.blogId = blogId;
        this.title = title;
        this.url = url;
        this.authorName = authorName;
        this.image = image;
        this.thumbImage = thumbImage;
        this.content = content;
        this.categoryName = categoryName;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
