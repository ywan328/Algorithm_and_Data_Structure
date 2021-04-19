package com.day04;

import com.day03.AbstractList;

public class LinkedListDouble<E> extends AbstractList<E> {
    private Node<E> first;
    private Node<E> last;
    //内部类
    private class Node<E>{
        Node<E> pre;//上一个node的地址
        Node<E> next;//下一个node的地址
        E element;//存储的数据

        public Node(Node<E> pre, Node<E> next, E element) {
            this.pre = pre;
            this.next = next;
            this.element = element;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if(pre!=null){
                sb.append(pre.element);
            }else{
                sb.append("null");
            }
            sb.append("<-").append(element).append("->");
            if(next!=null){
                sb.append(next.element);
            }else{
                sb.append("null");
            }
            return sb.toString();
        }
    }

    @Override
    public E get(int index) {
        //下标越界处理
        checkIndex(index);
        return node(index).element;
    }
    //获取节点的方法
    private Node<E> node(int index){
        //下标越界处理
        checkIndex(index);
        if(index<size>>1){
            //拿到first
            Node<E> node=first;
            for (int i = 0; i <index; i++) {
                node=node.next;
            }
            return node;
        }else{//从后往前
            Node<E> node=last;
            for (int i = size-1; i>index ; i--) {
                node=node.pre;
            }
            return node;
        }
    }

    @Override
    public E set(int index, E element) {
        //下标越界处理
        checkIndex(index);
        //通过node(index)获取element
        Node<E> node=node(index);
        E old=node.element;
        node.element=element;
        return old;
    }

    @Override
    public void clear() {
        size=0;
        first=null;
        last=null;
    }
    @Override
    public int indexOf(E element) {
        Node<E> node=first;
        if(element==null){
            for (int i = 0; i <size; i++) {
                if(node.element==null){
                    return i;
                }
                node=node.next;
            }
        }else{
            for (int i = 0; i <size; i++) {
                if(element.equals(node.element)){
                    return i;
                }
                node=node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }
    @Override
    public void add(int index, E element) {
        //下标越界index=size
        checkAddIndex(index);
        if (index==size){
            Node<E> oldlast=last;
            last=new Node<E>(oldlast,null,element);
            if(oldlast==null){
                first=last;
            }else{
                oldlast.next=last;
            }
        }else{
            Node<E> next=node(index);
            Node<E> pre=next.pre;
            Node<E> node=new Node<>(pre,next,element);
            next.pre=node;
            if(pre==null){
                first=node;
            }else{
                pre.next=node;
            }
        }
        size++;
    }

    @Override
    public E remove(int index) {
        //下标越界检测index==size
        checkIndex(index);
        //记录被删除的节点
        Node<E> node=node(index);
        //记录被删除节点的前一个节点
        Node<E> pre=node.pre;
        //记录被删除节点的后一个节点
        Node<E> next=node.next;
        if(pre==null){
           first=next;
        }else{
            //将前一个节点的next指向后一个节点
            pre.next=next;
        }
        if(next==null){//index=size-1  删除最后的元素
            last=pre;
        }else{
            //将后一个节点的pre指向前一个节点
            next.pre=pre;
        }
        size--;
        return node.element;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("size:" + size + "->[");
        Node<E> node=first;
        for (int i = 0; i < size; i++) {
            if (i != 0) {//只要不是第0个元素就拼接一个,和空格
                sb.append(", ");
            }
            sb.append(node.toString());//拼接每一个元素
            node=node.next;
        }
        sb.append("]");//拼接末尾
        return sb.toString();//返回输出结果
    }
}
