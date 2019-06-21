package es.pryades.nadarin.dto;

import es.pryades.nadarin.common.AppContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
*
* @author dismer.ronda 
* @since 1.0.0.0
*/

@EqualsAndHashCode(callSuper=true)
@Data
public class Task extends BaseDto
{
	private static final long serialVersionUID = -2274211758046839179L;

	private String timezone;
	private String language;
	private String month;
	private String day;
	private String hour;
	private String minute;
	private Integer times;

	private Long ref_user;

	private Integer clazz;
	private String description;
	private String details;

	private String user_name;

	public String getTaskClazzAsString( AppContext ctx )
   	{
   		return ctx.getString( "task.clazz." + getClazz() ); 
   	}
}
