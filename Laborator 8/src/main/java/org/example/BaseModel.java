package org.example;

public abstract class BaseModel {
    protected int id;

    public BaseModel() {}

    public BaseModel(int id)
    {
        this.id = id;
    }

    public abstract int getId();
    public abstract void setId(int id);

}