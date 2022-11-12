package xyz.mperminov.viewconstructorssample

import android.content.Context
import android.util.AttributeSet

class ChildCustomView : CustomView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(
        context,
        attrs,
        R.attr.defaultChildCustomViewStyleAttr
    )

}