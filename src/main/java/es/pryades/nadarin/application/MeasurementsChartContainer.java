package es.pryades.nadarin.application;

public interface MeasurementsChartContainer
{
	int getFromHour();
	int getToHour();
	
	String getVariableParameter( String variable, String key );
}
