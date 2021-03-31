# 算法的评估

算法（Algorithm）是指用来操作数据、解决程序问题的一系列方法。

对于同一个问题，使用不同的算法，也许最终得到的结果是一样的，但在过程中消耗的资源和时间却会有很大的区别。就比如拧一个螺母，扳手和钳子都可以胜任，但使用钳子拧螺母肯定没有扳手的效率高。

![图片](https://uploader.shimo.im/f/ERtWvOt33wVxCgxf.png!thumbnail?fileGuid=rc7Bxqex4jw9yWp0)

# 斐波那契数引入复杂度分析

```java
package com;
public class Test {
    /**
     * 求第n个斐波那契数
     * 斐波那契数列：这个数列从第3项开始，每一项都等于前两项之和。
     * 下标 0 1 2 3 4 5 6 7
     * 数列 0 1 1 2 3 5 8 13
     */
    //递归的方式：
    public static long fun1(long n){
        if(n<=1) return n;
        return fun1(n-1)+fun1(n-2);
    }
    public static long fun2(int n){
        if(n<=1) return n;
        long first=0;
        long second=1;
        for (long i = 0; i <n-1 ; i++) {
            long sum=first+second;
            first=second;
            second=sum;
        }
        return second;
    }
    public static void main(String[] args) {
       System.out.println(fun1(70));
//        int n=46;
//        TimeTool.check("fun1",new TimeTool.Task(){
//            public void execute(){
//                System.out.println(fun1(n));
//            }
//        });
//
//        TimeTool.check("fun1",new TimeTool.Task(){
//            public void execute(){
//                System.out.println(fun2(n));
//            }
//        });
    }
}
```
**计时工具（不用敲）**
```java
package com;
import java.text.SimpleDateFormat;
import java.util.Date;
public class TimeTool {
    private static final SimpleDateFormat fmt=new SimpleDateFormat("HH:mm:ss.SSS");
    public interface Task{
        void execute();
    }
    public static void check(String title,Task task){
        if(task==null){
            return;
        }
        title=(title==null)?"":("["+title+"]");
        System.out.println(title);
        System.out.println("开始："+fmt.format(new Date()));
        long begin=System.currentTimeMillis();
        task.execute();
        long end=System.currentTimeMillis();
        System.out.println("结束："+fmt.format(new Date()));
        double mill=(end-begin)/1000.0;
        System.out.println("耗时："+mill+"秒");
        System.out.println("------------");
    }
}
```
**那么我们应该如何去衡量不同算法之间的优劣呢？**
## **1.事后统计法**

通过统计、监控，利用计算机计时器对不同算法的运行时间进行比较，从而确定算法效率的高低，但有非常大的局限性：

* 测试结果非常依赖测试环境
* 测试结果受数据规模的影响很大
## **2.事前分析估算**

在计算机程序编制前，依据统计方法对算法进行估算。

>大家想一下，当我们要实现一个功能的时候，更多的希望快速知道几种解法中的最优解然后去实现，而不是花大力气去把每种解法都做出来再测试得到结果，因为太低效。
>所以我们需要在代码执行前对影响代码效率的因素（如时间、空间复杂度等）做一个评估。因此我们需要通过复杂度分析来决策，下面我们主要讲解面试中最高频的时间复杂度。

**时间维度**：是指执行当前算法所消耗的时间，我们通常用「时间复杂度」来描述。

**空间维度：**是指执行当前算法需要占用多少内存空间，我们通常用「空间复杂度」来描述。

# 推导大O阶方法

>算法的执行效率，粗略地讲，就是算法代码执行的时间。但是，如何在不运行代码的情况下，用“肉眼”得到一段代码的执行时间呢？这里有段非常简单的代码，现在，我就带你一块来估算一下这段代码的执行时间。
```java
package com;
public class Test02 {
    public void cal01(int age) {
        //1 时间复杂度：O(1)
        if(age>58){
            System.out.println("阿姨");
        }else if (age>28){
            System.out.println("小姐姐");
        }else{
            System.out.println("美少女");
        }
    }
    int cal02(int n) {
        //时间复杂度：O(n)
        int sum = 0;//执行1次
        int i = 1;//执行1次
        //    执行n次 执行n次
        for (; i <= n; ++i) {
            sum = sum + i;//执行n次
        }
        return sum;//执行1次
    }
    public void cal03(int n) {
        //1+3n 时间复杂度：O(n)
        for (int i = 0; i < n; i++) {
            System.out.println("关阿姨");
        }
    }
    public void cal04(int n) {
        //1+2n+n*(1+3n)=3n^2+3n+1
        //时间复杂度：O(n^2)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println("guan");
            }
        }
    }
    public void cal05(int n) {
        //1+2n+n*(1+20+20+20)=1+2n+61n=1+63n
        //时间复杂度：O(n)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.println("guan");
            }
        }
    }
    public void cal06(int n) {
        //log2(n)  时间复杂度：O(logn)
        int i = 1;
        while(i<n){
            i = i * 2;
        }
    }
    public void cal07(int n) {
        //共执行1+2*log2(n)+log2(n)*(1+3n)=1+3*log2(n)+2*nlog2(n)
        //时间复杂度： 忽略常数、系数最终为O(logn+logn)也就是O(nlogn)
        for (int i = 1; i <n ; i+=i) {
            //1+3n
            for (int j = 0; j < n; j++) {
                System.out.println("ayi");
            }
        }
    }
}
```
尽管我们不知道unit_time的具体值，但是通过这几段代码执行时间的推导过程，我们可以得到一个非常重要的规律，那就是，**所有代码的执行时间T(n)与每行代码的执行次数n成正比。**
我们可以把这个规律总结成一个公式。注意，大O就要登场了！

![图片](https://uploader.shimo.im/f/bRzsOS8V8ABEsQXA.png!thumbnail?fileGuid=rc7Bxqex4jw9yWp0)

* T(n)表示代码执行的时间；
* n表示数据规模的大小；
* f(n)表示每行代码执行的次数总和。
* 因为这是一个公式，所以用f(n)来表示。公式中的O，表示代码的执行时间T(n)与f(n)表达式成正比。

---


1. **公式中的低阶、常量、系数三部分并不左右增长趋势，所以都可以忽略**。用大O表示法表示刚讲的那两段代码的时间复杂度，就可以记为：T(n) = O(n)； T(n) = O(n^2)。
2. **大O时间复杂度实际上并不具体表示代码真正的执行时间，而是表示代码执行时间随数据规模增长的变化趋势**，所以，也叫作渐进时间复杂度（asymptotic time complexity），简称时间复杂度。一般随着n的增大，T(n)增长最慢的算法为最优算法。

**总结**

* 用常数1取代运行时间中的所有加法常数
* 在修改后的运行次数函数中，只保留最高阶项
* 如果最高阶项存在且系数不是1，则去除去除与这个项相乘的系数
# 常见的时间复杂度量级

### 常数阶O(1)

首先介绍顺序结构的时间复杂度

无论代码执行了多少行，只要是没有循环等复杂结构，那这个代码的时间复杂度就都是O(1)，如：

```java
int i = 1;
int j = 2;
++i;
j++;
int m = i + j;
```
上述代码在执行的时候，它消耗的时候并不随着某个变量的增长而增长，那么无论这类代码有多长，即使有几万几十万行，都可以用O(1)来表示它的时间复杂度。
注意：不管这个常数是多少，都记作O(1),而不能记作O(3)，O(12)等其他任何数字。

对于分支结构无论判断条件是真还是假，执行的次数都是恒定的，不会随着n的变大而发生变化，所以单纯的分支结构（不包含在循环结构中），其时间复杂度都是O(1)。

### 线性阶O(n)

线性阶的循环结构会复杂很多，要确定某个算法的阶次，需要确定某个特定语句运行的次数，因此分析算法的复杂度，关键就是要分析循环结构的运行情况。

「一个循环」，算法需要执行的运算次数用输入大小n的函数表示，即 T(n) 。

```java
for(int i=1;i<=n;i++){
  System.out.println(i);
}
```
「一个循环」，算法需要执行的运算次数用输入大小n的函数表示，即 T(n) 。
```java
for(int i=1;i<=n;i++){
  System.out.println(i);
}
for(int i=1;i<=n;i++){
  System.out.println(i);
}
```
如果是for循环并列关系那么n会执行2n次，忽略常数也是O(n)
### 对数阶O(logn)

一般省略底数，![图片](https://uploader.shimo.im/f/FXuuq0A3a4YhJaOF.png!thumbnail?fileGuid=rc7Bxqex4jw9yWp0)

![图片](https://uploader.shimo.im/f/23QfCCrCwbvsEOKL.png!thumbnail?fileGuid=rc7Bxqex4jw9yWp0)

```java
int i = 1;
while(i<n){
    i = i * 2;
}
```
从上面代码可以看到，在while循环里面，每次都将 i 乘以 2，乘完之后，i 距离 n 就越来越近了。我们试着求解一下，假设循环x次之后，i 就大于 n 了，此时这个循环就退出了，也就是说 2 的 x 次方等于 n，那么 x =$$log_{2}n$$

也就是说当循环$$log_{2}n$$以后，这个代码就结束了。一般忽略省略底数，因此这个代码的时间复杂度为：O(logn).

### 平方阶O(n^2)

举例：

```java
for(i=1; i<=n; i++){
   for(j=1; j<=n; j++){
       j = i;
       j++;
    }
}
```
这段代码其实就是嵌套了2层n循环，它的时间复杂度就是 O(n*n)，即 O(n²)
如果将其中一层循环的n改成m，即：

```java
for(i=1; i<=m; i++){
   for(j=1; j<=n; j++){
       j = i;
       j++;
    }
}
```
那它的时间复杂度就变成了 O(m*n)，所以总结循环的时间复杂度等于循环体的复杂度乘以该循环运行的次数。下面这个循环嵌套它的时间复杂度又是多少呢？
```java
for(i=0; i<n; i++){
   for(j=i; j<n; i++){
      Sytem.out.println("111");
    }
}
```
由于i=0时，内循环执行了n次，当i=1时，执行了n-1次......当i=n-1时，执行了1次，所以总共执行了：n+(n-1)+(n-2)+.....+1=n(n+1)/2=n^2/2+n/2
使用推导大O阶的方法：最终为n^2

### 线性对数阶O(nlogn)

线性对数阶O(nlogN) 其实非常容易理解，将时间复杂度为O(logn)的代码循环N遍的话，那么它的时间复杂度就是 n * O(logN)，也就是了O(nlogN)。

就拿上面的代码加一点修改来举例：

```java
for(m=1; m<n; m++){
    i = 1;
    while(i<n){
        i = i * 2;
    }
}
```
立方阶O(n³)、K次方阶O(n^k)
参考上面的O(n²) 去理解就好了，O(n³)相当于三层n循环，其它的类似。

但是O(n³)过大的n都会使得结果变得不现实，同样O(n²) 和O(n!)等除非是很小的n值，否则哪怕n只是100都是噩梦般的运行时间，所以这种不切实际的算法时间复杂度一般我们不讨论。

![图片](https://uploader.shimo.im/f/NnkPrvxPkF0NL6RT.png!thumbnail?fileGuid=rc7Bxqex4jw9yWp0)

函数生成工具：https://zh.numberempire.com/

当数据规模较小的时候，上面从下至上依次的时间复杂度越来越大，执行的效率越来越低。

![图片](https://uploader.shimo.im/f/kbmbdorPuEcVjuFl.png!thumbnail?fileGuid=rc7Bxqex4jw9yWp0)

当数据规模较大的时候效果愈加明显

![图片](https://uploader.shimo.im/f/PeginIQdsEmRIKo2.png!thumbnail?fileGuid=rc7Bxqex4jw9yWp0)

## 分析斐波那契数的时间复杂度

```java
//时间复杂度：其实就是看fun1方法被调用了多少次，调用了多少次就是执行了多少次，如果传入的是5调用fun1（4）和fun1（3）依次推导共调用O(2^n)
public static long fun1(long n){
    if(n<=1) return n;
    return fun1(n-1)+fun1(n-2);
}
//时间复杂度：O(n)
public static long fun2(int n){
    if(n<=1) return n;
    long first=0;
    long second=1;
    for (long i = 0; i <n-1 ; i++) {
        //每次加都是前两个
        long sum=first+second;
        first=second;      
        second=sum;
        
    }
    return second;
}
```
# Leetcode（斐波那契数）

[题目链接：https://leetcode-cn.com/problems/fibonacci-number/submissions/](https://leetcode-cn.com/problems/fibonacci-number/submissions/)

斐波那契数，通常用 F(n) 表示，形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：

F(0) = 0，F(1) = 1

F(n) = F(n - 1) + F(n - 2)，其中 n > 1

给你 n ，请计算 F(n) 。

示例 1：

输入：2

输出：1

解释：F(2) = F(1) + F(0) = 1 + 0 = 1

示例 2：

输入：3

输出：2

解释：F(3) = F(2) + F(1) = 1 + 1 = 2

示例 3：

输入：4

输出：3

解释：F(4) = F(3) + F(2) = 2 + 1 = 3

# 数据结构介绍

## 什么是数据结构？

数据结构是计算机存储、组织数据的方式。

>每个程序或软件的骨干都是两个实体：**数据、算法**。
>算法将数据转换为程序可以有效使用的内容。因此，重要的是要了解如何构造数据，以便算法可以快速维护，利用和迭代数据。

编程语言中存在的数据结构与现实世界系统非常相似。

>想象一下您去超市购物。在琳琅满目的店中如果商品没有位于何处的标志，您将很难找到想要的东西！
>而如果对商品进行了归类并增加了商品指示牌，找寻的效率就会提升很多倍

<img src="https://uploader.shimo.im/f/7HkvsV3K5MgAUs8K.png!thumbnail?fileGuid=rc7Bxqex4jw9yWp0" alt="图片" style="zoom: 50%;" />

同样，**数据**就相当于我们的**商品**，而**数据结构**为我们提供了一种在数字空间中组织信息的方法，类比于**增加商品指示牌**等方法，让我们进行数据操作更加快速便捷。

**常见的数据结构**

* 线性结构：数组，链表、栈、队列、哈希表
* 树形结构：二叉树、AVL树、红黑树、B树、堆、Trie、哈夫曼树、并查集
* 图形结构：领接矩阵、邻接表
## 数据结构如何使用？

数据结构为我们处理四个主要功能：

1. **输入信息（**如何接收数据**）**
2. **处理信息（**在数据结构中处理数据的方式**）**
3. **维护资料（**如何在结构内组织数据**）**
4. **检索信息（**检索查找并返回存储在结构中的数据**）**

不同类型的数据在进行输入，处理，存储和检索时操作方式不同。这就是为什么我们有几种数据结构可供选择的原因！

## 选择最佳的数据结构

不同的数据结构更适合于不同的任务。选择错误的数据结构可能会导致代码变慢或无法响应（并弄乱程序！），因此在做出决定时，请考虑以下几个因素：

>1、是否有任何数据结构具有最适合此目的的内置功能？
>2、是否选择了最优的数据结构来进行搜索、排序、迭代？
>3、是否需要控制留出内存来存储数据？
>4、不同的数据结构需要多长时间才能完成各种任务，运行时都做了什么？
# 线性表-数组

线性表是最基本、最简单、也是最常用的一种数据结构。线性表是数据结构的一种，一个线性表是n个具有相同特性的数据元素的有序数列。

![图片](https://uploader.shimo.im/f/O1ytIRX0sktQtI84.png!thumbnail?fileGuid=rc7Bxqex4jw9yWp0)

顺序表存储是将数据元素放到一块连续的内存存储空间，相邻数据元素的存放地址也相邻

## 生活中的线性表

<img src="https://uploader.shimo.im/f/7eiXUIDO3Cw7LQ48.png!thumbnail?fileGuid=rc7Bxqex4jw9yWp0" alt="图片" style="zoom:33%;" /><img src="https://uploader.shimo.im/f/MHUVXFBrEvZBFTv8.png!thumbnail?fileGuid=rc7Bxqex4jw9yWp0" alt="图片" style="zoom:33%;" />

## 数组（Array）

**数组是一种顺序存储的线性表。所有元素的内存地址是连续的。**

```java
//java中数组的声明
int[] array=new int[]{11,66,88};
```
<img src="https://uploader.shimo.im/f/VWrPa0Rj6zFLDS1P.png!thumbnail?fileGuid=rc7Bxqex4jw9yWp0" alt="图片" style="zoom:33%;" />

**优点**：

* 空间利用率高。
* 存取速度高效，通过下标来直接存取。

**缺点**：

* 插入和删除比较慢，比如：插入或者删除一个元素时，整个表需要遍历移动元素来重新排一次顺序。
* 不可以增长长度，有空间限制,当需要存取的元素个数可能多于顺序表的元素个数时,会出现"溢出"问题.当元素个数远少于预先分配的空间时,空间浪费巨大。
### ArrayList自实现（动态数组）

**需求**：底层采用数组 实现动态扩容数组（ArrayList）

**目的：**

1. 体会线性结构
2. 练习常用算法
3. 强化数组
4. 练习泛型

**面对的问题：**

* 数组一旦创建，容量不可变
* 扩容的条件

**动态数组接口设计**

◼intsize();*// 元素的数量*

◼booleanisEmpty();*// 是否为空*

◼booleancontains(E element);*// 是否包含某个元素*

◼voidadd(E element);*// 添加元素到最后面*

◼ Eget(intindex);*// 返回index位置对应的元素*

◼ Eset(intindex,E element);*// 设置index位置的元素*

◼voidadd(intindex,E element);*// 往index位置添加元素*

◼ Eremove(intindex);*// 删除index位置对应的元素*

◼intindexOf(E element);*// 查看元素的位置*

◼voidclear();*// 清除所有元素*

**具体实现：**

先准备所有空的方法

```java
package com;
/**
 * 动态可变数组  自动扩容
 */
public class DynamicArray<E> {
    private int size = 0;//保存当前元素长度
    //定义默认初始化容量
    private final int DEFAULT_CAPACITY = 10;
    //查找失败返回值
    private final int ELEMENT_NOt_FOUND = -1;
    //用于保存数组元素
    private E[] elements = (E[]) new Object[DEFAULT_CAPACITY];
    /**
     * 检查索引越界
     *
     * @param index 当前访问索引
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("索引越界" + "允许范围 size：0 => " + (size - 1) + " 当前索引：" + index);
        }
    }
    /**
     * 检查添加索引越界
     *
     * @param index 添加位置的索引
     */
    private void checkAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("索引越界" + "允许范围 size：0 => " + (size) + " 当前索引：" + index);
        }
    }
    /**
     * 确保数组容量够用
     */
    private void ensureCapacity() {
        //扩容1.5倍
        E[] newElements = (E[]) new Object[elements.length + (elements.length >> 1)];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;//引用
    }
    //无参构造方法
    public DynamicArray() {
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
     * 返回当前元素的数量
     *
     * @return 当前元素的个数
     */
    public int size() {
        return size;
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
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) return true;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) return true;
            }
        }
        return false;
    }
    /**
     * 添加元素到尾部
     *
     * @param element 待添加的元素
     */
    public void add(E element) {
        if (size > elements.length - 1) {
            ensureCapacity();
        }
        elements[size++] = element;
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
        checkIndex(index);//检查索引越界
        E old = elements[index];
        //
        elements[index] = element;
        return old;
    }

    /**
     * 向index位置添加元素
     *
     * @param index   插入位置的索引
     * @param element 插入的元素
     */
    public void add(int index, E element) {
        checkAddIndex(index);//检查索引越界
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];//把元素右移
        }
        elements[index] = element;
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
        E old = elements[index];
        for (int i = index; i < size; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null;//清空最后一个元素
        return old;
    }
    /**
     * 查找元素
     *
     * @param element 需要查找的元素(注意)可能为null
     * @return 返回该元素索引 | -1
     */
    public int indexOf(E element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) {
                    return i;
                }
            }
        }
        return ELEMENT_NOt_FOUND;
    }
    /**
     * 清空所有元素
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
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
}
```
**测试**
```java
package com;
public class TestDy {
    public static void main(String[] args) {
        DynamicArray<String> list = new DynamicArray(1);
        list.add("sdas");
        list.add("32143");
        DynamicArray<Student> stuList = new DynamicArray<>();
        Student st1 = new Student(1, "张三", 4);
        Student st2 = new Student(2, "李四", 23);
        Student st3 = new Student(3, "赵六", 17);
        stuList.add(st1);
        stuList.add(null);
        System.out.println(stuList.contains(null));
        System.out.println(stuList.indexOf(null));
        stuList.add(st2);
        stuList.add(st3);
        System.out.println(stuList.toString());
    }
}
```




