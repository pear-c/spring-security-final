package com.example.springsecurityfinal.config.converter;

import com.example.springsecurityfinal.domain.Member;
import com.example.springsecurityfinal.domain.Role;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.*;
import java.lang.reflect.Field;

public class CsvHttpMessageConverter extends AbstractHttpMessageConverter<Member> {
    public CsvHttpMessageConverter() {
        super(new MediaType("text", "csv"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    protected Member readInternal(Class<? extends Member> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {

        try(BufferedReader br = new BufferedReader(new InputStreamReader(inputMessage.getBody()))) {
            String line = br.readLine();
            String[] fields = line.split(",");

            String id = fields[0].trim();
            String name = fields[1].trim();
            String password = fields[2].trim();
            Integer age = Integer.valueOf(fields[3].trim());
            Role role = Role.valueOf(fields[4].trim().toUpperCase());

            return new Member(id, name, password, age, role);
        }
    }

    @Override
    protected boolean canRead(MediaType mediaType) {
        return true;
    }

    @Override
    protected void writeInternal(Member member, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        Field[] fields = member.getClass().getDeclaredFields();

        outputMessage.getHeaders().setContentType(MediaType.valueOf("text/csv; charset=UTF-8"));
        try (Writer writer = new OutputStreamWriter(outputMessage.getBody())) {
            for(int i=0; i<fields.length; i++) {
                writer.write(fields[i].getName());
                if(i < fields.length - 1) {
                    writer.write(',');
                }
            }
            writer.write(System.lineSeparator());
            writer.write(member.toString());
        }
    }
}
