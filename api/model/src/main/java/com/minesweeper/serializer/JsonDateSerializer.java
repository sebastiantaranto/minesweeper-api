package com.minesweeper.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class JsonDateSerializer extends JsonSerializer<Date> {

	private static final String ISO_8601_DATE_WITH_MILLIS_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	private static final String UTC = "UTC";

	public JsonDateSerializer() {
	}

	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_8601_DATE_WITH_MILLIS_FORMAT, Locale.getDefault());
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone(UTC));
		String dateAsString = simpleDateFormat.format(date);
		jsonGenerator.writeString(dateAsString);
	}

}
