package app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Strings {

    public static final String UNDER_CONSTRUCTION = "OOPS! This part of application is currently under construction...";
    public static final String ABOUT_TEXT = "Studio Managemment System (2018).\n" +
            "\n" +
            "Version: 1.0 Release\n" +
            "Build: 20180406-0013\n" +
            "Developed by: Oleksandr FRANKIV, Nazar TKACH,\n" +
            "Anton SHERSTIUK, Artur IVANENKO\n";

    public static final String MENU_ALBUM = "Album";
    public static final String MENU_ALBUM_VIEW = "View albums";
    public static final String MENU_ALBUM_NEW = "New...";
    public static final String MENU_ALBUM_EDIT_CURRENT = "Edit current...";

    public static final String MENU_SALES = "Sale";
    public static final String MENU_SALES_VIEW = "View sales";
    public static final String MENU_SALES_NEW = "New...";
    public static final String MENU_SALES_LICENSES = "Manage licenses";
//    public static final String MENU_ALBUM_EDIT_CURRENT = "Edit current...";
//    public static final String MENU_ALBUM_EDIT_CURRENT = "Edit current...";
    ///////
    public static final String MENU_MUSICIAN = "Musician";
    public static final String MENU_MUSICIAN_VIEW = "View musicians";

    public static final String MENU_HELP = "Help";
    public static final String MENU_HELP_DOCS = "Documentation";
    public static final String MENU_HELP_ABOUT = "About";

    public static final String DIALOG_NEW_PRICE = "Please specify new price: ";
    public static final String DIALOG_NUMBER_FORMAT_ERROR = "Please input a correct number";
    public static final String DIALOG_INVALID_NUMBER_ERROR = "Please input a number between 0 and 1";
    public static final String DIALOG_MUSICIAN_REMOVED_FROM_SONG = "Artist's fee share removed from this song";
    public static final String DIALOG_ILLEGAL_PRICE_CHANGE_ERROR = "Cannot change price more often than once a day";
    public static final String DIALOG_EMPTY_NAME_ERROR = "Please specify name";
    public static final String DIALOG_EMPTY_DATE_ERROR = "Please specify date";

    public static final String DIALOG_NEW_TITLE = "New album:";
    public static final String DIALOG_EDIT_TITLE = "Edit album:";

    public static final String MENU_SONG_VIEW = "View songs";
    public static final String MENU_SONG = "Song";
    public static final String MENU_SONG_EDIT = "Edit...";
    public static final String MENU_SONG_NEW = "New...";


    public static final String MENU_SONG_CHANGE_SHARE = "Change share";
    public static final String  DIALOG_FAILED_INSERTIONS_SONG = " Failed at insertion of song";
    public static final String  DIALOG_EMPTY_AUTHOR_ERROR = "Please input author name";
    public static final String  DIALOG_WRONG_ALBUM_ERROR = "Wrong album name";
    public static final String DIALOG_ADD_MUSICIAN = "Add musician";
    public static final String DIALOG_MUSICIAN_ADDED_TO_SONG  = "SUCCESS. Musician added";
    public static final String DIALOG_NEW_SONG_TITLE="New song:";
    public static final String DIALOG_EDIT_SONG_TITLE="Edit song:";
    public static final String DIALOG_CHOOSE_SONG ="Please choose a song";
    public static final String MENU_MUSICIAN_SHARE = "Musicians share";
    public static final String SHARE_CHANGED = "Share changed";
	public static final String INPUT_WRONG_SHARE = "Wrong format of share.Share must be in range from 0 to 1";
    public static final String CHANGED_SONG= "Song is edited";
    public static final String INSERTED_SONG = "Song is added";

    ////////
    public static final String SELECT_MUSICIAN_BUTTON = "Select";
    public static final String FIELD_MUSICIAN_LABEL = "Album:";
    //////

    public static final String SELECT_ALBUM_BUTTON = "Select";
    public static final String FIELD_ALBUM_LABEL = "Album:";
    public static final String FIELD_OPEARION_LABEL = "Operation:";
    public static final String OPERATION_OPTION_NONE = "-";
    public static final String OPERATION_OPTION_RECORD_PURCHASE = "record purchase";
    public static final String OPERATION_OPTION_MONTHLY_PAYMENT = "monthly payment";
    public static final String SALES_NEW_LABEL_CLIENT = "Client:";
    public static final String SALES_NEW_LABEL_QTY = "Quantity:";
    public static final String SALES_NEW_LABEL_LICENSE = "License:";
    public static final String SALES_NEW_LABEL_COST = "Cost:";
    public static final String SALES_NEW_LABEL_MONTHS = "Months:";
    public static final String SALES_NEW_BTN_CONFIRM = "Confirm";
    public static final String SALES_NEW_CONFIRMATION_TITLE = "Confirm payment";
    public static final String SALES_NEW_CONFIRMATION_PLEASE_CHECK = "Please check all the details and click 'OK' to complete the payment:\n";
    public static final String SALES_NEW_CONFIRMATION_REVENUES = "\nThe artists are going to receive the following revenues:";
    public static final String SALES_NEW_CONFIRMATION_MESSAGE_RECORD = SALES_NEW_CONFIRMATION_PLEASE_CHECK +
            "\n----------- RECORD PURCHASE ----------- \n" +
            "Album's title: '%s' (#%s)\n" +
            "Price: %s UAH  Quantity: %s \n" +
            "Total: %s UAH \n" +
            "Client's name: %s \n\n" +
            "%s \n" +
            SALES_NEW_CONFIRMATION_REVENUES;
    public static final String SALES_NEW_CONFIRMATION_MESSAGE_LICENSE = SALES_NEW_CONFIRMATION_PLEASE_CHECK +
            "\n----------- LICENSE PAYMENT ----------- \n" +
            "License #%s (album '%s', expires %s)\n" +
            "Client's name: %s \n" +
            "Total: %s UAH \n" +
            "Accrued for: %s \n" +
            "Expected status: %s \n\n" +
            "%s \n" +
            SALES_NEW_CONFIRMATION_REVENUES;
    public static final String SALES_LICENSE_TITLE = "Licenses";
    public static final String SALES_LICENSE_HIDE_PAID_CHECKBOX = " hide paid";
    public static final String SALES_LICENSE_NEW = "New license";
    public static final String SALES_LICENSE_MONTHLY_PAYM = "Monthly paym.";
    public static final String SALES_LICENSE_CREATE = "Create";
    public static final String SALES_LICENSE_CLOSE = "Close";
    public static final String SALES_LICENSE_DELETE = "DELETE";
    public static final String SALES_LICENSE_EDIT = "EDIT";
    public static final String SALES_LICENSE_EDIT_ALBUM = "Album:  \t";
    public static final String SALES_LICENSE_EDIT_PURCHASED = "\tPurchased: \t";
    public static final String SALES_LICENSE_EDIT_CLIENT_NAME = "Client name: \t";
    public static final String SALES_LICENSE_EDIT_CHANGE_DURATION = "Change duration by, mo.: \t";

    public static final String[] SALES_MAIN_TABLE_LABELS = { "Date", "Album", "Musician", "Operation", "Qty / Month", "Client", "Total" };
    public static final String[] SALES_LICENSE_TABLE_LABELS = { "#", "Date", "Album", "Musician", "Duration (month)", "Paid", "Client" };

    public static Map<Integer, String> MONTHS = months();
    private static Map<Integer, String> months() {
        Map<Integer,String> map = new HashMap<Integer,String>();
        map.put(1, "JAN"); map.put(2, "FEB");
        map.put(3, "MAR"); map.put(4, "APR");
        map.put(5, "MAY"); map.put(6, "JUN");
        map.put(7, "JUL"); map.put(8, "AUG");
        map.put(9, "SEP"); map.put(10, "OCT");
        map.put(11, "NOV"); map.put(12, "DEC");
        return map;
    };
}
