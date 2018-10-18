package me.shkschneider.skeleton.annotations

@Deprecated("Broken") // FIXME
@Retention(AnnotationRetention.SOURCE)
@Target(
        AnnotationTarget.CLASS,
        AnnotationTarget.ANNOTATION_CLASS,
        AnnotationTarget.TYPE_PARAMETER,
        AnnotationTarget.PROPERTY,
        AnnotationTarget.FIELD,
        AnnotationTarget.LOCAL_VARIABLE,
        AnnotationTarget.VALUE_PARAMETER,
        AnnotationTarget.CONSTRUCTOR,
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.TYPE,
        AnnotationTarget.EXPRESSION,
        AnnotationTarget.FILE,
        AnnotationTarget.TYPEALIAS
)
annotation class SkHide
