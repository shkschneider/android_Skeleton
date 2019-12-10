package me.shkschneider.skeleton

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SkeletonLiveDataTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `default`() = with(SkeletonLiveData<Boolean>()) {
        val observer = this.mockObserve()
        assert(value, null, observer, 0)
        this += false // ++
        assert(value, false, observer, 1)
        this += true // ++
        assert(value, true, observer, 2)
    }

    @Test
    fun `initialValue`() = with(SkeletonLiveData<Boolean>(
            initialValue = true // ++
    )) {
        val observer = this.mockObserve()
        assert(value, true, observer, 1)
        this += false // ++
        assert(value, false, observer, 2)
        this += null // ++
        assert(value, null, observer, 3)
    }

    @Test
    fun `fallback`() = with(SkeletonLiveData<Boolean>(
            fallback = false
    )) {
        val observer = this.mockObserve()
        assert(value, null, observer, 0)
        this += true // ++
        assert(value, true, observer, 1)
        this += null // ++
        assert(value, false, observer, 2)
    }

    @Test
    fun `initialValue_fallback`() = with(SkeletonLiveData<Boolean>(
            initialValue = true, // ++
            fallback = false
    )) {
        val observer = this.mockObserve()
        assert(value, true, observer, 1)
        this += null // ++
        assert(value, false, observer, 2)
    }

    @Test
    fun `distinct`() = with(SkeletonLiveData<Boolean>(
            initialValue = true, // ++
            distinct = true
    )) {
        val observer = this.mockObserve<Boolean>()
        assert(value, true, observer, 1)
        this += true
        this += true
        assert(value, true, observer, 1)
        this += true
        this += true
        this += false // ++
        this += true // ++
        assert(value, true, observer, 3)
    }

    @Test
    fun `initialValue_fallback_distinct`() = with(SkeletonLiveData<Boolean>(
            initialValue = true, // ++
            fallback = false,
            distinct = true
    )) {
        val observer = this.mockObserve<Boolean>()
        assert(value, true, observer, 1)
        this += null // ++
        assert(value, false, observer, 2)
        this += null
        assert(value, false, observer, 2)
    }

    @Test
    fun `filter_NonNull`() = with(SkeletonLiveData<Boolean>(
            filter = SkeletonLiveData.Filters.NonNull
    )) {
        val observer = this.mockObserve<Boolean>()
        assert(value, null, observer, 0)
        this += null
        assert(value, null, observer, 0)
        this += true // ++
        this += null
        assert(value, true, observer, 1)
    }

    @Test
    fun `filter_true`() = with(SkeletonLiveData<Boolean>(
            filter = { it == true }
    )) {
        val observer = this.mockObserve<Boolean>()
        assert(value, null, observer, 0)
        this += null
        assert(value, null, observer, 0)
        this += false
        assert(value, null, observer, 0)
        this += true // ++
        assert(value, true, observer, 1)
    }

    @Test
    fun `initialValue_filter`() = with(SkeletonLiveData<Boolean>(
            initialValue = true,
            filter = { it != true }
    )) {
        val observer = this.mockObserve<Boolean>()
        assert(value, true, observer, 1)
    }

    private inline fun <reified T : Any> assert(actual: T?, expected: T?, observer: Observer<T?>, count: Int) {
        assert(actual == expected) { "actual='$actual' <> expected='$expected'" }
        verify(exactly = count) { observer.onChanged(any()) }

    }

}