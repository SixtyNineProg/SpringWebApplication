package by.clevertec.WebApplication.constants;

public class Constants {
    public static final String USER = "/user";
    public static final String ID_PATH_VARIABLE = "/{id}";
    public static final String PATH_GET_ALL = "/getAll";
    public static final String PATH_UPDATE = "/updateUser";

    public static final String ERROR_PARSING_OF_OBJECT = "can't represent object of class {} in json form for logging: {}";
    public static final String USER_SAVED = "User saved successful : {}";
    public static final String USER_RECEIVED = "User with id={} received successful : {}";
    public static final String USER_RECEIVED_CACHE = "User with id={} received from the cache successful : {}";
    public static final String USER_DELETED = "User with id={} deleted successful";
    public static final String USERS_RECEIVED = "All users with received successful";
    public static final String USER_UPDATED = "User with id={} updated successful : {}";
    public static final String USER_PAGEABLE = "User pageable received successful : {}";

    public static final String PAGESIZE_KEY = "pagesize";
    public static final String PAGESIZE_VALUE = "3";
    public static final String PAGENUMBER_KEY = "pagenumber";
    public static final String PAGENUMBER_VALUE = "0";

    public static final int sizeCache = 2;
}