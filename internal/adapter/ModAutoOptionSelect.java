package adapter;

public interface ModAutoOptionSelect {
	public double getTotalPrice(String modelname);
	
	public void setOptionChoice(String modelname, String setName, String optionName);
	
	public String getSelectedOptions(String modelname);
}
