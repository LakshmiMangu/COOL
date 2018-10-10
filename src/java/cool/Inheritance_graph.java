package cool;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Arrays;
import java.lang.StringBuffer;
import java.util.Collections;
import java.util.*;

//function to get graph from the given classes 
public class Inheritance_graph
{
	private List<Node> list_nodes;
	private Map<String,Integer> classtoindexmap;
	private boolean main_exists;
	private static int Root_index = 0;

	private static AST.class_ Root_class = new AST.class_("Object",null,null,new ArrayList<>(),0);
	private static Node Root_node = new Node(Root_class,Root_index);

	public Node get_root()
	{
		return Root_node;
	}

	public Inheritance_graph()
	{
		list_nodes = new ArrayList<>();
		classtoindexmap = new HashMap<>();
		main_exists = false;
		add_base_classes();
	}

	//adding the base classes to the list of classes
	private void add_base_classes()
	{
		add_object();
		add_io();
		add_string();

		classtoindexmap.put("int",-1);
		classtoindexmap.put("boolean",-1);
	}

	private void add_object()
	{
		Root_class.features = new ArrayList<>();
		Root_class.features.add(new AST.method("abort",new ArrayList<>(),"Object",null,0));
		Root_class.features.add(new AST.method("type_name",new ArrayList<>(),"String",null,0));
		Root_class.features.add(new AST.method("copy",new ArrayList<>(),"Object",null,0));

		classtoindexmap.put("Object",Root_index);
		list_nodes.add(Root_node);
	}

	private void add_io()
	{
		List<AST.formal> stringlist = new ArrayList<>(Arrays.asList(new AST.formal("x","String",0)));
		List<AST.feature> io_features = new ArrayList<>();
		List<AST.formal> intlist = new ArrayList<>(Arrays.asList(new AST.formal("x","Int",0)));

		io_features.add(new AST.method("out_string",stringlist,"IO",null,0));
		io_features.add(new AST.method("out_int",intlist,"IO",null,0));
		io_features.add(new AST.method("in_string",stringlist,"String",null,0));
		io_features.add(new AST.method("in_int",intlist,"Int",null,0));

		AST.class_ io_astclass = new AST.class_("IO",null,"Object",io_features,0);
		Node io_node = new Node(io_astclass,0);

		classtoindexmap.put("IO",list_nodes.size());
		list_nodes.add(io_node);
	}

	private void add_string()
	{
		List<AST.formal> stringlist = new ArrayList<>(Arrays.asList(new AST.formal("x","String",0)));
		List<AST.formal> intlist = new ArrayList<>(Arrays.asList(new AST.formal("x","Int", 0),new AST.formal("y","Int", 0)));
        List<AST.feature> stringFeatures = new ArrayList<>();

        stringFeatures.add(new AST.method("length", new ArrayList<>(),"Int", null, 0));
        stringFeatures.add(new AST.method("concat", stringlist,"String", null, 0));
        stringFeatures.add(new AST.method("substr", intlist, "String", null, 0));

        AST.class_ string_astClass = new AST.class_("String", null,"Object", stringFeatures, 0);
        Node stringNode = new Node(string_astClass, 0);

        classtoindexmap.put("String",list_nodes.size());
		list_nodes.add(stringNode);
	}

	//defining class node to define all the characteristics of each class
	public static class Node
	{
		public static int no_parent = -1;
		private AST.class_ ast_class;
		private int index;
		private Node parent_node;
		private List<Node> child_list;

		
		private Node(AST.class_ ast_class , int index)
		{
			this.ast_class = ast_class;
			this.index = index;
			this.child_list = new ArrayList<>();
			this.parent_node = null;
		}

		public void add_child(Node child)
		{
			child_list.add(child);
		}

		public AST.class_ getAstClass()
		{
			return ast_class;
		}

		public int get_index()
		{
			return index;
		}

		public Node get_parent()
		{
			return parent_node;
		}

		public void set_parent(Node parent_node)
		{
			this.parent_node =  parent_node;
		}

		public List<Node> get_child_list()
		{
			return child_list;
		}

	}

	public List<Node> get_list_nodes()
	{
		return list_nodes;
	}

	//checking if the class name is restricted 
	private boolean Restricted_class(AST.class_ name)
	{
		return "IO" == name.toString() || "Int" == name.toString() || "String" == name.toString() || "Bool" == name.toString();
	}

	//hecking if the class can be inherited from or not
	private boolean Restricted_inheritance_class(AST.class_ name)
	{
		return "Int" == name.toString() || "String" == name.toString() || "Bool" == name.toString();
	}

