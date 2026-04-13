package com.venusilkmills.app.Model;

/**
 * Created by DELL on 09/01/2018.
 */
import java.util.ArrayList;

public class Item_List {

    private Item_List() {}

    static Item_List obj = null;
    public static Item_List instance() {
        if (obj == null) obj = new Item_List();
        return obj;
    }

    public ArrayList<Item_Help> cache;
}
