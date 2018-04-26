package me.shkschneider.skeleton.java

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS,
        AnnotationTarget.FILE,
        AnnotationTarget.FIELD,
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.VALUE_PARAMETER,
        AnnotationTarget.CONSTRUCTOR,
        AnnotationTarget.LOCAL_VARIABLE,
        AnnotationTarget.ANNOTATION_CLASS)
annotation class SkHide(val enabled: Boolean = true)
