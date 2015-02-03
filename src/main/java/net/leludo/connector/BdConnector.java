package net.leludo.connector;

import java.net.URI;

public class BdConnector
{

    private String driverClass;
    private String url;
    private String username;
    private String password;

    
    public BdConnector() {
	}

    public BdConnector(URI dbUri) {
		System.out.println(dbUri.toString());
		System.out.println(dbUri.getUserInfo());
		
		username = dbUri.getUserInfo().split(":")[0] ;
		password = dbUri.getUserInfo().split(":")[1] ;
		
		url = "jdbc:"+dbUri.getScheme()+"://"+dbUri.getHost()+(dbUri.getPort()==-1?"":":"+dbUri.getPort())+dbUri.getPath()+"?"+dbUri.getQuery();
		System.out.println(url);

	}

	/**
     * @return the driverClass
     */
    public String getDriverClass()
    {
        return driverClass;
    }

    /**
     * @param driverClass
     *            the driverClass to set
     */
    public void setDriverClass(String driverClass)
    {
        this.driverClass = driverClass;
    }

    /**
     * @return the url
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
}
