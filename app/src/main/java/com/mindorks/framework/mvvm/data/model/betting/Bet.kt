package com.mindorks.framework.mvvm.data.model.betting

import com.mindorks.framework.mvvm.data.model.db.User

data class Bet (
    val better: User,
    val amount: Int,
    val spread: Int? = null,
    val overUnder: Int? = null,
    val lineOdds: String,

    val betType: BetType,
    val betPlatform: BetPlatform
) {
    enum class BetType {
        SPREAD,
        MONEY_LINE,
        OVER_UNDER,
        PURE_VIG
    }

    enum class BetPlatform {
        INSTAGRAM,
        REDDIT,
        CHAT
    }
}
