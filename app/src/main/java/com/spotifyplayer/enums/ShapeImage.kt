package com.spotifyplayer.enums

enum class ShapeImage(val value: Int) {

    UNDEFINE(0),
    CIRCLE(1),
    ROUNDED(2),
    SQUARE(3);

    companion object {
        fun parse(value: Int): ShapeImage {
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