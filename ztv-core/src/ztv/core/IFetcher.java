package ztv.core;

public interface IFetcher {
	public void start(String url, String userName, String pwd) throws Exception;
	public int getStatusCode();
	public String getText() throws Exception;
}
