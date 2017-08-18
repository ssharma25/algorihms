package com;

public class Solution {
    public static int lengthOfLongestSubstring(String s) {
        int max = 0;
        s = s + "1";
        String index;
        String present = "";
        int i = 0;
        int count = 0;
        while(i<s.length()-2){
            index = Character.toString(s.charAt(i));
            System.out.println(present.contains(index));
          while(!present.contains(Character.toString(s.charAt(i))) && !Character.toString(s.charAt(i)).equals("1")) {
             index = Character.toString(s.charAt(i));
             present = present + index;
             count = count + 1;
             /*if(i == s.length()-1){
            	 break;
             }*/
             
              i = i+1;
          }
          System.out.println(count);
            if(max < count){
                max = count;
            }
            count = 0;
            present = "";
        }
        return max;
    }
    public static void main(String [] args){
    	System.out.println(lengthOfLongestSubstring("au"));
    }
}