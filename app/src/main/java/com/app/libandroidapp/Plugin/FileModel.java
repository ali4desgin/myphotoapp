package com.app.libandroidapp.Plugin;

import android.graphics.Bitmap;

public class FileModel {

    private String id;
    private String filename;
    private String filesize;
    private String username;
    private byte[] file;

    public String getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getFile() {
        return file;
    }

    public String getFileSize() {
        return filesize;
    }


    public FileModel(String id, String filename,String filesize, String username, byte[] file) {
        this.id = id;
        this.filename = filename;
        this.username = username;
        this.filesize = filesize;
        this.file = file;
    }
}
