package cool;

interface ErrorReporter{
	public void report(String filename , int linenum , String error);
}