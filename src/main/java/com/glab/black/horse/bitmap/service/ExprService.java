package com.glab.bitmap.service;

import com.google.common.base.Stopwatch;
import org.roaringbitmap.RoaringBitmap;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * ( app1 & app2 ) | ! app3
 */
@Component
public class ExprService {


    public int[] exec(String expression) {
        Stopwatch started = Stopwatch.createStarted();
        List<String> work = work(trim(expression));
        List<String> strings = infixToPostfix(work);
        RoaringBitmap rr = doCal(strings);
        started.stop();
        //System.out.println("expression:[" + expression + "];rr size:" + RamUsageEstimator.humanSizeOf(rr) + ";user count:" + rr.getLongCardinality() + ";time spent:" + started);
        if (rr.getLongCardinality() >= 100_0000) {
            return null;
        }
        return rr.toArray();
    }

    private static final char[] op = {'&', '|', '!', '(', ')'};
    private static final String[] strOp = {"&", "|", "!", "(", ")"};

    private String trim(String expression) {
        return expression.replaceAll(" ", "");
    }

    private boolean isOp(char c) {
        for (char value : op) {
            if (value == c) {
                return true;
            }
        }
        return false;
    }

    private boolean isOp(String s) {
        for (String value : strOp) {
            if (value.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private List<String> work(String str) {
        List<String> list = new ArrayList<>();
        char c;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (!isOp(c)) {
                sb.append(c);
            }
            if (isOp(c)) {
                if (sb.toString().length() > 0) {
                    list.add(sb.toString());
                    sb.delete(0, sb.toString().length());
                }
                list.add(c + "");
            }
        }
        if (sb.toString().length() > 0) {
            list.add(sb.toString());
            sb.delete(0, sb.toString().length());
        }
        return list;
    }

    private void printList(List<String> list) {
        for (String o : list) {
            System.out.print(o + " ");
        }
    }

    private List<String> infixToPostfix(List<String> list) {
        List<String> postfixList = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            switch (s) {
                case "(":
                case "!":
                    stack.push(s);
                    break;
                case "&":
                case "|":
                    if (!stack.empty()) {
                        while (!(stack.peek().equals("("))) {
                            postfixList.add(stack.pop());
                            if (stack.empty()) {
                                break;
                            }
                        }
                    }
                    stack.push(s);
                    break;
                case ")":
                    while (!(stack.peek().equals("("))) {
                        postfixList.add(stack.pop());
                    }
                    stack.pop();
                    break;
                default:
                    postfixList.add(s);
                    break;
            }
            if (i == list.size() - 1) {
                while (!stack.empty()) {
                    postfixList.add(stack.pop());
                }
            }
        }
        return postfixList;
    }

    private RoaringBitmap doCal(List<String> list) {
        Stack<RoaringBitmap> stack = new Stack<>();
        for (String s : list) {
            if (!isOp(s)) {
                RoaringBitmap byTag = null;
                try {
                    //TODO
                    byTag = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                RoaringBitmap byTag = getByTag(s);
                stack.push(byTag);
            } else {
                switch (s) {
                    case "&": {
                        RoaringBitmap pop1 = stack.pop();
                        RoaringBitmap pop2 = stack.pop();
                        pop1.and(pop2);
                        stack.push(pop1);
                        break;
                    }
                    case "|": {
                        RoaringBitmap pop1 = stack.pop();
                        RoaringBitmap pop2 = stack.pop();
                        pop1.or(pop2);
                        stack.push(pop1);
                        break;
                    }
                    case "!": {
                        RoaringBitmap pop1 = stack.pop();
                        RoaringBitmap pop2 = getAllRr(pop1.getLongCardinality());
                        pop2.andNot(pop1);
                        stack.push(pop2);
                        break;
                    }
                }
            }
        }
        return stack.pop();
    }

    private static RoaringBitmap getAllRr(long size) {
        RoaringBitmap rr = new RoaringBitmap();
        for (int i = 1; i <= size; i++) {
            rr.add(i);
        }
        return rr;
    }
}
