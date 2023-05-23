package com.komikatow.komiku.utils;

public interface ItemRecyclerClick {

    void onClickListener(int pos);

    default boolean onLongClickListener(int pos) {
        return true;
    }
}
