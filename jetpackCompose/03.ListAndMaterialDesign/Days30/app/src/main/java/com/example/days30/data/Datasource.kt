package com.example.days30.data

import com.example.days30.R
import com.example.days30.model.Music

class Datasource(){
    fun loadMusicInfo():List<Music>{
        return listOf(
            Music(R.drawable.letter, R.string.title1, R.string.artist1, R.string.genre_rock, R.string.duration1),
            Music(R.drawable.window, R.string.title2, R.string.artist2, R.string.genre_indie, R.string.duration2),
            Music(R.drawable.play, R.string.title3, R.string.artist3, R.string.genre_rock, R.string.duration3),
            Music(R.drawable.firefly, R.string.title4, R.string.artist4, R.string.genre_rock, R.string.duration4),
            Music(R.drawable.a_day_of_mercury, R.string.title5, R.string.artist5, R.string.genre_rock, R.string.duration5),
            Music(R.drawable.no_pain, R.string.title6, R.string.artist6, R.string.genre_indie, R.string.duration6),
            Music(R.drawable.good, R.string.title7, R.string.artist7, R.string.genre_rock, R.string.duration7),
            Music(R.drawable.name, R.string.title8, R.string.artist8, R.string.genre_jpop, R.string.duration8),
            Music(R.drawable.horse_deer, R.string.title9, R.string.artist9, R.string.genre_jpop, R.string.duration9),
            Music(R.drawable.to_you, R.string.title10, R.string.artist10, R.string.genre_rock, R.string.duration10),
            Music(R.drawable.white_night, R.string.title11, R.string.artist11, R.string.genre_rock, R.string.duration11),
            Music(R.drawable.come_into_my_dream, R.string.title12, R.string.artist12, R.string.genre_balled, R.string.duration12),
            Music(R.drawable.fate, R.string.title13, R.string.artist13, R.string.genre_dance, R.string.duration13),
            Music(R.drawable.if_i_have_only_you, R.string.title14, R.string.artist14, R.string.genre_balled, R.string.duration14),
            Music(R.drawable.strawberry_cake, R.string.title15, R.string.artist15, R.string.genre_rock, R.string.duration15),
            Music(R.drawable.maybe, R.string.title16, R.string.artist16, R.string.genre_jpop, R.string.duration16),
            Music(R.drawable.zombie, R.string.title17, R.string.artist17, R.string.genre_rock, R.string.duration17),
            Music(R.drawable.higher, R.string.title18, R.string.artist18, R.string.genre_pop, R.string.duration18),
            Music(R.drawable.isnt_that_good, R.string.title19, R.string.artist19, R.string.genre_rock, R.string.duration19),
            Music(R.drawable.somehow, R.string.title20, R.string.artist20, R.string.genre_balled, R.string.duration20),
            Music(R.drawable.comedy, R.string.title21, R.string.artist21, R.string.genre_jpop, R.string.duration21),
            Music(R.drawable.ten_thousand_hours, R.string.title22, R.string.artist22, R.string.genre_pop, R.string.duration22),
            Music(R.drawable.polaroid_love, R.string.title23, R.string.artist23, R.string.genre_RB, R.string.duration23),
            Music(R.drawable.beyond_love, R.string.title24, R.string.artist24, R.string.genre_RB, R.string.duration24),
            Music(R.drawable.alike, R.string.title25, R.string.artist25, R.string.genre_cpop, R.string.duration25),
            Music(R.drawable.windy, R.string.title26, R.string.artist26, R.string.genre_cpop, R.string.duration26),
            Music(R.drawable.happiness, R.string.title27, R.string.artist27, R.string.genre_pop, R.string.duration27),
            Music(R.drawable.the_summer, R.string.title28, R.string.artist28, R.string.genre_balled, R.string.duration28),
            Music(R.drawable.rainbow, R.string.title29, R.string.artist29, R.string.genre_folk, R.string.duration29),
            Music(R.drawable.curious, R.string.title30, R.string.artist30, R.string.genre_RB, R.string.duration30)
        )
    }
}