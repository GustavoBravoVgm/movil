package com.ar.vgmsistemas.utils;

import com.ar.vgmsistemas.entity.Preferencia;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Logging {

    private static final Logger logger = Logger.getLogger("com.ar.vgmsistemas.Logging");
    private static FileHandler fileHandler;

    public static Logger getInstance() {
        if (fileHandler == null)
            new Logging();
        return logger;
    }

    public Logging() {
        try {
            String path = Preferencia.getPathSistema() + "/preventa.log";
            File file = new File(path);
            if (!file.exists())
                file.createNewFile();

            fileHandler = new FileHandler(path, true);
            logger.addHandler(fileHandler);
        } catch (Exception ex) {

        }

    }
}
