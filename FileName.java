package edu.csuft.wsw.mywangpan;

public class FileName {

    String name;  //文件名
    String hash;  //散列值

    public FileName(){

    }
    public FileName(String name , String hash){
        this.name=name;
        this.hash=hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



}
