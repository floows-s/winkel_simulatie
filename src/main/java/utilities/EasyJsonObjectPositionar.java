package utilities;

import core.logger.Logger;
import core.logger.TagLogger;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class EasyJsonObjectPositionar {

    private final static Logger log = new TagLogger(EasyJsonObjectPositionar.class.getSimpleName());

    private static Integer first_x;

    public static String toClipboard(String format, int x, int y, boolean concat, boolean snap_x_to_first) {
        if(snap_x_to_first){
            if(first_x == null){
                first_x = x;
            }

            x = first_x;
        }

        String filledInFormat = format;

        // Replace specifiers with given values
        filledInFormat = filledInFormat.replaceAll("%x", Integer.toString(x));
        filledInFormat = filledInFormat.replaceAll("%y", Integer.toString(y));

        // Set clipboard content
        String newContent = filledInFormat;
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        if(concat){
            String clipboardContent = getClipboardContent(clipboard);
            newContent = clipboardContent + newContent;
        }

        StringSelection stringSelection = new StringSelection(newContent);
        clipboard.setContents(stringSelection, null);

        // Return result for good measure
        return filledInFormat;
    }

    private static String getClipboardContent(Clipboard clipboard){
        String content = "";

        if(clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)){
            try{
                content = (String) clipboard.getData(DataFlavor.stringFlavor);
            }catch(Exception ex ){
                log.logError("An error has occurred while trying to get clipboard content: " + ex.getMessage());
            }
        }

        return content;
    }
}
