package com.mindorks.framework.mvvm.data.model.betting

import com.mindorks.framework.mvvm.data.model.db.User

data class Bet(
    val betters: List<User>,
    val winners: List<User>,

    val openingDateTime: Long,
    val closingDateTime: Long,

    val isOpeningLine: Boolean,
    val spread: Int? = null,
    val overUnder: Int? = null,
    val lineOdds: String
) {
    enum class BetType {
        SPREAD,
        MONEY_LINE,
        OVER_UNDER,
        PURE_VIG
    }
}