	private boolean class_without_methods(Node name)
	{
		return "Int" == name.parent_node.toString() || "Bool" == name.parent_node.toString();
	}

	//function to add a class to the graph
	public void add_class(AST.class_ ast_class)
	{
		//if class with the name already exists
		if(classtoindexmap.containsKey(ast_class.name))
		{
			Visit.error_reporter.report(Visit.filename,ast_class.get_line_num(),new StringBuffer().append("class ").append(ast_class.name).append(" redefined").toString());
		}
		//if the class name is restricted
		else if(Restricted_class(ast_class))
		{
			Visit.error_reporter.report(Visit.filename,ast_class.get_line_num(),new StringBuffer().append("Base class ").append(ast_class.name).append(" cannot be redefined").toString());
		}
		else 
		{
			classtoindexmap.put(ast_class.name,list_nodes.size());
			list_nodes.add(new Node(ast_class,list_nodes.size()));

			//chehcking if the given class is main class
			if("Main".equals(ast_class.name))
			{
				main_exists = true;
			}
		}
	}

	public String get_parent_node(String given_class)
	{
		Node class_node = list_nodes.get(classtoindexmap.get(given_class));
		return class_node.getAstClass().parent;
	}

	//linking a parent node to the child node and vice-versa
	private void linking()
	{
		for (Node node : list_nodes)
		{
			//if parent of the given node's class is present
			if(node.getAstClass().parent!=null)
			{
				//if the class inherits from classes restricted for inheritance
				if(Restricted_inheritance_class(node.getAstClass()))
				{
					Visit.error_reporter.report(Visit.filename,node.getAstClass().get_line_num(),new StringBuffer().append("Base Class ").append(node.getAstClass().parent).append(" cannot be inherited ").toString());
				}
				//linking the parent and child respectively
				else if(classtoindexmap.containsKey(node.getAstClass().parent))
				{
					node.set_parent(list_nodes.get(classtoindexmap.get(node.getAstClass().parent)));
					list_nodes.get(classtoindexmap.get(node.getAstClass().parent)).add_child(node);
				}
				else 
				{
					Visit.error_reporter.report(Visit.filename,node.getAstClass().get_line_num(),new StringBuffer().append("Inherited class for ").append(node.getAstClass().name).append(" is not declared ").toString());
				}
			}
			//if parent of the node is absent
			else
			{
				if(!("Object"==node.getAstClass().name))
				{
					node.set_parent(Root_node);
					Root_node.add_child(node);
				}
			}
		}
	}

	//initialises the graph formation
	public void initialise()
	{
		linking();

		//
		if(!main_exists)
		{
			Visit.error_reporter.report(Visit.filename,0,new StringBuffer().append("Class 'Main' is missing").toString());
		}

		List<Boolean> checked = new ArrayList<>();
		List<Boolean> stacking = new ArrayList<>();
		LinkedList<Node> list = new LinkedList<>();
		
		for(int i = 0 ; i < list_nodes.size() ; i++)
		{
			checked.add(false);
			stacking.add(false);
		} 

		for(int i = 3 ; i < list_nodes.size() ; i++)
		{
			if(cycle_detection(i,checked,stacking,list))
			{
				
				Visit.error_reporter.report(Visit.filename,0,new StringBuffer().append("Cycle detected in inheritance of classes").toString());
			}
		}


	}

	//detects if there are any cycles.
	private boolean cycle_detection(int integer , List<Boolean> checked ,List<Boolean> stacking , LinkedList<Node> list )
	{
		Node node = list_nodes.get(integer);
		list.addLast(node);
		
		if(checked.get(integer)==false)
		{
			stacking.set(integer,true);
			checked.set(integer,true);
			if(node.parent_node!=null)
			{
				int num = node.get_parent().get_index();
				if(num != -1)
				{
					if( !checked.get(num) && cycle_detection(num,checked,stacking,list)||stacking.get(num))
					{
						return true;
					}
				}
			}

		}
		
		list.removeLast();
		stacking.set(integer,false);
		return false;
	}

	//one conforming to two
	public boolean Conforming(String one,String two)
	{
		if( one == two || two == "Object")
		{
			return true;
		}
		
		Node node_one = list_nodes.get(classtoindexmap.get(one));
		Node node_two = list_nodes.get(classtoindexmap.get(two));
		
		if(Restricted_inheritance_class(node_one.getAstClass()) || Restricted_class(node_two.getAstClass()))
		{
			return false;
		}
		while(node_two.parent_node!=null)
		{
			node_two = node_two.get_parent();
			if(node_two.getAstClass().name == node_one.getAstClass().name)
			{
				return true;
			}
		}
		return false;
	}

	


}