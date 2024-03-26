package com.ar.vgmsistemas.repository.filesystem;

import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.utils.FileManager;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class PreferenciaRepositoryImpl {

    private static final String PREFS_NAME = "/preferencias.xml";

    @SuppressWarnings("finally")
    public Preferencia loadPreferencias(String path) {
        Preferencia preferencia = null;
        FileInputStream is;
        try {
            is = new FileInputStream(path + PREFS_NAME);
            Serializer serializer = new Persister();
            preferencia = serializer.read(Preferencia.class, is);
        } catch (Exception e) {
            preferencia = new Preferencia();
            savePreferencias(preferencia);
        } finally {
            return preferencia;
        }
    }

    public void savePreferencias(Preferencia preferencia) {
        if (FileManager.isSdPresent()) {
            File file = new File(Preferencia.getPathSistema() + PREFS_NAME);
            if (file.exists()) {
                file.delete();
            }
            try {
                FileOutputStream os = new FileOutputStream(file);
                Serializer serializer = new Persister();
                serializer.write(preferencia, os);
                os.flush();
                os.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
