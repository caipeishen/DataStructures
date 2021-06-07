package cn.cps.stack;

import java.util.Arrays;

/**
 * @Author: Cai Peishen
 * @Date: 2021/6/2 11:29
 * @Description: 综合计算机
 * 使用栈完成表达式的计算思路
 *  1.通过一个index值（索引）,来遍历我们的表达式
 *  2.如果我们发现是一个数字,就直接入数栈
 *  3.如果发现扫描到是一个符号,就分如下情况
 *  3.1.如果发现当前的符号栈为空，就直接入栈
 *  3.2.如果符号栈有操作符，就进行比较,如果当前的操作符的优先级小于或者等于栈中的操作符，就需要从数栈中pop出两个数,在从符号栈中pop出一个符号，进行运算，将得到结果，入数栈，然后将当前的操作符入符号栈，
 *      如果当前的操作符的优先级大于栈中的操作符，就直接入符号栈.
 *  4.当表达式扫描完毕，就顺序的从数栈和符号栈中pop出相应的数和符号，并运行.
 *  5.最后在数栈只有一个数字,就是表认式的结果
 */
public class Calculator {

    public static void main(String[] args) {
        ArrayStack numStack = new ArrayStack(10);
        ArrayStack operStack = new ArrayStack(10);

        String expression = "7*2*2-5+1-5+3-4"; // 15//如何处理多位数的问题？

        char[] chars = expression.toCharArray();
        for (char c : chars) {
            if (!operStack.isOper(c)) {
                // 数字
                numStack.push(Character.getNumericValue(c));
            } else {
                // 运算符
                if (operStack.isEmpty()) {
                    operStack.push(c);
                } else {
                    int getOper = operStack.stack[operStack.top];
                    int curPriority = operStack.priority(c);
                    int stackPriority = operStack.priority(getOper);

                    if (curPriority <= stackPriority) {
                        int num1 = numStack.pop();
                        int num2 = numStack.pop();
                        int oper = operStack.pop();
                        int cal = numStack.cal(num1, num2, oper);
                        numStack.push(cal);
                    }
                    operStack.push(c);
                }

            }
        }
        System.out.println(numStack.toString());
        System.out.println(operStack.toString());


        while (true) {
            if (numStack.top == 0) {
                break;
            }
            int num1 = numStack.pop();
            int num2 = numStack.pop();
            int oper = operStack.pop();
            int value = numStack.cal(num1, num2, oper);
            numStack.push(value);
        }
        System.out.println("结果为："+numStack.pop());



    }


    static class ArrayStack {
        private int maxSize; // 栈的大小
        private int[] stack; // 数组，数组模拟栈，数据就放在该数组
        private int top = -1;// top表示栈顶，初始化为-1

        //构造器
        public ArrayStack(int maxSize) {
            this.maxSize = maxSize;
            stack = new int[this.maxSize];
        }

        /**
         * 是否为空
         * @return
         */
        public boolean isEmpty() {
            return top == -1 ? true : false;
        }

        /**
         * 是否已满
         * @return
         */
        public boolean isFull() {
            return top == (maxSize - 1) ? true : false;
        }

        /**
         * 栈中添加元素
         * @param value
         */
        public void push(int value) {
            if (isFull()) {
                System.out.println("该栈已满");
                return;
            }
            top++;
            stack[top] = value;
        }

        /**
         * 栈中取值
         * @return
         */
        public int pop() {
            if (isEmpty()) {
                throw new RuntimeException("该栈已空不能取值");
            }
            int value = stack[top];
            top--;
            return value;
        }

        /**
         * 判断是否为运算符
         * @param val
         * @return
         */
        private boolean isOper(char val) {
            return val == '+' || val == '-' || val == '*' || val == '/' || val == '%';
        }

        /**
         * 数字越大，则优先级就越高.
         * 返回运算符的优先级，优先级是程序员来确定, 优先级使用数字表示
         * @param oper
         * @return
         */
        public int priority(int oper) {
            if(oper == '*' || oper == '/' || oper == '%'){
                return 1;
            } else if (oper == '+' || oper == '-') {
                return 0;
            } else {
                return -1; // 假定目前的表达式只有 +, - , * , /, %
            }
        }

        /**
         * 计算方法
         * @param num1
         * @param num2
         * @param oper
         * @return
         */
        public int cal(int num1, int num2, int oper) {
            int res = 0; // res 用于存放计算的结果
            switch (oper) {
                case '+':
                    res = num1 + num2;
                    break;
                case '-':
                    res = num2 - num1;// 注意顺序
                    break;
                case '*':
                    res = num1 * num2;
                    break;
                case '/':
                    res = num2 / num1;
                    break;
                case '%':
                    res = num2 % num1;
                    break;
                default:
                    break;
            }
            return res;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("ArrayStack{");
            sb.append("maxSize=").append(maxSize);
            sb.append(",stack=").append(Arrays.toString(stack));
            sb.append(",top=").append(top);
            sb.append('}');
            return sb.toString();
        }


    }

}
