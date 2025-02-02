package memoranda.util;

/**
 * Storage.java
 * Created on 12.02.2003, 0:21:40 Alex
 * Package: net.sf.memoranda.util
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.text.html.HTMLEditorKit;

import memoranda.ui.ExceptionDialog;
import nu.xom.Builder;
import nu.xom.Document;


/**
 *
 */
/*$Id: FileStorage.java,v 1.15 2006/10/09 23:31:58 alexeya Exp $*/
public class FileStorage implements Storage {

    public static String JN_DOCPATH = Util.getEnvDir();
    private HTMLEditorKit editorKit = new HTMLEditorKit();

    public FileStorage() {
        /*The 'MEMORANDA_HOME' key is an undocumented feature for
          hacking the default location (Util.getEnvDir()) of the memoranda
          storage dir. Note that memoranda.config file is always placed at fixed
          location (Util.getEnvDir()) anyway */
        String mHome = (String) Configuration.get("MEMORANDA_HOME");
        if (mHome.length() > 0) {
            JN_DOCPATH = mHome;
            /*DEBUG*/
            System.out.println("[DEBUG]***Memoranda storage path has set to: " +
                    JN_DOCPATH);
        }
    }

    public static void saveDocument(Document doc, String filePath) {
        /**
         * @todo: Configurable parameters
         */
        try {
            /*The XOM bug: reserved characters are not escaped*/
            //Serializer serializer = new Serializer(new FileOutputStream(filePath), "UTF-8");
            //serializer.write(doc);

            OutputStreamWriter fw =
                    new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
            fw.write(doc.toXML());
            fw.flush();
            fw.close();
        }
        catch (IOException ex) {
            new ExceptionDialog(
                    ex,
                    "Failed to write a document to " + filePath,
                    "");
        }
    }

    public static Document openDocument(InputStream in) throws Exception {
        Builder builder = new Builder();
        return builder.build(new InputStreamReader(in, "UTF-8"));
    }

    public static Document openDocument(String filePath) {
        try {
            return openDocument(new FileInputStream(filePath));
        }
        catch (Exception ex) {
            new ExceptionDialog(
                    ex,
                    "Failed to read a document from " + filePath,
                    "");
        }
        return null;
    }

    public static boolean documentExists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * @see memoranda.util.Storage#openMimeTypesList()
     */
    public void openMimeTypesList() {
        if (!new File(JN_DOCPATH + ".mimetypes").exists()) {
            try {
                MimeTypesList._doc =
                        openDocument(
                                FileStorage.class.getResourceAsStream(
                                        "/util/default.mimetypes"));
            }
            catch (Exception e) {
                new ExceptionDialog(
                        e,
                        "Failed to read default mimetypes config from resources",
                        "");
            }
            return;
        }
        /*DEBUG*/
        System.out.println(
                "[DEBUG] Open mimetypes list: " + JN_DOCPATH + ".mimetypes");
        MimeTypesList._doc = openDocument(JN_DOCPATH + ".mimetypes");
    }
    /**
     * @see memoranda.util.Storage#storeMimeTypesList()
     */
    public void storeMimeTypesList() {
        /*DEBUG*/
        System.out.println(
                "[DEBUG] Save mimetypes list: " + JN_DOCPATH + ".mimetypes");
        saveDocument(MimeTypesList._doc, JN_DOCPATH + ".mimetypes");
    }
    /**
     * @see memoranda.util.Storage#restoreContext()
     */
    public void restoreContext() {
        try {
            /*DEBUG*/
            System.out.println(
                    "[DEBUG] Open context: " + JN_DOCPATH + ".context");
            Context.context.load(new FileInputStream(JN_DOCPATH + ".context"));
        }
        catch (Exception ex) {
            /*DEBUG*/
            System.out.println("Context created.");
        }
    }
    /**
     * @see memoranda.util.Storage#storeContext()
     */
    public void storeContext() {
        try {
            /*DEBUG*/
            System.out.println(
                    "[DEBUG] Save context: " + JN_DOCPATH + ".context");
            Context.context.save(new FileOutputStream(JN_DOCPATH + ".context"));
        }
        catch (Exception ex) {
            new ExceptionDialog(
                    ex,
                    "Failed to store context to " + JN_DOCPATH + ".context",
                    "");
        }
    }

}
