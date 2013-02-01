package com.sfeir.cloudsqlpoc.entities;

import com.google.appengine.api.datastore.Key;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity class or POJO representing a Contact
 * @author saroop
 *
 */
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key id;
    private String title;
    private String text;

    public Article() {
    }

    public Article(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public Key getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}