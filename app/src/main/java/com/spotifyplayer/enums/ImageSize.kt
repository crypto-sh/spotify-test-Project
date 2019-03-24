package com.spotifyplayer.enums


enum class ImageSize(val value : Int) {

    UNDEFINE(0),
    SMALL(64),
    NORMAL(300),
    BIG(640);

    companion object {
        fun parse(value: Int): ImageSize {
            if (value == 0) {
                return UNDEFINE
            }
            var items  = values()
            for (item in items){
                if (item.value == value){
                    return item
                }
            }
            return UNDEFINE
        }
    }
}