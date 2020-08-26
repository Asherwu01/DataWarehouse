package com.asher.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

public class TestFor {

    /**
     *  增强for修改list，一边遍历一边修改
     */
    /*@Test
    public void testForEach(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(12);
        list.add(11);
        list.add(10);

        for (Integer integer : list) {
            if (integer == 10){
                list.remove(10);
            }
        }
        System.out.printf(list.toString());
    }*/

    /*@Test
    public void testIterator(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(12);
        list.add(11);
        list.add(10);

        Iterator<Integer> iterator = list.iterator();

        while (iterator.hasNext()){
            if (iterator.next()==10){
                list.remove(10);
            }
        }
        System.out.printf(list.toString());
    }*/

    /*@Test
    public void testGeneralFor(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(12);
        list.add(11);
        list.add(10);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i) == 11){
                list.remove(i);// 下标越界
            }
        }

        System.out.printf(list.toString());
    }*/

    /*
        问题：边遍历边修改集合，会报错，并发修改异常，
        原因：迭代器会同步原来的list，当list发生修改，迭代器同步就抛出异常，迭代器是原来list的副本，
             如果迭代器自身改变，原list不变，怎不会报错，并且迭代器反向同步list，list也会改变
        办法：迭代器遍历，且只修改迭代器
     */
    @Test
    public void testIterator2(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(12);
        list.add(11);
        list.add(10);

        Iterator<Integer> iterator = list.iterator();

        while (iterator.hasNext()){
            if (iterator.next()==10){
                iterator.remove();//注意，迭代器运行在新开的线程，并且是原list的副本，会定期同步list，如果list修改，抛异常，如果自身修改，反向促使list修改
            }
        }
        System.out.printf(list.toString());
    }
}
