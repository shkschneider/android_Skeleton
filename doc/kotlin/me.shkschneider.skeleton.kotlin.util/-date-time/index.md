[kotlin](../../index.md) / [me.shkschneider.skeleton.kotlin.util](../index.md) / [DateTime](./index.md)

# DateTime

`open class DateTime : `[`Comparable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-comparable/index.html)`<`[`DateTime`](./index.md)`>`

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `DateTime(timeInMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = System.currentTimeMillis())`<br>`DateTime(year: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, month: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, day: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, hour: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, minute: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, second: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, millis: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0)` |

### Properties

| Name | Summary |
|---|---|
| [calendar](calendar.md) | `val calendar: `[`Calendar`](https://docs.oracle.com/javase/6/docs/api/java/util/Calendar.html) |
| [day](day.md) | `open val day: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [dayOfWeek](day-of-week.md) | `open val dayOfWeek: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [hour](hour.md) | `open val hour: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [millis](millis.md) | `open val millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [minute](minute.md) | `open val minute: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [month](month.md) | `open val month: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [second](second.md) | `open val second: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [timestamp](timestamp.md) | `val timestamp: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [timeZone](time-zone.md) | `open val timeZone: `[`TimeZone`](https://docs.oracle.com/javase/6/docs/api/java/util/TimeZone.html) |
| [year](year.md) | `open val year: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Functions

| Name | Summary |
|---|---|
| [after](after.md) | `infix fun after(other: `[`DateTime`](./index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [before](before.md) | `infix fun before(other: `[`DateTime`](./index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [compareTo](compare-to.md) | `open fun compareTo(other: `[`DateTime`](./index.md)`): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [equals](equals.md) | `open fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | `open fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [on](on.md) | `infix fun on(other: `[`DateTime`](./index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [onOrAfter](on-or-after.md) | `infix fun onOrAfter(other: `[`DateTime`](./index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [onOrBefore](on-or-before.md) | `infix fun onOrBefore(other: `[`DateTime`](./index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [toCalendar](to-calendar.md) | `fun toCalendar(): `[`Calendar`](https://docs.oracle.com/javase/6/docs/api/java/util/Calendar.html) |
| [toMutableDateTime](to-mutable-date-time.md) | `fun toMutableDateTime(): `[`MutableDateTime`](../-mutable-date-time/index.md) |
| [toString](to-string.md) | `open fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [toUTC](to-u-t-c.md) | `fun toUTC(): `[`DateTime`](./index.md) |

### Companion Object Properties

| Name | Summary |
|---|---|
| [AM](-a-m.md) | `const val AM: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [APRIL](-a-p-r-i-l.md) | `const val APRIL: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [AUGUST](-a-u-g-u-s-t.md) | `const val AUGUST: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [DECEMBER](-d-e-c-e-m-b-e-r.md) | `const val DECEMBER: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [epoch](epoch.md) | `val epoch: `[`DateTime`](./index.md) |
| [FEBRUARY](-f-e-b-r-u-a-r-y.md) | `const val FEBRUARY: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [FRIDAY](-f-r-i-d-a-y.md) | `const val FRIDAY: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [JANUARY](-j-a-n-u-a-r-y.md) | `const val JANUARY: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [JULY](-j-u-l-y.md) | `const val JULY: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [JUNE](-j-u-n-e.md) | `const val JUNE: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [MARCH](-m-a-r-c-h.md) | `const val MARCH: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [MAY](-m-a-y.md) | `const val MAY: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [MONDAY](-m-o-n-d-a-y.md) | `const val MONDAY: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [NOVEMBER](-n-o-v-e-m-b-e-r.md) | `const val NOVEMBER: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [now](now.md) | `val now: `[`DateTime`](./index.md) |
| [OCTOBER](-o-c-t-o-b-e-r.md) | `const val OCTOBER: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [PM](-p-m.md) | `const val PM: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [SATURDAY](-s-a-t-u-r-d-a-y.md) | `const val SATURDAY: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [SEPTEMBER](-s-e-p-t-e-m-b-e-r.md) | `const val SEPTEMBER: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [SUNDAY](-s-u-n-d-a-y.md) | `const val SUNDAY: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [THURSDAY](-t-h-u-r-s-d-a-y.md) | `const val THURSDAY: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [timestamp](timestamp.md) | `val timestamp: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [TUESDAY](-t-u-e-s-d-a-y.md) | `const val TUESDAY: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [WEDNESDAY](-w-e-d-n-e-s-d-a-y.md) | `const val WEDNESDAY: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Inheritors

| Name | Summary |
|---|---|
| [MutableDateTime](../-mutable-date-time/index.md) | `class MutableDateTime : `[`DateTime`](./index.md)`, `[`Comparable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-comparable/index.html)`<`[`DateTime`](./index.md)`>` |
