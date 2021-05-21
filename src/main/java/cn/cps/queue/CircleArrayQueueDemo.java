package cn.cps.queue;

import java.util.Scanner;

/**
 * @Author: Cai Peishen
 * @Date: 2021/5/21 9:47
 * @Description:
 */
public class CircleArrayQueueDemo {

    public static void main(String[] args) {

        /**
         * front = 0; rear = 0
         * 添加：rear会往后走
         * 获取：front会往后走
         * 整体为圆形，maxSize=4，实际有效数据为3个
         * 满 = (rear + 1) % maxSize = front
         * 空 = front == rear
         *
         * 添加：
         * 添加第一个前判断是否满：(0+1)%4 = 1 != 0，没满，rear可以往后移，变成了(0+1)%4 = 1
         * 添加第二个前判断是否满：(1+1)%4 = 2 != 0，没满，rear可以往后移，变成了(1+1)%4 = 2
         * 添加第三个前判断是否满：(2+1)%4 = 3 != 0，没满，rear可以往后移，变成了(2+1)%4 = 3
         * 添加第四个前判断是否满：(3+1)%4 = 0 == 0，已满，rear不能往后移，至此front=0,rear=3
         *
         * 获取：
         * 获取第一个数据前判断是否为空：0 != 3，存在数据，front可以往后移，变成了(0+1)%4 = 1
         * 获取第二个数据前判断是否为空：1 != 3，存在数据，front可以往后移，变成了(1+1)%4 = 2
         * 获取第三个数据前判断是否为空：2 != 3，存在数据，front可以往后移，变成了(2+1)%4 = 3
         * 获取第四个数据前判断是否为空：3 == 3，没数据了，front不能往后移，至此front=3,rear=3
         *
         * 添加：目前front=3,rear=3
         * 添加第一个前判断是否满：(3+1)%4 = 0 != 3，没满，rear可以往后移，变成了(3+1)%4 = 0 != 3  添加前【null null null null 预留】 添加后 【数据 null null null 预留】，这里的null并不是真正的null，是相对于队列的null
         * 添加第二个前判断是否满：(0+1)%4 = 1 != 3，没满，rear可以往后移，变成了(0+1)%4 = 1 != 3
         * 添加第三个前判断是否满：(1+1)%4 = 2 != 3，没满，rear可以往后移，变成了(1+1)%4 = 2 != 3
         * 添加第二个前判断是否满：(2+1)%4 = 3 == 3，已满，rear不能往后移，至此front=3,rear=3
         */


        //测试一把
        System.out.println("测试数组模拟环形队列的案例~~~");

        // 创建一个环形队列
        CircleArray queue = new CircleArray(4); //说明设置4, 其队列的有效数据最大是3
        char key = ' '; // 接收用户输入
        Scanner scanner = new Scanner(System.in);//
        boolean loop = true;
        // 输出一个菜单
        while (loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            key = scanner.next().charAt(0);// 接收一个字符
            switch (key) {
                case 's':
                    queue.showQueue();
                    break;
                case 'a':
                    System.out.println("输出一个数");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;
                case 'g': // 取出数据
                    try {
                        int res = queue.getQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h': // 查看队列头的数据
                    try {
                        int res = queue.headQueue();
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e': // 退出
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出~~");
    }

}

class CircleArray {
    private int maxSize; // 表示数组的最大容量
    //front 变量的含义做一个调整： front 就指向队列的第一个元素, 也就是说 arr[front] 就是队列的第一个元素
    //front 的初始值 = 0
    private int front;
    //rear 变量的含义做一个调整：rear 指向队列的最后一个元素的后一个位置. 因为希望空出一个空间做为约定.
    //rear 的初始值 = 0
    private int rear; // 队列尾
    private int[] arr; // 该数据用于存放数据, 模拟队列

    public CircleArray(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new int[maxSize];
    }

    // 判断队列是否满
    public boolean isFull() {
        return (rear  + 1) % maxSize == front;
    }

    // 判断队列是否为空
    public boolean isEmpty() {
        return rear == front;
    }

    // 添加数据到队列
    public void addQueue(int n) {
        // 判断队列是否满
        if (isFull()) {
            System.out.println("队列满，不能加入数据~");
            return;
        }
        //直接将数据加入
        arr[rear] = n;
        //将 rear 后移, 这里必须考虑取模
        rear = (rear + 1) % maxSize;
    }

    // 获取队列的数据, 出队列
    public int getQueue() {
        // 判断队列是否空
        if (isEmpty()) {
            // 通过抛出异常
            throw new RuntimeException("队列空，不能取数据");
        }
        // 这里需要分析出 front是指向队列的第一个元素
        // 1. 先把 front 对应的值保留到一个临时变量
        // 2. 将 front 后移, 考虑取模
        // 3. 将临时保存的变量返回
        int value = arr[front];
        front = (front + 1) % maxSize;
        return value;

    }

    // 显示队列的所有数据
    public void showQueue() {
        // 遍历
        if (isEmpty()) {
            System.out.println("队列空的，没有数据~~");
            return;
        }
        // 思路：从front开始遍历，遍历多少个元素
        // 动脑筋
        for (int i = front; i < front + size() ; i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    // 求出当前队列有效数据的个数
    public int size() {
        // rear = 2
        // front = 1
        // maxSize = 3
        return (rear + maxSize - front) % maxSize;
    }

    // 显示队列的头数据， 注意不是取出数据
    public int headQueue() {
        // 判断
        if (isEmpty()) {
            throw new RuntimeException("队列空的，没有数据~~");
        }
        return arr[front];
    }
}