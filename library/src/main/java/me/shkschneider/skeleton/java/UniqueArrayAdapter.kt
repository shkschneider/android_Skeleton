import android.content.Context
import android.widget.ArrayAdapter

@Deprecated("Use distinct()", ReplaceWith(".distinct()"))
open class UniqueArrayAdapter<T>(
        context: Context,
        layout: Int,
        items: List<T>
) : ArrayAdapter<T>(context, layout, items.distinct())
