package es.pryades.nadarin.export;

import es.pryades.nadarin.common.AppContext;
import es.pryades.nadarin.common.HasLogger;
import es.pryades.nadarin.common.Utils;
import es.pryades.nadarin.dto.Parameter;
import es.pryades.nadarin.dto.Query;
import lombok.Getter;

public class AsyncExportThread extends Thread implements HasLogger
{
    @Getter
    private AppContext context;
    @Getter
    private String format;

    private Query query;

    public AsyncExportThread( AppContext context, String format, Query query )
    {
        this.context = context;
        this.format = format;
        this.query = query;
    }

    public void run ()
    {
        getLogger().info( "async export started" );

        long totalPages = 1L;
        Integer pageSize = getContext().getIntegerParameter( Parameter.PAR_MAX_ROWS_EXPORTED );

        try
        {
            long rows = 100;//getFieldManagerImp().getNumberOfRows( getContext(), query );

            totalPages = (long)Math.ceil( rows / (double)pageSize );
        }
        catch ( Throwable e )
        {
            Utils.logException( e, getLogger() );
        }

        for ( int i = 0; i < totalPages; i++ )
        {
            getLogger().info( "exporting " + (i+1) + " of " + totalPages );

           /* ByteArrayOutputStream bos = getExportedOutputStream( format, pageSize, (long)(i+1) );

            if ( bos != null )
            {
                String from = getContext().getParameter( Parameter.PAR_MAIL_SENDER_EMAIL );
                String to = getContext().getUser().getEmail();
                String host = getContext().getParameter( Parameter.PAR_MAIL_HOST_ADDRESS );
                String port = getContext().getParameter( Parameter.PAR_MAIL_HOST_PORT );
                String sender = getContext().getParameter( Parameter.PAR_MAIL_SENDER_USER );
                String password = getContext().getParameter( Parameter.PAR_MAIL_SENDER_PASSWORD );

                String text = getContext().getString( "PagedContent.message.text" ).
                        replaceAll( "%user%", getContext().getUser().getName() );
                String subject = getContext().getString( "PagedContent.message.subject" ).
                        replaceAll( "%page%", Long.toString( i+1 ) ).
                        replaceAll( "%total%", Long.toString( totalPages ) );

                String proxyHost = getContext().getParameter( Parameter.PAR_SOCKS5_PROXY_HOST );
                String proxyPort = getContext().getParameter( Parameter.PAR_SOCKS5_PROXY_PORT );

                List<Attachment> attachments = new ArrayList<Attachment>();
                attachments.add(  new Attachment( Utils.getUUID() + "." + format, "application/" + format, bos.toByteArray() ) );

                try
                {
                    Utils.sendMail( from, to, subject, host, port, sender, password, text, attachments, proxyHost, proxyPort, "true".equals( getContext().getParameter( Parameter.PAR_MAIL_AUTH ) ) );
                }
                catch ( Throwable e )
                {
                    Utils.logException( e, getLogger() );
                }
            }*/
        }

        getLogger().info( "async export finished" );
    }
}