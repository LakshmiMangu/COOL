package cool;

public class Semantic{
	private boolean errorFlag = false;
	public void reportError(String filename, int lineNo, String error){
		errorFlag = true;
		System.err.println(filename+":"+lineNo+": "+error);
	}
	public boolean getErrorFlag(){
		return errorFlag;
	}

/*
	Don't change code above this line
*/
	public Semantic(AST.program program){
		//Write Semantic analyzer code here
		Visit.error_reporter = new ErrorReporter()
		{
			@Override
			public void report(String filename , int linenum , String error)
			{
				reportError(filename,linenum,error);
			}
		};
		Visit.visit(program);
	}
}
