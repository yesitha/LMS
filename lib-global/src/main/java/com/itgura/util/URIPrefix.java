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
    public static final String GET_ALL = "/get-all";
}
