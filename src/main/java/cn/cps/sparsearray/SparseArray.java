package cn.cps.sparsearray;

/**
 * @Author: Cai Peishen
 * @Date: 2021/5/19 17:50
 * @Description: 稀疏数组
 */
public class SparseArray {

    public static void main(String[] args) {

        // 创建一个原始的二维数组 11 * 11
        // 0：没有棋子， 1：黑子， 2：白子
        int weight = 11;
        int height = 11;
        int[][] chessArray = new int[weight][height];

        // 初始化数据
        chessArray[1][2] = 1;
        chessArray[2][3] = 2;

        System.out.println("==================原始数组==================");

        // 打印初始化
        for (int[] wNums : chessArray) {
            for (int hNums : wNums) {
                System.out.printf("%d\t", hNums);
            }
            System.out.println("");
        }

        // 稀疏数组第一行：总行数 总列数 数字总数
        int count = 0;
        for (int[] hang : chessArray) {
            for (int hangLie : hang) {
                if (hangLie != 0) {
                    count++;
                }
            }
        }

        System.out.println();
        System.out.println("总行数：" + weight + "，总列数：" + height + "，数字总数：" + count);
        System.out.println();

        // 行数多一行 列数固定为3
        int[][] sparseArray = new int[count+1][3];
        sparseArray[0][0] = weight;
        sparseArray[0][1] = height;
        sparseArray[0][2] = count;

        // 封装稀疏数组
        int index = 0;
        for (int i = 1; i < chessArray.length; i++) {
            for (int j = 0; j < chessArray[0].length; j++) {
                int num = chessArray[i][j];
                if (num != 0) {
                    index ++;
                    sparseArray[index][0] = i;
                    sparseArray[index][1] = j;
                    sparseArray[index][2] = chessArray[i][j];
                }
            }
        }

        System.out.println("==================稀疏数组==================");

        // 打印稀疏数组
        for (int[] wNums : sparseArray) {
            for (int hNums : wNums) {
                System.out.printf("%d\t", hNums);
            }
            System.out.println();
        }

    }

}
