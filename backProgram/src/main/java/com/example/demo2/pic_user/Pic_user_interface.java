package com.example.demo2.pic_user;
import java.util.List;
public interface Pic_user_interface {
    Pic_user add(Pic_user p);
    List<Pic_user> findby_user(String name);

    boolean updateLikeCheck(Integer pic_id,String name,boolean like);//更新喜欢

    boolean updateCollectCheck(Integer pic_id,String name,boolean collect);//更新收藏

    List<Pic_user> findPicId(String name,boolean check);
}
