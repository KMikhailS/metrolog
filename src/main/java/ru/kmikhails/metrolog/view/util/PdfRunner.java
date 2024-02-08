package ru.kmikhails.metrolog.view.util;

import ru.kmikhails.metrolog.exception.DeviceException;

import java.io.IOException;

public class PdfRunner {
//    private static final Logger LOG = LogManager.getLogger(PdfRunner.class);

    public void showScan(String filename) {
        try {
            String command = "SumatraPDF.exe " + filename;
//            LOG.info(command);
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            if (!process.isAlive()) {
                throw new DeviceException("Ошибка отображения скана\nОбратитесь в поддержку");
            }
        } catch (IOException e) {
//            LOG.error("Ошибка отображения скана", e);
            throw new DeviceException("Ошибка отображения скана\nОбратитесь в поддержку");
        }
    }
}
