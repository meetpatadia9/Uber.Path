package com.ipsmeet.uberpath.dataclass

data class HistoryDataClass(
    val date: String,
    val transaction: List<TransactionDataClass>
)
