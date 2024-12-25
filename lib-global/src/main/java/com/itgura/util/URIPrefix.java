package com.itgura.util;

public class URIPrefix {
    public static final String V1 = "/v1";
    public static final String V2 = "/v2";
    public static final String API = "api";
	public static final String BY_ID = /* "/"+URIPathVariable.ID+ */"/{"+URIPathVariable.ID+"}";
    public static final String BY_CODE = "/"+URIPathVariable.ID+"/{"+URIPathVariable.ID+"}";
    public static final String BY_REF_NO = "/"+URIPathVariable.REF_NO+"/{"+URIPathVariable.REF_NO+"}";

    public static final String INFO = "/info";


    public static final String DOCUMENTS = "/documents";
    public static final String CREATE = "/create";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";
    public static final String GET = "/get";
    public static final String ID = "/{id}";
    public static final String CLASS_ID = "/{classId}";
    public static final String YEAR = "/{year}";
    public static final String MONTH = "/{month}";
    public static final String LESSON_ID = "/{lessonId}";
    public static final String SESSION_ID = "/{sessionId}";
    public static final String GET_ALL = "/get-all";
    public static final String UPDATE_ACCESS_TIME_DURATION = "/update-access-time-duration";
    public static final String GET_ACCESS_TIME_DURATION = "/get-access-time-duration";
    public static final String GET_PRICE = "/get-price";
    public static final String UPDATE_TAGS = "/update-tags";
}
