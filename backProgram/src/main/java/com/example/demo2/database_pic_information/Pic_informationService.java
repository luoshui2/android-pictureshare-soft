package com.example.demo2.database_pic_information;

import com.example.demo2.database_picture.Picture;
import com.example.demo2.database_picture.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class Pic_informationService implements Pic_information_interface{

    @Autowired
    private Pic_informationRepository repository;
    private PictureRepository pictureRepository;

    @Override
    public Pic_information add(Pic_information picInformation) {
        return repository.save(picInformation);
    }

    //维护picture里面的名字(外键)

}
