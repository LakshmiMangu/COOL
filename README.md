Structure of the Code :

Code is divided into three parts.
	1. Inheritance Graph
	2. Type Checking for Classes,Attributes and Methods.
	3. Type checking for Expressions.

1.Inheritance Graph (Inheritance_graph.java,ErrorReporter.java) :
	
Inheritance_graph.java :

	public void initialise() : Checks if the Class Main is present.If Main is present,it calls cycle detection function after the linking is done to check if there are any cycles are 					   present.
	private void linking() : Every node is linked to it's parent and child nodes.This function is called in initialise and then cycle detection is called in initialise.
	
	public boolean conforming() : According to conformation rules in COOL manual,if type1 conforms to type2 it returns TRUE.

	private boolean cycle_detection() : Detects if any cycle is present in the graph.

	There are other simple functions like  get_list_nodes,get_parent etc which return the list of nodes in the graph and parent of node respectively(These can be understood from the name 		of function).

ErrorReporter.java :
	This provides an interface to report errors.
	
2.Type Checking for Classes,Attributes and Methods (Visit.java,ErrorReporter.java) : 

Visit.java :
	public void visit(AST.program prog) : Visits the program and initialises the function public void initialise() and calls the public void visit(AST.class_ classname) iteratively.
	public void visit(AST.class_ classname) : Visits the class and calls the public void visit(AST.method met) or public void visit(AST.attribute attr)  iteratively depending upon the instance.
	public void visit(AST.attribute attr): Visits the attribute and checks if the attribute has valid name in it's scope and it calls the Type checking for Expressions boides.
	public void visit(AST.method met) : Visits the methods and checks if the method has valid and valid formals in it's scope and it calls the Type checking for Expressions boides.
	private void method_redefinition(),private void attribute_redifinition() : Check if the attributes and methods are valid.

	Valid Method : A method is valid if 
		       ->It's defined return type conforms to the type returned by it's body.
		       ->It's name is unique in it's local scope.	
		       ->If it's defined in parent and child class,the types in both of them should match.
	Valid Method is added to method_scopetable.
	
	scope_table : This stores all types,names of methods,classes,attributes.
	method_scopetable  : This table stores the mangled names of the methods.

	Mangling : It's used to verify if defined return type of method conforms to the type returned by it's body.
	public static String mangled_name_type(),public static String mangled_name_class() : They return MAngled name of the method with list_of_formals and function and name,type respectively.

	private void programvisitor(node) : It does a depth first search starting from the node. 

ErrorReporter.java :
	This provides an interface to report errors.


Design Aspects :
-> In inheritance_graph.java, nodes are stored in array_list .
-> Cycle detection is  implimented using the linked list and appending the list based upon the cyclic properties of classes. 
-> For traversal,Depth-First Search is used to preserve Inheritance. 
-> While checking for mangling,mangled name and it's type are stored using a hashmap and are used to veerify the validity of the method.
-> The scope tables for methods and classes are different because the method is local within a class and the attributes can be redefined more than once in the different method of same class.


** The code submitted is giving errors because of incompleteness in the expression checking.The functional part of the code(Inheritance Graph) is present here.
Test Cases :
test1.cl : Correct code checking the inheritace graph.
test2.cl : Cycle Present in the inheritance graph.
 

	
