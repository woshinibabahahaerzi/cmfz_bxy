package com.baizhi.service;

import com.baizhi.annotation.RedisCache;
import com.baizhi.api.BaseApiService;
import com.baizhi.dao.BannerDAO;
import com.baizhi.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 类描述信息 ()
 *
 * @author bxy
 * @Date 2019/10/28$ 9:52$
 * @version: V_1.0.0
 */

@Service
@Transactional
public class BannerServiceImpl extends BaseApiService implements BannerService {

    @Autowired
    private BannerDAO bannerDAO;

    @RedisCache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String, Object> selectAllBanners(Integer page, Integer rows) {
        List<Banner> banners = null;
        int count = 0;
        Integer total = null;
        try {
            Banner banner = new Banner();
            RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
            //分页查询到的数据
            banners = bannerDAO.selectByRowBounds(banner, rowBounds);
            //总条数
            count = bannerDAO.selectCount(banner);
            //总页数
            total = count%rows==0?count/rows:count/rows+1;
            return setResultSuccessDataByPage(banners,page,total,count);
        } catch (Exception e) {
            e.printStackTrace();
            return setResultError("查询所有轮播图失败");
        }
    }

    @Override
    public String add(Banner banner) {
        String id = UUID.randomUUID().toString().replace("-", "");
        banner.setId(id);
        banner.setCreateDate(new Date());
        int i = bannerDAO.insertSelective(banner);
        if (i == 0){
            throw new RuntimeException("添加轮播图失败");
        }
        return id;
    }

    @Override
    public void update(Banner banner) {
        if ("".equals(banner.getCover())){
            banner.setCover(null);
        }
        int i = bannerDAO.updateByPrimaryKeySelective(banner);
        if (i == 0){
            throw new RuntimeException("修改轮播图失败");
        }
    }

    @Override
    public void delete(Banner banner) {
        int i = bannerDAO.deleteByPrimaryKey(banner.getId());
        if (i == 0){
            throw new RuntimeException("删除轮播图失败");
        }
    }
}
