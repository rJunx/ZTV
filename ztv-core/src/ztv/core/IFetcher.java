package ztv.core;

public interface IFetcher {
	public void start(String url, String userName, String pwd);
	public int getStatusCode();
	public String getText();
}
