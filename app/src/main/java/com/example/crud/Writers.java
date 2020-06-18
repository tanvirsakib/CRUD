package com.example.crud;

public class Writers {

    String writerId;
    String writerName;
    String writerType;


    public Writers(){

    }

    public Writers(String writerId, String writerName, String writerType) {
        this.writerId = writerId;
        this.writerName = writerName;
        this.writerType = writerType;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getWriterType() {
        return writerType;
    }

    public void setWriterType(String writerType) {
        this.writerType = writerType;
    }
}
