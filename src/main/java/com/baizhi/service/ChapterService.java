package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {

    Map<String,Object> selectChaptersByAlbumId(Integer page, Integer rows,String albumId);

    String add(Chapter chapter,String albumId);

    void update(Chapter chapter);

    void delete(Chapter chapter,String albumId);

}
