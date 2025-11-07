package com.interview.genG;

public class MoveZeros {
    public static void main(String[] args) {
        int[] nums = {1,0,3,5,0,1,5};
        MoveZeros.moveZeroes(nums);
    }
    public static void moveZeroes(int[] nums) {
        int insertPOS = 0;
        for (int num : nums) {
            if (num != 0){
                nums[insertPOS++] = num;
            }
        }
        while (insertPOS < nums.length){
            nums[insertPOS++] = 0;
        }
        System.out.println("Final Array");
        for (int j : nums){
            System.out.print(j + " ");
        }
    }
}
