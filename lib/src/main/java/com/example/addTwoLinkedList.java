package com.example;

/**
 * Created by Larry-sea on 2016/10/23.
 * <p>
 * 将两个list相加并输出
 */

public class addTwoLinkedList {

    static ListNode listNodeA;
    static ListNode listNodeB;

    static public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


    static public void inputAndInit() {
        listNodeA = new ListNode(1);
        listNodeA.next = new ListNode(2);
        listNodeA.next.next = new ListNode(5);

        listNodeB = new ListNode(3);
        listNodeB.next = new ListNode(7);
        listNodeB.next.next = new ListNode(5);


    }


    static public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode listNodeResult = new ListNode(l1.val + l2.val);
        int lastAddResult = l1.val + l2.val;
        ListNode tempListNode = null;
        while (l1.next != null && l2.next != null) {

            if (lastAddResult >= 10) {
                listNodeResult.next = tempListNode = new ListNode(l1.next.val + l2.next.val + 1);
                lastAddResult = l1.next.val + l2.next.val + 1;
            } else {
                listNodeResult.next = new ListNode(l1.next.val + l2.next.val);
                lastAddResult = l1.next.val + l2.next.val;
            }
            l1.next = l1.next.next;
            l2.next = l2.next.next;
            listNodeResult.next = listNodeResult.next.next;
        }
        return listNodeResult;
    }


    static public void showList(ListNode listNode) {
        System.out.println(listNode.val);
        while (listNode.next != null) {
            System.out.println(listNode.next.val);
            listNode.next = listNode.next.next;
        }
    }

    public static void main(String args[]) {
        //   inputAndInit();
//        showList(addTwoNumbers(listNodeA, listNodeB));
        int array[] = {1, 2, 3, 4, 5};
        showList(initNode(array));

    }


    static public ListNode initNode(int[] array) {
        ListNode listNodeResult = new ListNode(array[0]);
        ListNode pointNode =new ListNode(array[0]);
        for (int position = 1; position < array.length; position=position++) {
            pointNode.next = new ListNode(array[position]);
            listNodeResult.next = pointNode.next;
            pointNode.next=pointNode.next.next;
        }
        return listNodeResult;
    }

}
