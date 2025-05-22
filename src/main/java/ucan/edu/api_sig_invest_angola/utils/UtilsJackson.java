package ucan.edu.api_sig_invest_angola.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class UtilsJackson {

    private static final ObjectMapper objectMapper = createObjectMapper();

    private UtilsJackson() {
        // impede instanciação
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // Formatos personalizados para datas
        JavaTimeModule timeModule = new JavaTimeModule();

        // Personaliza o formato de entrada e saída
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));

        timeModule.addSerializer(LocalDate.class, new ToStringSerializer(dateFormatter.getClass()));
        timeModule.addSerializer(LocalDateTime.class, new ToStringSerializer(dateTimeFormatter.getClass()));

        mapper.registerModule(timeModule);

        return mapper;
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) throws JsonProcessingException, JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }

    public static String objectToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T convert(Object source, Class<T> targetClass) {
        return objectMapper.convertValue(source, targetClass);
    }

    public static <T> List<T> jsonToList(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(
                json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)
        );
    }
}
