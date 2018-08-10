import android.content.Context
import android.widget.ArrayAdapter

class UniqueArrayAdapter<T> : ArrayAdapter<T> {

    constructor(context: Context, layout: Int, items: List<T>) : super(context, layout, items.distinct())

}
