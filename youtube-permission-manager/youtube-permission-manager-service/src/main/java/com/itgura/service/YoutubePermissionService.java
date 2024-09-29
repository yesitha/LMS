package com.itgura.service;


import java.util.List;

public interface YoutubePermissionService {

  void grantPermission(String url, List<String> emails);
  void revokePermission(String url, List<String> emails);


}
