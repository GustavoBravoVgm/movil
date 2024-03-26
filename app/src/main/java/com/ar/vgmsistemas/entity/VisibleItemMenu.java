package com.ar.vgmsistemas.entity;


public class VisibleItemMenu extends ItemMenu {

    private VisibleItemMenu[] subItems;
    private int imageResource;
    private int imageResourceTwo;
    private int type;
    private boolean open = false;
    private Integer father;

    public VisibleItemMenu(String itemName, VisibleItemMenu[] subItems, int imageResource, int imageResourceTwo, int type, int posItem, Integer father) {
        super(itemName, posItem, type);
        setSubItems(subItems);
        setImageResource(imageResource);
        setImageResourceTwo(imageResourceTwo);
        setFather(father);
    }

    public VisibleItemMenu[] getSubItems() {
        return subItems;
    }

    public void setSubItems(VisibleItemMenu[] mSubItems) {
        this.subItems = mSubItems;
    }

    public boolean haveSubItems() {
        if (subItems == null) {
            return false;
        } else if (subItems.length > 0) {
            return true;
        }
        return false;

    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getFather() {
        return father;
    }

    public void setFather(Integer father) {
        this.father = father;
    }

    public int getImageResourceTwo() {
        return imageResourceTwo;
    }

    public void setImageResourceTwo(int imageResourceTwo) {
        this.imageResourceTwo = imageResourceTwo;
    }
}
