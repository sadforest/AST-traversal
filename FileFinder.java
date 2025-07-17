import java.io.*;
import java.util.*;

public class FileFinder {
    public static char[] fileToCharArray(File file) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        final StringBuffer buffer = new StringBuffer();
        String line = null;
        while (null != (line = in.readLine())) {
            buffer.append(line).append("\n");
        }

        return buffer.toString().toCharArray();
    }

    public static List<char[]> getFilesInDirectory(String directory) throws IOException {
        List<File> discoveredFiles = new ArrayList<File>();
        List<char[]> charArrayFiles = new ArrayList<char[]>();

        try {
            File dir = new File(directory);
            if (dir.isDirectory()){
                discoveredFiles.addAll(Arrays.asList(dir.listFiles()));
                boolean dirPresent = false;
                do {
                    dirPresent = false;
                    List<File> searchFiles = new ArrayList<File>();
                    for (File file : discoveredFiles) {
                        dirPresent = dirPresent || file.isDirectory();
                        if (file.isDirectory()){
                            searchFiles.addAll(Arrays.asList(file.listFiles()));
                        }
                        else if (file.getName().endsWith(".java")){
                            searchFiles.add(file);
                        }
                    }

                    discoveredFiles = searchFiles;
                } while (dirPresent);

            } else {
                discoveredFiles.add(dir);
            }

        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

        for(int i = 0; i < discoveredFiles.size(); i++){
            charArrayFiles.add(fileToCharArray(discoveredFiles.get(i)));
        }

        return charArrayFiles;
    }
}
