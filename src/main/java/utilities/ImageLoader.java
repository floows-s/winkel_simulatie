package utilities;


import core.logger.Logger;
import core.logger.TagLogger;
import javafx.scene.image.Image;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/// A util tool for loading an array of images.
public class ImageLoader {

    private static Logger log = new TagLogger(ImageLoader.class.getSimpleName());


    /// Loads all image files from a folder as an ordered list.
    ///
    /// Files are sorted in ascending order before loading, so list order follows file naming.
    /// Entries that are not files, or that cannot be loaded as images, are skipped.
    ///
    /// @param folderURI path of the folder containing the images in the {@code resource} folder.
    /// @return the loaded images in ascending file order. An empty list if the folder is empty,
    ///         or {@code null} if the URI is {@code null}, is not a folder, or the folder cannot be read.
    public static List<Image> fromFolder(String folderURI){
        if(folderURI == null){
            log.logError("Failed to load animation. Given folder URI is null. Returning null.");
            return null;
        }

        // Convert URI to load from resources
        URL folderResourceURL = ImageLoader.class.getResource(folderURI);
        if(folderResourceURL == null){
            log.logError("Failed to load animation. Resource not found. Returning null. URI: " + folderURI);
            return null;
        }

        // Create file object for folder
        File folder;
        try{
            folder = new File(folderResourceURL.toURI());
        } catch (URISyntaxException | IllegalArgumentException e) {
            log.logError("Failed to load images from folder. Couldn't convert resource to a file. Returning null. URI: " + folderURI);
            return null;
        }

        if(!folder.isDirectory()){
            log.logError("Failed to load animation. Given folder URI does not lead to a folder. Returning null. URI: " + folderURI);
            return null;
        }

        // Retrieve files
        File[] files = folder.listFiles();
        if(files == null){
            log.logError("Failed to retrieve files from folder. I/O error. Returning null. URI: " + folderURI);
            return null;
        }

        if(files.length == 0){
            log.logWarning("No files found in the given folder. Returning empty list. URI: " + folderURI);
            return List.of();
        }

        // Sort files into ascending order, to gerente an order.
        Arrays.sort(files);

        // Load images
        List<Image> images = new ArrayList<>();
        for(File f : files){
            if(!f.isFile()) continue;

            Image loadedImage;

            try{
                loadedImage = new Image(folderResourceURL.toExternalForm() + f.getName());
            }catch(IllegalArgumentException e){
                log.logWarning("Couldn't load " + f.getName() + " as a image. Skipping." );
                continue;
            }

            images.add(loadedImage);
        }

        return images;
    }

}
