package com.example.my2048;

import android.media.Image;

public class Block {
    private int number;
    private Image image;
    public Block(Image image, int number){
        this.number = number;
        this.image = image;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public int getNumber() {
        return number;
    }
    public boolean issame(Block b){
        if (b.getNumber() == this.getNumber()){
            return true;
        }
        else
            return false;
    }
}
