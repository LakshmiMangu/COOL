package cool;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuffer;

    public class Visit {

	public static Inheritance_graph inheritance_graph;
	public static ErrorReporter error_reporter;
	public static String filename;
	// public static String currentclass="";
	// public static Map<String,String> manglemap = new HashMap<>();
	//static manglemap = new HashMap<>();

	public static void visit(AST.program prog){

		inheritance_graph = new Inheritance_graph();
		for(AST.class_ c : prog.classes)
		{
			inheritance_graph.add_class(c);
		}
		//calling the initialise funtion to check inheritance graph properties
		inheritance_graph.initialise();

	}

}

	// for(Inheritance_graph.Node every_node : inheritance_graph.get_list_nodes())
		// {
		// 	for(AST.feature new_feature : every_node.getAstClass().features)
		// 	{
		// 		if(new_feature instanceof AST.method)
		// 		{
		// 			AST.method new_method = (AST.method) new_feature;
		// 			manglemap.put(mangled_name_class(every_node.getAstClass().name,new_method.formals,new_method.name),new_method.typeid);
		// 		}
		// 	}
		// }
		// Inheritance_graph.Node root = inheritance_graph.get_root();
		// programvisitor(root);


	
// 	public static ScopeTable<String> method_scopetable = new ScopeTable<>();
// 	public static ScopeTable<String> scope_table = new ScopeTable<>();

// 	//static scope_table = new ScopeTable<>();
// 	//static method_scopetable = new ScopeTable<>();

// 	private void method_redefinition(AST.method meth)
// 	{
// 		//already the method is in local class
// 		if(method_scopetable.lookUpLocal(meth.name)!=null)
// 		{
// 			error_reporter.report(filename,meth.get_line_num(),new StringBuffer().append("Method ").append(meth).append(" defined more than once ").toString());
// 		}
// 		else
// 		{
// 			//if return type has not been defined
// 			if(!(inheritance_graph.class_exists(meth.typeid)))
// 			{
// 				error_reporter.report(filename,meth.get_line_num(),new StringBuffer().append("Method ").append(meth).append(" has no defined return type ").toString());
// 				meth.typeid = "Object";
// 			}

// 			String mangledname = mangled_name_type(meth.name,meth.typeid,meth.formals);
// 			String global_mangledname = method_scopetable.lookUpGlobal(meth.name) ;

// 			//if method defined on both parent and current classes but donot conform to the same return types
// 			if((global_mangledname!=null)&&(global_mangledname != mangledname))
// 			{
// 				error_reporter.report(filename,meth.get_line_num(),new StringBuffer().append("Method ").append(meth).append(" has been defined in parent class of ").append(meth).append(" and both of these aren't following the same return type").toString());
// 			}

// 			//if there's no complication above , add the method to method_scopetable
// 			method_scopetable.insert(meth.name,mangledname);
// 		}
// 	}

// 	public static String mangled_name_type(String type , String function , List<AST.formal> formals)
// 	{
// 		StringBuffer building_mangledname = new StringBuffer();
// 		//add_mangledname_type(building_mangledname,type);
// 		building_mangledname.append("TypeName_");
		
// 		if(type == null)
// 		{
// 			building_mangledname.append(0);
// 		}
// 		else
// 		{
// 			building_mangledname.append(type).append("_");
// 		}
// 		//add_mangledname_function(building_mangledname,function);
		
// 		building_mangledname.append("FunctionName_");
		
// 		if(function == null)
// 		{
// 			building_mangledname.append(0);
// 		}
// 		else
// 		{
// 			building_mangledname.append(function).append("_");
// 		}

// 		building_mangledname.append("Formals_");
// 		if(formals!=null)
// 		{
// 			int k=0;
// 			for(AST.formal form : formals)
// 			{
// 				k++;
// 				building_mangledname.append(k).append(form.typeid).append("_");
// 			}
// 		}
// 		else
// 		{
// 			building_mangledname.append("formals_is_empty");
// 		}

// 		return building_mangledname.toString();
// 	}

// 	// public static void add_mangledname_type(String name , String type)
// 	// {
// 	// 	name.append("TypeName_");
// 	// 	if(type == null)
// 	// 	{
// 	// 		name.append(null);
// 	// 	}
// 	// 	else
// 	// 	{
// 	// 		name.append(type).append("_");
// 	// 	}
// 	// }

// 	// public static void add_mangledname_function(String name , String function)
// 	// {
// 	// 	name.append("FunctionName_");
// 	// 	if(function == null)
// 	// 	{
// 	// 		name.append(null);
// 	// 	}
// 	// 	else
// 	// 	{
// 	// 		name.append(function).append("_");
// 	// 	}
// 	// }

// 	private void attribute_redefinition(AST.attr attrib)
// 	{
// 		if(scope_table.lookUpGlobal(attrib.name) == null)
// 		{
// 			scope_table.insert(attrib.name,attrib.typeid);
// 		}
// 		else
// 		{
// 			if(scope_table.lookUpLocal(attrib.name)==null)
// 			{
// 				error_reporter.report(filename,attrib.get_line_num(),new StringBuffer().append("Attribute ").append(attrib.name).append(" name is taken already in parent class").toString());
// 			}
// 			else
// 			{
// 				error_reporter.report(filename,attrib.get_line_num(),new StringBuffer().append("Attribute ").append(attrib.name).append(" name is taken already in ").append(currentclass).toString());
// 			}
// 		}
// 	}

