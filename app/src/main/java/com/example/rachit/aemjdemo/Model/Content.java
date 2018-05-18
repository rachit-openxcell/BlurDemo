
package com.example.rachit.aemjdemo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Content {

    @SerializedName("blogContentId")
    @Expose
    private Integer blogContentId;
    @SerializedName("contentTitle")
    @Expose
    private String contentTitle;
    @SerializedName("contentImage")
    @Expose
    private String contentImage;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("word")
    @Expose
    private List<Word> word = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Content() {
    }

    /**
     * 
     * @param contentImage
     * @param contentTitle
     * @param description
     * @param word
     * @param blogContentId
     */
    public Content(Integer blogContentId, String contentTitle, String contentImage, String description, List<Word> word) {
        super();
        this.blogContentId = blogContentId;
        this.contentTitle = contentTitle;
        this.contentImage = contentImage;
        this.description = description;
        this.word = word;
    }

    public Integer getBlogContentId() {
        return blogContentId;
    }

    public void setBlogContentId(Integer blogContentId) {
        this.blogContentId = blogContentId;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentImage() {
        return contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Word> getWord() {
        return word;
    }

    public void setWord(List<Word> word) {
        this.word = word;
    }

}
