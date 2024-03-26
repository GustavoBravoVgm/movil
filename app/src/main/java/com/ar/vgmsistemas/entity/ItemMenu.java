package com.ar.vgmsistemas.entity;

public class ItemMenu {
    public static final int FRAGMENT = 1;
    public static final int ACTIVITY = 2;

    private String itemName;
    private int posItem;
    private int type;
    private boolean addToStack = false;

    public ItemMenu(String itemName, int posItem, int type) {
        setItemName(itemName);
        setPosItem(posItem);
        setType(type);
    }

    public ItemMenu(String itemName, int posItem, int type, boolean addToStack) {
        this(itemName, posItem, type);
        setAddToStack(addToStack);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPosItem() {
        return posItem;
    }

    public void setPosItem(int posItem) {
        this.posItem = posItem;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isAddToStack() {
        return addToStack;
    }

    public void setAddToStack(boolean addToStack) {
        this.addToStack = addToStack;
    }


}
