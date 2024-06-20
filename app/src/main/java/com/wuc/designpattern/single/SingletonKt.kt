package com.wuc.designpattern.single

/**
 * @author: wuc
 * @date: 2024/6/20
 * @desc:
 */
class SingletonKt {
    companion object {
        @Volatile
        private var instance: SingletonKt? = null
        fun getInstance(): SingletonKt {
            return instance ?: synchronized(this) {
                instance ?: SingletonKt().also {
                    instance = it
                }
            }
        }
    }
}