import java.io.Console;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.*;

/*
 * A linked-list based stack implementation.
 * 'value' of the node is generic.
 * Operations supported: push, pop, isEmpty()
 * 
 * @author: Ravi Agrawal
 */
class LinkedStack<Item>{
    private Node first;

    private class Node{
        Item value;
        Node next;
    }
    
    public void push(Item value){
        Node oldFirst = first;
        Node newNode = new Node();
        newNode.value = value;
        newNode.next = oldFirst;
        first = newNode;
    }
    
    public Item pop(){
        Item oldValue = first.value;
        first = first.next;
        return oldValue;
    }
    
    public boolean isEmpty(){
        return (first == null);
    }
}

/*
 * Implementation of Dijkstra's Two Stack algorithm.
 * Operators supported: '+', '-', '*', '/'.
 * 
 * @author: Ravi Agrawal
 * @date: August 2014
 */
public class DijkstraTwoStack{
    /**
     * Method returns the result of evaluating an 
     * algebraic expression using Dijkstra's Two
     * Stack algorithm.
     *
     * @param expression: the String expression to evaluate.
     * A sample expression: (2*(3+4))
     */
    public static int evaluate(String expression){
        LinkedStack<String> operatorStack = new LinkedStack<String>();
        LinkedStack<Integer> operandStack =  new LinkedStack<Integer>();
        int exp_len = expression.length();
        int operand = 0;
        
        // Dijkstra's two-stack algorithm
        for (int i = 0; i < exp_len; i++){
            char catchChar = expression.charAt(i);
            
            // Ignore left paranthesis
            if (catchChar == '(')
                continue;
            
            // Capture all digits of a number
            if (catchChar >= '0' && catchChar <= '9'){
                operand = operand * 10 + Integer.parseInt(String.valueOf(catchChar));
            } else{
                // All digits captured, push operand on stack
                if (operand != 0){
                     operandStack.push(operand);
                    operand = 0;
                }
                // Push operator on stack
                if (String.valueOf(catchChar).matches("(\\+?-?\\*?/?)")){
                     operatorStack.push(String.valueOf(catchChar));
                }
                // Pop out two operands and push back the result
                // of evaluating them with a popped operator.
                if (catchChar == ')'){
                    int operand1 = operandStack.pop();
                    int operand2 = operandStack.pop();
                    String operator = operatorStack.pop();
 
                    switch (operator){
                        case "+": 
                            operandStack.push(operand2 + operand1);
                            break;
                        case "-":
                            operandStack.push(operand2 - operand1);
                            break;
                        case "*":
                            operandStack.push(operand2 * operand1);
                            break;
                        case "/":
                            operandStack.push(operand2 / operand1);
                            break;
                        default:
                            System.err.println("Operator " + operator + 
                                               " not recognized");
                            System.exit(1);
                    }
                }
            }
        }
        return operandStack.pop();
    }
    
    public static void main(String[] args){
        Console c = System.console();
        if (c == null){
            System.err.println("No console");
            System.exit(1);
        }
        String expression = c.readLine("Enter an algebraic expression to evaluate: ");
        System.out.println("Result: " + evaluate(expression));
    }    
}