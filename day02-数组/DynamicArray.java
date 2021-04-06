package com.day02;

/**
 * 动态可变数组  自动扩容
 */
public class DynamicArray<E> {
    private int size = 0;//保存当前元素个数
    //定义默认初始化容量
    private static final  int DEFAULT_CAPACITY = 10;
    //查找失败返回值
    private static final int ELEMENT_NOT_FOUND = -1;
    //用于保存数组元素
    private E[] elements = (E[]) new Object[DEFAULT_CAPACITY];

    public DynamicArray() {
        this(DEFAULT_CAPACITY);
    }
    /**
     * 带参初始化
     *
     * @param capacity 初始化容量
     */
    public DynamicArray(int capacity) {
        if (capacity < 10) {
            elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            elements = (E[]) new Object[capacity];
        }
    }

    /**
     * 当前数组是否为空
     * 空：true
     * 非空：false
     *
     * @return 返回true | false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 是否包含某个元素
     *
     * @param element
     * @return 返回true | false
     */
    public boolean contains(E element) {
        return indexOf(element)!=ELEMENT_NOT_FOUND;
    }

    /**
     * 查找元素
     *
     * @param element 需要查找的元素
     * @return 返回该元素索引 | -1
     */
    public int indexOf(E element) {
        if(element==null){
            for (int i = 0; i <size; i++) {
                if(elements[i]==null){
                    return i;
                }
            }
        }else{
            for (int i = 0; i <size; i++) {
                if(element.equals(elements[i])){
                    return i;
                }
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    /**
     * 返回对应索引的值 不存在返回-1
     *
     * @param index 元素的索引
     * @return 对应值 | -1
     */
    public E get(int index) {
        checkIndex(index);
        return elements[index];
    }

    /**
     * 设置index位置元素的值
     *
     * @param index   需要设置的位置索引
     * @param element 设置的值
     * @return 返回原先的值
     */
    public E set(int index, E element) {
        checkIndex(index);
        E old=elements[index];
        elements[index]=element;
        return  old;
    }
    /**
     * 清空所有元素
     */
    public void clear() {
        //遍历将每个元素复制为null即可清空
        for (int i = 0; i <size; i++) {
            elements[i]=null;
        }
        size=0;
    }
    /**
     * 返回当前元素的数量
     *
     * @return 当前元素的个数
     */
    public int size() {
        return size;
    }
    /**
     * 添加元素到尾部
     *
     * @param element 待添加的元素
     */
    public void add(E element) {
        if(size>elements.length-1){
            System.out.println("开始扩容");
            ensureCapacity(size+1);
        }
        elements[size]=element;
        size++;
    }

    /**
     * 向index位置添加元素
     *
     * @param index   插入位置的索引
     * @param element 插入的元素
     */
    public void add(int index, E element) {
        if(index<0||index>size){
            throw new IndexOutOfBoundsException("索引越界,允许范围 size：0 => " + (size) + " 当前索引：" + index);
        }
        for (int i =size; i>index; i--) {
            elements[i]=elements[i-1];//元素右移
        }
        elements[index]=element;
        size++;
    }

    /**
     * 移除index位置元素
     *
     * @param index 被移除元素的索引
     * @return 返回原先值
     */
    public E remove(int index) {
        checkIndex(index);
        E old=elements[index];
        for (int i = index; i <size; i++) {
            elements[i]=elements[i+1];
        }
        size--;
        elements[size]=null;//清空最后一个元素
        //判断是否缩容
        if(size==elements.length>>1){
            System.out.println("开始缩容");
            ensureCapacity(elements.length>>1);
        }
        return old;
    }




    /**
     * 返回元素集合size:5, [1, 3, 4 ,5 ,7 ]
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("size:" + size + " => [");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(" ,");
            }
            sb.append(elements[i]);
        }
        sb.append("]");
        return sb.toString();
    }
    //判断是否越界
    public void checkIndex(int index){
        if(index<0||index>=size){
            throw new IndexOutOfBoundsException("索引越界,允许范围 size：0 => " + (size - 1) + " 当前索引：" + index);
        }
    }
    //确保数组容量
    public void ensureCapacity(int capacity){
        if(elements.length>=capacity){
            return;
        }
        //扩容1.5倍
        E[] newElments=(E[])new Object[elements.length+(elements.length>>1)];
        //将原有数组元素复制到新数组中
        for (int i = 0; i <size; i++) {
            newElments[i]=elements[i];
        }
        elements=newElments;
    }
}