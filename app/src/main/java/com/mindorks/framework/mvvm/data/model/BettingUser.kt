package com.mindorks.framework.mvvm.data.model

import com.mindorks.framework.mvvm.data.model.betting.BetContest
import com.mindorks.framework.mvvm.data.model.db.User

data class BettingUser (
    val user: User,
    val isVerifiedOver18: Boolean = false,

    var bettingBalance: Int = 0,
    var cumulativeBettingBalance: Int = 0,
    var lostBettingBalance: Int = 0,
    var wonBettingBalance: Int = 0,

    var currentBets: List<BetContest> = emptyList(),
    var winningBets: List<BetContest> = emptyList(),
    var losingBets: List<BetContest> = emptyList()
)
