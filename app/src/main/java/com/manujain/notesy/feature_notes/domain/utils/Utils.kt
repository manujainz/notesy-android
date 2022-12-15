package com.manujain.notesy.core

import com.manujain.notesy.feature_notes.domain.utils.NotesOrder
import com.manujain.notesy.feature_notes.domain.utils.OrderType

fun getNotesOrderFromStr(str: String, orderType: OrderType): NotesOrder {
    // TODO This approach requires refactor
    return if (str == "Title") {
        NotesOrder.Title(orderType)
    } else if (str == "Color")
        NotesOrder.Color(orderType)
    else
        NotesOrder.Date(orderType)
}
