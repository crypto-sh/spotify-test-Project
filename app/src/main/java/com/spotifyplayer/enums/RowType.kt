package com.spotifyplayer.enums

enum class RowType(val value : Int) {

    UNDEFINE(-1),
    ALBUM(0),
    TRACK(1),
    PLAYLIST(2),
    ARTIST(3);

    companion object {
        fun parse(value: Int): RowType {
            if (value < 0) {
                return UNDEFINE
            }
            var items  = RowType.values()
            for (item in items){
                if (item.value == value){
                    return item
                }
            }
            return UNDEFINE
        }
    }
}