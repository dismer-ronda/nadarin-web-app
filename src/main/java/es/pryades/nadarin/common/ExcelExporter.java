package es.pryades.nadarin.common;

import java.io.InputStream;

public interface ExcelExporter
{
	InputStream getExcelStream();
	String getFileName();
}
