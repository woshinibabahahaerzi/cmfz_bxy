package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

public interface AlbumService {

    Map<String,Object> selectAllAlbums(Integer page,Integer rows);

    String add(Album album);

    void update(Album album);

    void delete(Album album);

}
