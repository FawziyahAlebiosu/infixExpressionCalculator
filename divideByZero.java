package projectONE;
/* FAWZIYAH ALEBIOSU;
   CMSC 350 Project 1
   This entire program evaluates ~syntactically accurate ~ infix expressions that holds unsigned integers AND
   contain the four arithmetic operators. It solves this using two stacks.
 */

public class divideByZero extends IllegalArgumentException{
    /*this is the class that will throw a DivideByZero Exception
    if the expression contains a divideByZero.

     */
    public divideByZero(String message){
        super(message);
    }
}