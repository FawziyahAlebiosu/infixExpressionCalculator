package projectONE;

/* FAWZIYAH ALEBIOSU;
   CMSC 350 Project 1
   This entire program evaluates ~syntactically accurate ~ infix expressions that holds unsigned integers AND
   contain the four arithmetic operators. It solves this using two stacks.
 */


import javax.swing.*;
//allows for Java GUI input pane and message dialog to be possible
public class collectAndDisplay{
    /*
    This is the class that will COLLECT the infix expression(from the user)
     and DISPLAY the result of the expression passed to it from the infixevaluator class
     */

    private String expression;//variable to hold the infix expression

    private String result;//variable to hold the result

    private static int resultOne;//static variable to store result and pass it to a display method

    //getter and setter methods for the two non-static variables

    public String getExpression(){
        return expression;
    }
    public void setExpression(String expression){
        this.expression = expression;
    }

    public String getResult(){
        return expression;
    }
    public void setResult(String result){
        //variable to hold the infix expression to be evaluated
        this.result = result;
    }

    /*
    --------------these are the definitions of the methods created to  collect and display input------------------
     */

    public String collectInput(){
        //this method will collect the infix expression from user using Java GUI
        expression = JOptionPane.showInputDialog("Enter an infix expression, you wish for me to calculate");

        JOptionPane.showMessageDialog(null, "To confirm, you would like me to calculate: " + expression);
        return expression;
    }

    public void displayResult(int result, String expression){
        //this method will display the result passed into it FROM infixevaluator class to the user
        JOptionPane.showMessageDialog(null, "The result of " + expression + " is: " + result);
    }
/*
---------------------MAIN METHOD----------------------------------------------------------
 */
    public static void main (String args[])  {
        collectAndDisplay userOne = new collectAndDisplay();
        //create object to call methods in collectanddisplay class

        String expressionOne = userOne.collectInput();
        //pass THE user input a.k.a the infix expression into a variable

        infixevaluator infixevaluatorobject = new infixevaluator();
        //create another object to call the methods in the infixevaluator class

        String[] tokenizedExpression = infixevaluatorobject.tokenizeExpression(expressionOne);
        //pass the tokenized version of user input into a variable

        resultOne = infixevaluatorobject.evaluateExpression(tokenizedExpression);
        //pass the variable that holds tokenized version into evaluateExpression method

        userOne.displayResult(resultOne, expressionOne);
        //pass the result of evaluating the expression into displayResult method which will use JAVA GUI to do so

    }
}