// 	public void visit(AST.class_ c)
// 	{
// 		currentclass = c.name;
// 		for(AST.feature fea : c.features)
// 		{
// 			if(fea instanceof AST.attr)
// 			{
// 				AST.attr new_attribute = (AST.attr) fea;
// 				attribute_redefinition(new_attribute);
// 			}
// 			else
// 			{
// 				AST.method new_method = (AST.method) fea;
// 				method_redefinition(new_method);
// 			}
// 		}

// 		if(c.name == "Main")
// 		{
// 			int k=0;
			
			
// 			String main_name = method_scopetable.lookUpLocal("main");
// 			if(main_name == null)
// 			{
// 				error_reporter.report(filename,c.get_line_num(),new StringBuffer().append("The class ").append(c).append(" has no 'main' defined").toString());
// 			}
// 			// if(main_name != null)
// 			// {
// 			// 	error_reporter.report(filename,c.get_line_num(),new StringBuffer().append("The method main in class ").append(c.name).append(" has arguments passed").toString());
// 			// }
// 		}

// 		for(AST.feature fea : c.features)
// 		{
// 			fea.being_visited(this);
// 		}

// 	}

// 	public void visit(AST.attr attrib)
// 	{
// 		if("self" == attrib.name)
// 		{
// 			//since self should not be considered as attribute name
// 			scope_table.remove(attrib.name);
// 			error_reporter.report(filename,attrib.get_line_num(),new StringBuffer().append("Attribute with 'self' name cannot be considered").toString());
// 			//passing expression??
// 		}
// 		//if the attribute type is not defined
// 		else if(!(inheritance_graph.class_exists(attrib.typeid)))
// 		{
// 			error_reporter.report(filename,attrib.get_line_num(),new StringBuffer().append("Type of attribute ").append(attrib).append(" has not been defined in the class").toString());
// 			//to continue compilation
// 			scope_table.insert(attrib.name,"Object");
// 			//passing expression
// 		}
// 		else
// 		{
// 			return ;
// 			//passing the expression
// 			//checking for presence of expression
// 		}
// 	}

// 	public void visit(AST.method method)
// 	{
// 		scope_table.enterScope();
// 		List<String> list_of_formals = new ArrayList<>();
// 		for(AST.formal form : method.formals)
// 		{
// 			if(form.name == "self")
// 			{
// 				error_reporter.report(filename,form.get_line_num(),new StringBuffer().append("Formal in the method ").append(method.name).append(" has the name 'self'").toString());
// 			}
// 			else if(list_of_formals.contains(form.name))
// 			{
// 				error_reporter.report(filename,form.get_line_num(),new StringBuffer().append("Formal ").append(form.name).append(" in the method ").append(method.name).append(" has the been defined more than once").toString());
// 			}
// 			else 
// 			{
// 				list_of_formals.add(form.name);
// 			}

// 			if(!(inheritance_graph.class_exists(form.typeid)))
// 			{
// 				error_reporter.report(filename,form.get_line_num(),new StringBuffer().append("Type for ").append(form.name).append(" is not defined").toString());
// 			}
// 			else
// 			{
// 				scope_table.insert(form.name,form.typeid);
// 			}
// 		}


// 		//method.body.expr_check(this)
// 		//method.body.type is expressions ka returnttytpe from expressions ka body
// 		if(!(inheritance_graph.Conforming(method.typeid , method.body.type)))
// 		{
// 			error_reporter.report(filename,method.get_line_num(),new StringBuffer().append("Defined return type and return type of expression body of ").append(method.name).append(" are not conforming").toString());
// 		}

// 		scope_table.exitScope();
// 	}

// 	private void programvisitor(Inheritance_graph.Node node)
// 	{
// 		scope_table.enterScope();
// 		method_scopetable.enterScope();

// 		node.getAstClass().being_visited(this);
// 		for (Inheritance_graph.Node every_child : node.get_child_list())
// 		{
// 			programvisitor(every_child);
// 		}

// 		method_scopetable.exitScope();
// 		scope_table.exitScope();
// 	}

// 	public static String mangled_name_class(String classname,List<AST.formal> formal_list , String function)
// 	{
// 		StringBuffer building_mangledname = new StringBuffer();
		
// 		building_mangledname.append("ClassName_");
// 		if(classname == null)
// 		{
// 			building_mangledname.append(0);
// 		}
// 		else
// 		{
// 			building_mangledname.append(classname).append("_");
// 		}

// 		building_mangledname.append("FunctionName_");
		
// 		if(function == null)
// 		{
// 			building_mangledname.append(0);
// 		}
// 		else
// 		{
// 			building_mangledname.append(function).append("_");
// 		}

// 		building_mangledname.append("Formals_");
// 		{
// 			if(formal_list!=null)
// 			{
// 				int k=0;
// 				for(AST.formal form : formal_list)
// 				{
// 					k++;
// 					building_mangledname.append(k).append(form.typeid).append("_");
// 				}
// 			}
// 			else
// 			{
// 				building_mangledname.append("formals_is_empty");
// 			}

// 			return building_mangledname.toString();
// 		}

// 	}

// }