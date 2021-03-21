package com.example.akiraito.codechallenge.extention

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 通知が一回限りのEventに使用する（例：画面遷移、クリックイベント）
 */
class SingleLiveData<T> : MutableLiveData<T>() {
    private val isPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer<T> { value ->
            // 複数Observeするケースは想定外のため、２個目以降のObserveは呼ばれません。
            if (isPending.compareAndSet(true, false)) {
                observer.onChanged(value)
            }
        })
    }

    override fun setValue(value: T) {
        isPending.set(true)
        super.setValue(value)
    }

    override fun postValue(value: T) {
        isPending.set(true)
        super.postValue(value)
    }
}
