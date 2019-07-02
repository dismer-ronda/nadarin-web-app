package es.pryades.nadarin.tasks;

import java.io.BufferedReader;
import java.io.Serializable;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import es.pryades.nadarin.common.*;
import es.pryades.nadarin.ui.common.HasAppContext;
import org.apache.log4j.Logger;

import es.pryades.nadarin.dto.Parameter;
import es.pryades.nadarin.dto.Task;
import es.pryades.nadarin.dto.User;
import es.pryades.nadarin.ioc.IOCManager;

public class DatabaseUpdateTaskAction implements HasAppContext, HasLogger, TaskAction, Serializable {
    private static final long serialVersionUID = 7452056074022160387L;

    public DatabaseUpdateTaskAction() {
    }

    private void notifyUser(AppContext ctx, User user, String body) {
        try {
            String from = ctx.getParameter(Parameter.PAR_MAIL_SENDER_EMAIL);
            String to = user.getEmail();
            String host = ctx.getParameter(Parameter.PAR_MAIL_HOST_ADDRESS);
            String port = ctx.getParameter(Parameter.PAR_MAIL_HOST_PORT);
            String sender = ctx.getParameter(Parameter.PAR_MAIL_SENDER_USER);
            String password = ctx.getParameter(Parameter.PAR_MAIL_SENDER_PASSWORD);

            String text = ctx.getString("tasks.database.update.message.text").
                    replaceAll("%user%", user.getName());

            String proxyHost = ctx.getParameter(Parameter.PAR_SOCKS5_PROXY_HOST);
            String proxyPort = ctx.getParameter(Parameter.PAR_SOCKS5_PROXY_PORT);

            Utils.sendMail(from, to, ctx.getString("tasks.database.update.message.subject"), host, port, sender, password, text + "\n" + body, null, proxyHost, proxyPort, "true".equals(ctx.getParameter(Parameter.PAR_MAIL_AUTH)));
        } catch (Throwable e) {
            getLogger().error("Error", e);
        }
    }

    private String executeUpdate(Connection conn, String queryString) {
        try {
            Statement statement = conn.createStatement();

            return Integer.toString(statement.executeUpdate(queryString));
        } catch (Throwable e) {
            getLogger().error("Error", e);

            return "fail";
        }
    }

    private String readCreateTable(BufferedReader bufferedReader) {
        String queryString = "";

        while (true) {
            try {
                String line = bufferedReader.readLine();

                queryString += line + "\n";

                if (line.startsWith(");"))
                    break;
            } catch (Throwable e) {
                getLogger().error("Error", e);
            }
        }

        return queryString;
    }


    @Override
    public void doTask(AppContext ctx, Task task, boolean forced) throws BaseException {
        getLogger().info("-------- started");

        DatabaseUpdateTaskData data = (DatabaseUpdateTaskData) Utils.toPojo(task.getDetails(), DatabaseUpdateTaskData.class, false);

        User queryUser = new User();
        queryUser.setId(task.getRef_user());
        User user = (User) IOCManager._UsersManager.getRow(ctx, queryUser);

        String body = "";

        try {
            Class.forName(Settings.DB_driver);
            Connection conn = DriverManager.getConnection(Settings.DB_url, Settings.DB_user, Settings.DB_password);

            StringReader reader = new StringReader(data.getSql());

            BufferedReader bufferedReader = new BufferedReader(reader);

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.startsWith("--") && !line.isEmpty()) {
                    if (line.startsWith("create table")) {
                        String queryString = line + "\n" + readCreateTable(bufferedReader);
                        body += queryString + " -> " + executeUpdate(conn, queryString) + "\n";
                    } else {
                        body += line + " -> " + executeUpdate(conn, line) + "\n";
                    }
                }
            }

            conn.close();

            bufferedReader.close();
        } catch (Throwable e) {
            body += " error";
            getLogger().error("Error", e);
        }

        notifyUser(ctx, user, body);

        getLogger().info("-------- finished");
    }

    @Override
	public TaskActionDataEditor getTaskDataEditor( )
	{
		return new DatabaseUpdateTaskDataEditor( );
	}

    @Override
    public boolean isUserEnabledForTask() {
        return getContext().getUser().getRef_profile().equals(Constants.ID_PROFILE_DEVELOPER);
    }
}
