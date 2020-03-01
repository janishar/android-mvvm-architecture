package com.mindorks.framework.mvvm.data.model.betting

import com.mindorks.framework.mvvm.data.model.db.User

data class BetContest(
    val betters: List<User>,
    val winners: List<User>,

    val openingDateTime: Long,
    val closingDateTime: Long,

    val isOpeningLine: Boolean,
    var bets: Map<Long, Bet>
)