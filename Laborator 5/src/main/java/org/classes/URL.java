package org.classes;

import java.util.ArrayList;

public class URL {
    private String user;
    private ArrayList<String> documents;
    public URL(String user, ArrayList<String> documents) {
        this.user = user;
        this.documents = documents;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public ArrayList<String> getDocuments() {
        return this.documents;
    }
    public void setDocuments(ArrayList<String> documents) {
        this.documents = documents;
    }
}