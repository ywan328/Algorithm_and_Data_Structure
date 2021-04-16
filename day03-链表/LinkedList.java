package com.day03;

public class LinkedList<E> extends AbstractList<E>{
    private Node<E> first;
    //内部类
    private class Node<E>{
        Node<E> next;//下一个node的地址
        E element;//存储的数据

        public Node(Node<E> next, E element) {
            this.next = next;
            this.element = element;
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
        //拿到first
        Node<E> node=first;
        for (int i = 0; i <index; i++) {
            node=node.next;
        }
        return node;
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
        //inde=0;
        if(index==0){
            first=new Node<>(first,element);
        }else{
            //获取前一个节点
            Node<E> pre=node(index-1);
            Node<E> next=pre.next;//0x888
            //创建新的接节点
            pre.next=new Node(next,element);
        }
        size++;
    }

    @Override
    public E remove(int index) {
        //下标越界检测index==size
        checkIndex(index);
        Node<E> oldNode=first;
        if(index==0){
            first=first.next;//0x888
        }else{
            //获取前一个节点
            Node<E> pre=node(index-1);
            oldNode=pre.next;//0x444
            pre.next=pre.next.next;//0x888
        }
        size--;
        return oldNode.element;
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
            sb.append(node.element);//拼接每一个元素
            node=node.next;
        }
        sb.append("]");//拼接末尾
        return sb.toString();//返回输出结果
    }
}
