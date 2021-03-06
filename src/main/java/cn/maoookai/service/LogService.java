package cn.maoookai.service;

import java.io.File;
import java.io.IOException;

public interface LogService {
    String log(long line) throws IOException;

    File fileLog();
}
