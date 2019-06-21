package es.pryades.nadarin.common;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import lombok.Data;

import org.apache.http.HttpHost;
import org.apache.ibatis.session.SqlSession;

import es.pryades.nadarin.dal.ibatis.DalManager;
import es.pryades.nadarin.dto.Parameter;
import es.pryades.nadarin.dto.ProfileRight;
import es.pryades.nadarin.dto.User;

/**
 * @author Dismer Ronda
 *
 */
@Data
public class AppContext extends Object implements Serializable, HasLogger
{
	private static final long serialVersionUID = -899983198743845287L;

	private SqlSession session;
	private User user;
	private List<ProfileRight> rights;
	private HashMap<Long, Parameter> parameters;
	private HashMap<String,Object> extra;
	private long start;
	private String language;

	public AppContext( )
	{
		session = null;

		extra = new HashMap<String,Object>();
	}
	
	public AppContext( String language )
	{
		this();
		this.language = language;
	}
	
	protected void finalize() throws Throwable 
	{
		closeSession();
	}

	public SqlSession openSession() throws BaseException
	{
		if ( getSession() != null )
			throw new BaseException( new Exception( "SQL session MUST be closed previously" ), getLogger(), BaseException.CONNECTION );
			
		if ( getSession() == null )
		{
			start = new Date().getTime();
			
			setSession( DalManager.openSession() );
		}
		
		return getSession();
	}
	
	public void closeSession() throws BaseException
	{
		if ( getSession() != null )
		{
			session.close();
			
			if ( getLogger().isDebugEnabled() )
				getLogger().debug( "session " + getSession().hashCode() + " consumed " + (new Date().getTime() - start) + " ms" );
			
			setSession( null );
		}
	}

	public boolean isPasswordExpired() throws BaseException
    {
		return CalendarUtils.isExpired( getUser().getChanged(), getIntegerParameter( Parameter.PAR_PASSWORD_VALID_TIME ) );
    }
	
	public void addData( String key, Object value )
	{
		extra.put( key, value );
	}
	
	public Object getData( String key )
	{
		return extra.get( key );
	}
	
	public void removeData( String key )
	{
		extra.remove( key );
	}
	
	public void clearData()
	{
		extra.clear();
	}
	
	public boolean hasRight( String code )
	{
		for ( ProfileRight right : rights )
			if ( code.equals( right.getRight_code() ) )
				return true;

		return false;
	}
	
	public String getString( String key )
	{
		return key;//I18N.get( key, language );
	}

	public String getParameter( long id )
	{
		Parameter parameter = parameters.get( id );
		
		if ( parameter != null ) 
			return parameter.getValue();
		
		return "";
	}

	public Long getLongParameter( long id )
	{
		return Long.parseLong( parameters.get( id ).getValue() );
	}

	public Double getDoubleParameter( long id )
	{
		return Double.parseDouble( parameters.get( id ).getValue() );
	}

	public Integer getIntegerParameter( long id )
	{
		return Integer.parseInt( parameters.get( id ).getValue() );
	}
	
	private Double getRelease()
	{
		if ( getUser().isAppTester() )
			return getDoubleParameter( Parameter.PAR_PRE_RELEASE );
		
		return getDoubleParameter( Parameter.PAR_RELEASE );
	}
	
	public boolean isReleased( double release )
	{
		return getUser().getRef_profile().equals( Constants.ID_PROFILE_DEVELOPER ) ? true : (getRelease() >= release);
	}
	
	public HttpHost getHttpProxy()
	{
		HttpHost proxy = null;
		
		String proxyHost = getParameter( Parameter.PAR_HTTP_PROXY_HOST );
		
		if ( !proxyHost.isEmpty() )
		{
			Integer proxyPort = 80;
			
			try
			{
				proxyPort = getIntegerParameter( Parameter.PAR_HTTP_PROXY_PORT );
			}
			catch ( Throwable e )
			{
			}

			proxy = new HttpHost( proxyHost, proxyPort );
		}
		
		return proxy;
	}
	
	public boolean isLogEnabled( long id, String operation )
	{
		return getParameter( id ).contains( operation );
	}
}
