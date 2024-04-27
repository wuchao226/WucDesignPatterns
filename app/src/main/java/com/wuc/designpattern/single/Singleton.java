package com.wuc.designpattern.single;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: wuc
 * @date: 2024/4/23
 * @desc: 单例模式 各种写法 <a href="https://www.jianshu.com/p/8549cde34cd5">...</a>
 */
public class Singleton {
    private Singleton() {
    }

    /**
     * 方式一、饿汉模式，通过 Java 的 ClassLoader 机制保证了单例对象唯一。
     * Kotlin实现：object Singleton{}
     * mInstanceByHungry什么时候被初始化？
     * Singleton 类被加载的时候就会被初始化
     */
    private static Singleton mInstanceByHungry = new Singleton();

    public static Singleton getInstanceByHungry() {
        return mInstanceByHungry;
    }

    /**
     * 方式二、懒汉模式，懒汉模式是声明一个静态对象，并且在用户第一次调用 getInstance() 时进行初始化，而饿汉模式是在声明静态对象时就已经初始化
     * 问题：即使 instance 已经被初始化（第一次调用时就会被初始化 instance），每次调用 getInstance() 方法都会进行同步，这样会消耗不必要的资源，这也是懒汉单例模式存在的最大问题。
     * 懒汉模式的优点是单例只有在使用时才会被实例化，在一定程度上节约了资源；缺点是第一次加载时需要及时进行实例化，反应稍慢，最大的问题是每次调用 getInstance() 都进行同步，造成不必要的开销。
     */
    private static Singleton mInstanceLazy = null;

    public static Singleton getInstanceLazy() {
        if (mInstanceLazy == null) {
            mInstanceLazy = new Singleton();
        }
        return mInstanceLazy;
    }

    /**
     * 方式三、双重检查锁机制,避免并发时创建了多个实例, 该方式不能完全避免并发带来的破坏.
     * kotlin实现:
     * class Singleton private constructor() {
     * companion object {
     * val instance: Singleton by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { Singleton() }
     * }
     * }
     */
    // 第一层锁：保证变量可见性
    private volatile static Singleton mInstanceDoubleCheck = null;

    public static Singleton getInstanceDoubleCheck() {
        // 第一次判空：无需每次都加锁，提高性能
        if (mInstanceDoubleCheck == null) {
            // 第二层锁：保证线程同步
            synchronized (Singleton.class) {
                // 第二次判空:避免多线程同时执行getInstance()产生多个single对象
                if (mInstanceDoubleCheck == null) {
                    mInstanceDoubleCheck = new Singleton();
                }
            }
        }
        return mInstanceDoubleCheck;
    }

    /**
     * 方式四、静态内部类,在第一次加载SingletonHolder时初始化一次INSTANCE对象, 保证唯一性, 也延迟了单例的实例化
     * 第一次加载 Singleton 类时，并不会初始化 INSTANCE ，只有在第一次调用 Singleton 的 getInstance 方法时才会导致 INSTANCE 被初始化。
     * 因此第一次调用 getInstance 方法会导致虚拟机加载 Holder 类，这种方式不仅能够确保线程安全，也能够保证单例对象的唯一性，同时也延迟了单例实例化。
     * kotlin实现:
     * class Singleton private constructor() {
     * companion object {
     * val instance = Holder.holder
     * }
     * <p>
     * private object Holder {
     * val holder = Singleton()
     * }
     * }
     */
    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstanceStaticInnerClass() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 方式五、枚举单例
     * 枚举在 Java 中与普通的类一样，不仅能够有字段，还能够有自己的方法。默认枚举实例的创建是线程安全的，并且在任何情况下它都是一个单例。
     */
    public enum SingletonEnum {
        INSTANCE;

        public void doSomething() {
            System.out.println("do sth.");
        }
    }

    public static SingletonEnum getInstanceEnum() {
        return SingletonEnum.INSTANCE;
    }

    /**
     * 方式六、注册到容器, 根据key获取对象.一般都会有多种相同属性类型的对象会注册到一个map中
     * 适用场景：一个系列的单例
     * 在程序的初始，将多种单例类型注入到一个统一的管理类中，在使用时根据 key 获取对象对应类型的对象。这种方式使得我们可以管理多种类型的单例，
     * 并且在使用时通过统一的接口进行获取操作，降低了用户的使用成本，也对用户隐藏了具体实现，降低了耦合度。
     */
    private static Map<String, Object> objMap = new HashMap<String, Object>();

    /**
     * 注册对象到map中
     *
     * @param key
     * @param instance
     */
    public static void registerService(String key, Object instance) {
        if (!objMap.containsKey(key)) {
            objMap.put(key, instance);
        }
    }

    /**
     * 根据key获取对象
     *
     * @param key
     * @return
     */
    public static Object getService(String key) {
        return objMap.get(key);
    }
}
