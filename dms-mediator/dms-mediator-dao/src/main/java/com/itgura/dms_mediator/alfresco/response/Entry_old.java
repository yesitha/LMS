package com.itgura.dms_mediator.alfresco.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Entry_old {

    @JsonProperty("isFile")
    private Boolean isFile;

//	@JsonProperty("createdByUser")
//	private User createdByUser;

    @JsonProperty("modifiedAt")
    private String modifiedAt;

    @JsonProperty("nodeType")
    private String nodeType;

    @JsonProperty("content")
    private Content content;

    @JsonProperty("parentId")
    private String parentId;

    @JsonProperty("aspectNames")
    private String[] aspectNames;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("isFolder")
    private Boolean isFolder;

//	@JsonProperty("modifiedByUser")
//	private User modifiedByUser;

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private String id;

//	@JsonProperty("properties")
//	private Map<String, Object> properties;

}

