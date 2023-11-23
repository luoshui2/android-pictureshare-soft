package com.example.demo2.database_picture;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


//实现用户进行数据库操作接口
@Service
public class PictureService implements Picture_interface{
    @Autowired
    private PictureRepository pictureRepository;


    public Picture add(Picture pic){
        return pictureRepository.save(pic);
    }

    @Override
    public Page<Picture> findAll_page(Pageable pageable) {
        return pictureRepository.findAll(pageable);
    }

    @Override
    public Page<Object[]> findPicAndInfo(Pageable pageable) {
        return pictureRepository.findPictureWithPic_information(pageable);
    }

    //更新likenumber
    @Override
    public boolean updateLikeNumber(Integer id,int num) {
        Optional<Picture> picture = pictureRepository.findById(id);
        if(picture.isPresent()){
            Picture p = picture.get();
            p.setLike_number(num);
            pictureRepository.save(p);
            return true;
        }
        return false;
    }

    //更新collectnumber
    @Override
    public boolean updateCollectNumber(Integer id, int number) {
        Optional<Picture> picture = pictureRepository.findById(id);
        if(picture.isPresent()){
            Picture p = picture.get();
            p.setCollect_number(number);
            pictureRepository.save(p);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateDownNumber(Integer id, int number) {
        Optional<Picture> picture = pictureRepository.findById(id);
        if(picture.isPresent()){
            Picture p = picture.get();
            p.setDownload_number(number);
            pictureRepository.save(p);
            return true;
        }
        return false;
    }

    @Override
    public String findurl(int id) {
        return pictureRepository.findbyid_url(id);
    }
}
