package cn.maoookai.service.impl;

import cn.maoookai.service.LogService;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class LogServiceImpl implements LogService {

    @Override
    public String log(long line) throws IOException {
        ReversedLinesFileReader fileReader = new ReversedLinesFileReader(new File("mirai.log"), Charset.defaultCharset());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < line; i++) {
            stringBuilder.append(fileReader.readLine()).append('\n');
        }
        return stringBuilder.toString();
    }

    @Override
    public File fileLog() {
        return new File("mirai.log");
    }

}
