package com.baizhi.service;

import com.baizhi.annotation.RedisCache;
import com.baizhi.api.BaseApiService;
import com.baizhi.dao.AlbumDAO;
import com.baizhi.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 类描述信息 (专辑业务操作)
 *
 * @author bxy
 * @Date 2019/10/29$ 10:17$
 * @version: V_1.0.0
 */

@Service
@Transactional
public class AlbumServiceImpl extends BaseApiService implements AlbumService {
    @Autowired
    private AlbumDAO albumDAO;

    @RedisCache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String, Object> selectAllAlbums(Integer page, Integer rows) {
        List<Album> albums = null;
        int count = 0;
        Integer total = null;
        System.out.println("进入到Service中");
        try {
            Album album = new Album();
            RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
            //分页查询到的数据
            albums = albumDAO.selectByRowBounds(album, rowBounds);
            //总条数
            count = albumDAO.selectCount(album);
            //总页数
            total = count%rows==0?count/rows:count/rows+1;
            return setResultSuccessDataByPage(albums,page,total,count);
        } catch (Exception e) {
            e.printStackTrace();
            return setResultError("查询所有专辑失败");
        }
    }

    @Override
    public String add(Album album) {
        return null;
    }

    @Override
    public void update(Album album) {

    }

    @Override
    public void delete(Album album) {

    }
}
