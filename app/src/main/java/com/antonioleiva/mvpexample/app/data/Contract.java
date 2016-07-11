package com.antonioleiva.mvpexample.app.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ebpearls on 7/7/2016.
 */
public class Contract {

    /**
     * The Content Authority is a name for the entire content provider, similar to the relationship
     * <p>
     * between a domain name and its website. A convenient string to use for content authority is
     * <p>
     * the package name for the app, since it is guaranteed to be unique on the device.
     */

    public static final String CONTENT_AUTHORITY = "com.antonioleiva.mvpexample.provider";

    /**
     * The content authority is used to create the base of all URIs which apps will use to
     * <p>
     * contact this content provider.
     */

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * A list of possible paths that will be appended to the base URI for each of the different
     * <p>
     * tables.
     */

    public static final String PATH_COMPANY = "company";

    public static final String PATH_FOLDERS = "folder";

    /**
     * Create one class for each table that handles all information regarding the table schema and
     * the URIs related to it.
     * It should
     * 1) extends from BaseColumns,which includes an _ID string that is used for the auto increment id of each table.
     * 2) include the MIME types of return queries (which are either a directory of multiple rows, or a single item),
     * 3) include a method to build a URI for an individual row in that table.
     */
    public static final class Company implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_COMPANY).build();
        // These are special type prefixes that specify if a URI returns a list or a specific item
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_COMPANY;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_COMPANY;

        // Define the table schema
        public static final String TABLE_NAME = "companyTable";
        public static final String COLUMN_COMPANY_ID = "companyId";
        public static final String COLUMN_COMPANY_NAME = "movieReleaseDate";
        public static final String COLUMN_BRANDING_COLOR = "branding_color";
        public static final String COLUMN_BACKGROUND_IMAGE = "background_image";
        public static final String COLUMN_COMPANY_STATE = "company_state";
        public static final String COLUMN_COMPANY_USER_ID = "company_user_id";

        // Define a function to build a URI to find a specific movie by it's identifier
        public static Uri buildCompanyUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class Folders implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FOLDERS).build();
        public static String CONTENT_TYPE = "vnd.android.cursor.dir" + CONTENT_URI + "/" + PATH_FOLDERS;
        public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item" + CONTENT_URI + "/" + PATH_FOLDERS;
        // Define the table schema
        public static final String TABLE_NAME = "companyTable";
        public static final String COLUMN_COMPANY_ID = "companyId";
        public static final String COLUMN_COMPANY_NAME = "movieReleaseDate";
        public static final String COLUMN_BRANDING_COLOR = "branding_color";
        public static final String COLUMN_BACKGROUND_IMAGE = "background_image";
        public static final String COLUMN_COMPANY_STATE = "company_state";
        public static final String COLUMN_COMPANY_USER_ID = "company_user_id";

        public static Uri buildFolderUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}

