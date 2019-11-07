package com.baizhi.service;

import com.baizhi.api.BaseApiService;
import com.baizhi.dao.AlbumDAO;
import com.baizhi.dao.ChapterDAO;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 类描述信息 ()
 *
 * @author bxy
 * @Date 2019/10/29$ 10:21$
 * @version: V_1.0.0
 */
@Service
@Transactional
public class ChapterServiceImpl extends BaseApiService implements ChapterService {

    @Autowired
    private ChapterDAO chapterDAO;

    @Autowired
    private AlbumDAO albumDAO;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String, Object> selectChaptersByAlbumId(Integer page, Integer rows, String albumId) {
        List<Chapter> chapters = null;
        int count = 0;
        Integer total = null;
        try {
            Chapter chapter = new Chapter();
            chapter.setAlbumId(albumId);
            RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
            //分页查询到的数据
            chapters = chapterDAO.selectByRowBounds(chapter, rowBounds);
            //总条数
            count = chapterDAO.selectCount(chapter);
            //总页数
            total = count%rows==0?count/rows:count/rows+1;
            return setResultSuccessDataByPage(chapters,page,total,count);
        } catch (Exception e) {
            e.printStackTrace();
            return setResultError("查询所有章节失败");
        }
    }

    @Override
    public String add(Chapter chapter,String albumId) {
        String id = UUID.randomUUID().toString().replace("-", "");
        chapter.setId(id);
        chapter.setAlbumId(albumId);
        chapter.setCreateDate(new Date());
        chapter.setLastUpdateDate(new Date());
        chapter.setStatus(1);
        int i = chapterDAO.insertSelective(chapter);
        if (i == 0 ){
            throw new RuntimeException("添加章节失败");
        }
        Album album = new Album();
        album.setId(albumId);
        Album selectOne = albumDAO.selectOne(album);
        album.setChapterCount(selectOne.getChapterCount()+1);
        int i1 = albumDAO.updateByPrimaryKeySelective(album);
        if (i1 == 0 ){
            throw new RuntimeException("修改专辑集数失败");
        }
        return id;
    }

    @Override
    public void update(Chapter chapter) {
        int i = chapterDAO.updateByPrimaryKeySelective(chapter);
        if (i == 0 ){
            throw new RuntimeException("修改章节失败");
        }
    }

    @Override
    public void delete(Chapter chapter,String albumId) {
        System.out.println("chapter:           "+chapter);
        System.out.println("albumId:           "+albumId);
        int i = chapterDAO.deleteByPrimaryKey(chapter.getId());
        if (i == 0 ){
            throw new RuntimeException("删除章节失败");
        }
        Album album = new Album();
        album.setId(albumId);
        Album selectOne = albumDAO.selectOne(album);
        album.setChapterCount(selectOne.getChapterCount()-1);
        int i1 = albumDAO.updateByPrimaryKeySelective(album);
        if (i1 == 0 ){
            throw new RuntimeException("修改专辑集数失败");
        }
    }
}
