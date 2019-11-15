package lv.dt.todoworkshop.ui

fun Int.formatWithZero() = if (this > 9) this.toString() else "0$this"