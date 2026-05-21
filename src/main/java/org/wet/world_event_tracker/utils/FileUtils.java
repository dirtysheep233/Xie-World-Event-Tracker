package org.wet.world_event_tracker.utils;

import org.wet.world_event_tracker.World_event_tracker;

import java.io.*;

public class FileUtils {
    public File createFile(String fileName) throws IOException {
        File file = new File(fileName);
        file.getParentFile().mkdirs();
        if (file.createNewFile()) {
            World_event_tracker.LOGGER.info("Created file {}", fileName);
        }
        else {
            World_event_tracker.LOGGER.error("Couldn't create file {}", fileName);
        }
        return file;
    }

    public void writeFile(File file, String content) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.write(content);
        fw.close();
    }

    public String readFile(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String content = "";
        while ((line = br.readLine()) != null) {
            content += line;
        }
        br.close();
        fr.close();
        return content;
    }
}
