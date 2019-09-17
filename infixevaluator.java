package projectONE;
/* FAWZIYAH ALEBIOSU;
   CMSC 350 Project 1
   This entire program evaluates ~syntactically accurate ~ infix expressions that holds unsigned integers AND
   contain the four arithmetic operators. It solves this using two stacks.
 */

import javax.swing.*;
import java.util.*;
//necessary for us to use Stack operations and Java GUI


public class infixevaluator {
    /*
    This is the main class that will evaluate the infix expression.
    For the purpose of Object Oriented Programming, it contains several methods
    being used to break up the calculation
     */

    Stack<Integer> operandStack = new Stack<Integer>();
    //this stack will contain the integers being operated on
    Stack<String> operatorStack = new Stack<String>();
    //this stack will contain the operators

    private int  result;
    //variable to hold the result so it can be returned in collectAndDisplay class
    public String tokenArray[];
    //the array that will contain the expression AFTER tokenization


    /*
    -------------------METHOD DEFINITIONS----------------------------------------------
     */
    public String[] tokenizeExpression(String expression) {
        /*this first method will tokenize the infix expression WITHOUT using spaces as delimiters
          To do this, I used regex to split the expression by operators.
          The good thing about regex, is that I am able to use the "delimiters" and still
          include them in my tokenized version
         */

        tokenArray = expression.split("(?<=[-+*/()])|(?=[-+*/()])");
        //this uses .split to tokenize the expression
        return tokenArray;
    }

    public int decidePrecedence(String o) {
            /*
            this SECOND method will decide precedence of the operators
            and pass it's decision to the evaluate method
            1-highest priority
            2-second highest

             */
        int precedence = 0;
         if (o.equals("*") || o.equals("/") ) {
            precedence = 1;
        }

         else if (o.equals("+") || o.equals("-")) {
            precedence = 2;
        }
        return precedence;
    }


    public int calculateOperations(Stack<Integer> operandStack, Stack <String> operatorStack) throws divideByZero {
        /*this THIRD method will do the calculations of operators and operands
         passed into it. To reduce duplication of code, I defined it to simply accept
         both stacks. That way, it does all the popping in the method, as opposed to outside it.

         */

        //initializing and declaring  variables
        int newValue = 0;
        int valueTwo= 0 ;
        int valueOne = 0;
        String operator ="(";

        valueTwo = operandStack.pop();
        valueOne = operandStack.pop();
        operator = operatorStack.pop();

        if (operator.equals( "*")) {
            newValue = valueOne * valueTwo;
        }
        //multiply

        try {
            /* this catches the divideByZero Exception
               if it notices that there is a 0 after a / operator
             */
            if (operator.equals("/") && (valueOne==0 || valueTwo==0)) {
                throw new divideByZero("sorry not possible");
            }
        }
        catch(divideByZero exception){
            //this is where the Java GUI notifies the user of the error
            JOptionPane.showMessageDialog(null, "Division by zero is not possible");

        }

         if (operator.equals( "+")) {
            newValue = valueOne + valueTwo;
        }

         else if (operator.equals( "-")) {
            newValue = valueOne - valueTwo;
        }

         else if(operator.equals("/") && !((valueOne==0 || valueTwo==0))){
             newValue = valueOne / valueTwo;
         }

        return newValue;

    }

    public int evaluateExpression(String[] tokenExpression){
        /*this FOURTH method will do the evaluation of the
        TOKENIZED version of the expression passed to it

         */
        for(int i=0 ;i < tokenArray.length; i++) {
            //while there are more tokens, get the next item/token:

            if (tokenArray[i].equals("(")) {
                //if it is a left paranthesis, push to operator stack
                operatorStack.push(tokenArray[i]);
            }

            else if ((tokenArray[i].matches("[0-9]+"))) {
                //if it is an integer, push to operand stack
                operandStack.push(Integer.parseInt(tokenArray[i]));
            }

            else if (tokenArray[i].matches("[-+*/]")){
                //if it is an operator:
                if (decidePrecedence(tokenArray[i]) == 1) {
                    /*returning 1 means that the operator at the top of the stack
                    a * or / operator
                     */
                    while (!operatorStack.isEmpty() && (operatorStack.peek() == "*" ||
                                                        operatorStack.peek() == "/")) {

                        /*while operator stack is not empty AND the operator at the top
                          has equal precedence, pop two operands and one operator,
                          do calculation.
                         */

                        operandStack.push(calculateOperations(operandStack, operatorStack));
                        //push the result onto the operandstack
                        operatorStack.push(tokenArray[i]);
                        //push the operator to the operator stack
                    }
                    operatorStack.push(tokenArray[i]);
                    //this makes sure the operator is pushed to the stack if the stack was empty

                }
                else if (decidePrecedence(tokenArray[i]) == 2) {
                    //returning a 2 means the operator on top is an + or a -
                    while (!operatorStack.isEmpty() && (operatorStack.peek().equals("+") ||
                            operatorStack.peek().equals("-") ||
                            operatorStack.peek().equals("*") ||
                            operatorStack.peek().equals("/"))) {
                        /*while operator stack is not empty AND the operator at the top
                          has equal precedence, pop two operands and one operator,
                          do calculation.
                         */

                        operandStack.push(calculateOperations(operandStack, operatorStack));
                    }
                    operatorStack.push(tokenArray[i]);
                    //this makes sure the operator is pushed to the stack if the stack was empty
                }
            }

            else if (tokenArray[i].equals(")")) {
                //if it is a right parenthesis though, then we need to evaluate other operators first by doing the following:

                while ((!operatorStack.peek().equals("("))) {
                    /*this will allow us to CALCULATE THE other items while we wait
                     */
                    operandStack.push(calculateOperations(operandStack, operatorStack));
                }
                operatorStack.pop();
                //this will pop off the paranthesis we used as a false bottom: the ( one.
            }
        }

        while((!operatorStack.isEmpty())){
            //after we have exited the entire token array, we check if there is anything left
            operandStack.push(calculateOperations(operandStack,operatorStack));
            //and we calculate what is left in the stack
        }
        //result is set to whatever is at the top of the operand stack
        result = operandStack.peek();
        //and it is returned, to be passed to the displayFunction for the user to see it.
        return result;
    }
}

