package exception;

@SuppressWarnings("serial")
public abstract class AutoException extends Exception {
	private int errorno;
	private String errormsg;
	
	public AutoException() {
		super();
		printmyproblem();
	}
	
	public AutoException(String errormsg) {
		super();
		this.errormsg = errormsg;
		printmyproblem();
	}
	
	public AutoException(int errorno) {
		super();
		this.errorno = errorno;
		printmyproblem();
	}
	
	public AutoException(int errorno, String errormsg) {
		super();
		this.errorno = errorno;
		this.errormsg = errormsg;
		printmyproblem();
	}
	
	public int getErrorno() {
		return errorno;
	}
	
	public void setErrorno(int errorno) {
		this.errorno = errorno;
	}
	
	public String getErrormsg() {
		return errormsg;
	}
	
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	
	public void printmyproblem() {
		System.out.println("AutoException [errorno=" + errorno + ", errormsg=" + errormsg + "]"); 
	}
}
