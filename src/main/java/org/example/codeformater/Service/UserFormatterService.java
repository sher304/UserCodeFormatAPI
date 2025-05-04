package org.example.codeformater.Service;


import com.google.googlejavaformat.java.FormatterException;
import com.google.googlejavaformat.java.JavaFormatterOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.googlejavaformat.java.Formatter;


@Service
public class UserFormatterService {

    Formatter formatter;

    @Autowired
    public UserFormatterService(Formatter formatter) {
        this.formatter = formatter;
    }

    public String makeFormatter(String userCode) throws FormatterException {
        return formatter.formatSource(userCode);
    }
}
