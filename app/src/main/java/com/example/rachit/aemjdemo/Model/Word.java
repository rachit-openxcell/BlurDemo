
package com.example.rachit.aemjdemo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Word {

    @SerializedName("blogWordID")
    @Expose
    private Integer blogWordID;
    @SerializedName("wordName")
    @Expose
    private String wordName;
    @SerializedName("wordDescription")
    @Expose
    private String wordDescription;
    @SerializedName("iBlogID")
    @Expose
    private Integer iBlogID;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Word() {
    }

    /**
     * 
     * @param wordDescription
     * @param blogWordID
     * @param wordName
     * @param iBlogID
     */
    public Word(Integer blogWordID, String wordName, String wordDescription, Integer iBlogID) {
        super();
        this.blogWordID = blogWordID;
        this.wordName = wordName;
        this.wordDescription = wordDescription;
        this.iBlogID = iBlogID;
    }

    public Integer getBlogWordID() {
        return blogWordID;
    }

    public void setBlogWordID(Integer blogWordID) {
        this.blogWordID = blogWordID;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public String getWordDescription() {
        return wordDescription;
    }

    public void setWordDescription(String wordDescription) {
        this.wordDescription = wordDescription;
    }

    public Integer getIBlogID() {
        return iBlogID;
    }

    public void setIBlogID(Integer iBlogID) {
        this.iBlogID = iBlogID;
    }

}
