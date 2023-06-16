package tutorial;

import tutorial.Wizard.A;

public class lecture7 {

}


//You can only inherit from one class


//Inheritance summary

class A extends B {} == A is a subclass of B
A has all the fields and methods that B has
//*except for private fields and methods
A can add its own fields and methods
A can only have 1 direct parent (it can have more via a inherance tree)
A can replace a parent's method by re-implementing it
if A doesnt implement something Java searches ancestors




Exceptions

Exception is a class, to create our own exceptions we inherit from

public class MyException extends Exception {}

We have to warn Java about the exception


public object get(int index) throws 
ArrayOutOfBoundException {
  if (index < 0 || index >= size())
      throw new
      ArrayOutOfBoundsException(""+index);
}

Now java expects the code that called get() to deal with 
it by catching it or rethrowing it.


Catching it 

try {
  get(-1);
  
} catch (ArrayOutOfBoundsException err) {
  System.out.printlnt("ohdear!");
}

Rethrowing it

void doBad() throws ArrayOutOfBoundsException {
  get(-1);
}