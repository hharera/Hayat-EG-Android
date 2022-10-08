package com.harera.dwaa.common.extension

import it.sephiroth.android.library.rangeseekbar.RangeSeekBar

fun RangeSeekBar.onProgressChanged(onChanged: (start: Int, end: Int) -> Unit) {
    this.setOnRangeSeekBarChangeListener(object : RangeSeekBar.OnRangeSeekBarChangeListener {
        override fun onProgressChanged(p0: RangeSeekBar?, p1: Int, p2: Int, p3: Boolean) {
            onChanged(p1, p2)
        }

        override fun onStartTrackingTouch(p0: RangeSeekBar?) {
            TODO("Not yet implemented")
        }

        override fun onStopTrackingTouch(p0: RangeSeekBar?) {
            TODO("Not yet implemented")
        }
    })
}