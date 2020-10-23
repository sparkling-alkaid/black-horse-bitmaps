package com.glab.black.horse.bitmap.service;

import com.glab.black.horse.bitmap.pojo.entity.QueryRequest;
import com.glab.black.horse.bitmap.pojo.entity.QueryResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Stack;

public class BitmapQueryService {
    private static HashSet<Character>supportOpertors = new HashSet<Character>();

    static {
        supportOpertors.add('*');
        supportOpertors.add('+');
        supportOpertors.add('/');
        supportOpertors.add('-');
        supportOpertors.add('(');
        supportOpertors.add(')');
    }

    public QueryResponse query(QueryRequest request){
        QueryResponse response = new QueryResponse();
        if(StringUtils.isEmpty(request.getRule())){
           return response;
        }
        String rule = request.getRule().replace(">=","+").replace("<=","-");
        char[] arr = rule.toCharArray();
        Stack<Character> characterStack = new Stack<>();
        Stack<String> integerStack = new Stack<>();
        int result = setStackDate(arr, characterStack, integerStack);
        return response;
    }


     /**
     * 将拆分好的字符依次放入两个栈中
     *
     * @param arr            需要运算的字符
     * @param characterStack 符号运算符
     * @param integerStack   数字运算符
     */
     private static int setStackDate(char[] arr, Stack<Character> characterStack, Stack<String> integerStack) {
         StringBuilder sb = new StringBuilder();
         for (int i = 0; i < arr.length; i++) {
                 if (arr[i] == '(') {
                         continue;
                     }
                 if (arr[i] == ')') {
                     // 3.根据规则,弹栈并运算
                     calculate(characterStack, integerStack);
                     continue;
                     }
                 // 如果是数字
                if(supportOpertors.contains(arr[i])){
                    characterStack.push(arr[i]);
                    integerStack.push(sb.toString());
                }else{
                    sb.append(arr[i]);
//                     integerStack.push(Integer.valueOf(String.valueOf(arr[i])));
                 }
         }
         // 将栈中,最后的结果算出
         String field = integerStack.pop();
         String value = integerStack.pop();
         integerStack.push(value);
         integerStack.push(field);
         return calculate(characterStack, integerStack);
     }
     /**
      * 根据规则进行计算
       * <p>
      * 规则:
      * (2*(1+3)+8)/4
      * integerStack弹出两位,与characterStack弹出的一位运算符进行计算,并把结果压回到integerStack,直到characterStack为空
      *
      * @param characterStack 字符栈
      * @param integerStack   数字栈
      */
private static int calculate(Stack<Character> characterStack, Stack<String> integerStack) {
    Integer sum = 0;
    while (!characterStack.empty()) {
            String field  = integerStack.pop();
            String value = integerStack.pop();
            System.out.println("num2 = " + field);
            System.out.println("num2 = " + field);
            Character operator = characterStack.pop();
            if (operator == '+') {
//                    sum = num2 + num1;
                } else if (operator == '-') {
//                    sum = num2 - num1;
                 } else if (operator == '*') {
//                     sum = num2 * num1;
                 } else if (operator == '/') {
//                     sum = num2 / num1;
                 }
             integerStack.push();
         }
     return sum;
    }

    public static void main(String[] args) {

        String expression = "(12*(14+3)+8)/4";
         Stack<Character> characterStack = new Stack<>();
                 Stack<String> integerStack = new Stack<>();
                 // 1.拆分字符串
                 char[] arr = expression.toCharArray();
                 // 2.将拆分好的字符依次放入两个栈中
                 int result = setStackDate(arr, characterStack, integerStack);

                System.out.println(expression + "=" + result);
             }

}
