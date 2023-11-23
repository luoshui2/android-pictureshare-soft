package com.example.demo2.controller;

import com.example.demo2.database_pic_information.Pic_information;
import com.example.demo2.database_pic_information.Pic_informationService;
import com.example.demo2.database_picture.Picture;
import com.example.demo2.database_picture.PictureService;
import com.example.demo2.database_user.UserService;
import com.example.demo2.database_user_information.User_information;
import com.example.demo2.database_user_information.User_informationService;
import com.example.demo2.file_upload.FileNameUtils;
import com.example.demo2.file_upload.FileUtils;
import com.example.demo2.database_user.User;
import com.example.demo2.path.Path;
import com.example.demo2.path.Url;
import com.example.demo2.pic_user.Pic_user;
import com.example.demo2.pic_user.Pic_userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("ceshi")
public  class Controller {
    @Autowired
    private UserService userService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private Pic_informationService picInformationService;
    @Autowired
    private User_informationService userInformationService;
    @Autowired
    private Pic_userService picUserService;

    private Path path = new Path();
    private Url url = new Url();

    //验证账号密码接口
    @RequestMapping("/get_user")
    public Map get_user(String name,String password){
        Map map = new HashMap();
        if(name == null || password == null){
            map.put("code","410");
            map.put("msg","error");
            return map;
        }
        User user = userService.findById(name);
        if(user == null){
            map.put("code","420");
            map.put("msg","error");
            return map;
        }else{
            map.put("msg","success");
            if(name.equals(user.getUsename())&&password.equals(user.getPassword())){
                map.put("code","200");
            }else{
                map.put("code","210");
            }
            return map;
        }
    }

    //获取账号密码接口
    @RequestMapping("/get_users")
    public Map get_users(String name){
        Map map = new HashMap();
        if(name == null){
            map.put("code","410");
            map.put("msg","error");
            return map;
        }
        User user = userService.findById(name);
        if(user == null){
            map.put("code","420");
            map.put("msg","error");
            return map;
        }else{
            map.put("code","200");
            map.put("msg","success");
            map.put("username",user.getUsename());
            map.put("password",user.getPassword());
            return map;
        }
    }
    //注册账号并且初始化获赞数
    @RequestMapping("/upload_user")
    public Map upload_user(String name,String password){
        Map map = new HashMap();
        if(name == null || password == null){
            map.put("code","410");
            map.put("msg","error");
            return map;
        }
        User user = userService.findById(name);
        //已经存在用户
        if(user != null){
            map.put("code","430");
            map.put("msg","error");
            map.put("usename",user.getUsename());
            return map;
        }else{
            //不存在用户，添加用户到数据库
            User new_user = new User();
            User_information userInformation = new User_information();
            new_user.setUsename(name);
            new_user.setPassword(password);

            userInformation.setUsename(name);
            userInformation.setCollect_total(0);
            userInformation.setLike_total(0);

            userService.add(new_user);
            userInformationService.add(userInformation);
            map.put("code","200");
            map.put("msg","success");
            return map;
        }
    }

    //测试服务器
    @RequestMapping("/hello")
    public String index(){
        System.out.println("ceshi");
        return "hello";
    }

    //上传图片的api
    @RequestMapping("/upload")
    public String upload(@RequestParam("title") String title,@RequestParam("usename") String usename,@RequestParam("fileName") MultipartFile file){


        //本机端
//        path.setLocalPath("F:/cs/imag");
//        path.setThPath("F:/cs/im");

        //服务器端
        path.setLocalPath("/sourcepic");
        path.setThPath("/thpic");

        // 上传成功或者失败的提示
        String msg = "";

        String new_filename = FileNameUtils.getFileName(file.getOriginalFilename());//带后缀的文件名字
        if (FileUtils.upload(file, path.getLocalPath(),path.getThPath() ,new_filename)){
            // 上传成功，给出页面提示
            msg = "上传成功！";

            //图片信息写入数据库

            Pic_information picInformation = new Pic_information();


           //图片名字
            picInformation.setName(new_filename.substring(0,new_filename.indexOf(".")));
            //图片类型
            picInformation.setType(new_filename.substring(new_filename.lastIndexOf(".")));
            //图片上传时间
            Date time = new Date();
            SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            picInformation.setUpload_date(t.format(time));
            //原图片存储路径
            picInformation.setPic_path(path.getLocalPath() + "/" + new_filename);
            //图片访问路径
            picInformation.setPic_url(url.getPicsource_url() + new_filename);
            //添加到数据库
            //外键先添加
            picInformationService.add(picInformation);

            Picture pic = new Picture();
            //id自增

            //文件名字
            pic.setName(new_filename.substring(0,new_filename.indexOf(".")));
            //标题
            pic.setTitle(title);
            //图片的用户名
            pic.setUsename(usename);
            //图片点赞数
            pic.setLike_number(0);
            //图片收藏数
            pic.setCollect_number(0);
            //图片的下载数
            pic.setDownload_number(0);

            pictureService.add(pic);

        }else {
            msg = "上传失败！";
        }
        return "upload success";
    }

    //浏览器下载图片
    @RequestMapping("/downloads")
    public String downloadFile(HttpServletResponse response,Integer id) throws IOException {
//        if (id != null) {
//            //设置文件路径
//            Picture picture = pictureService.findById(id);
//            if(picture == null){
//                return "download error";
//            }
//            File file = new File(picture.getSourcepath());
//            if (file.exists()) {
//                // 重置response
//                response.reset();
//                // ContentType，即告诉客户端所发送的数据属于什么类型
//                response.setContentType("application/octet-stream; charset=UTF-8");
//                // 获得文件的长度
//                response.setHeader("Content-Length", String.valueOf(file.length()));
//                // 设置编码格式
//                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(picture.getFilename(), "UTF-8"));
//                // 发送给客户端的数据
//                OutputStream outputStream = response.getOutputStream();
//                byte[] buffer = new byte[1024];
//                FileInputStream fis = null;
//                BufferedInputStream bis = null;
//                try {
//                    fis = new FileInputStream(file);
//                    bis = new BufferedInputStream(fis);
//                    OutputStream os = response.getOutputStream();
//                    int i = bis.read(buffer);
//                    while (i != -1) {
//                        os.write(buffer, 0, i);
//                        i = bis.read(buffer);
//                    }
//                    return "download success";
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (bis != null) {
//                        try {
//                            bis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (fis != null) {
//                        try {
//                            fis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
        return "download error";
    }
    //分页查询获取图片的信息
    @RequestMapping("/get_pic")
    public Map get_pic(@RequestParam("page") int page,@RequestParam("pageSize") int pagesize,
                       @RequestParam("username") String name){
        Map map = new HashMap<>();
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = (Pageable) PageRequest.of(page,pagesize,sort);
        Page<Object[]> pictures = pictureService.findPicAndInfo(pageable);
        List<Pic_user> pic_id = picUserService.findby_user(name);
        if(pictures.isEmpty()){
            map.put("msg","null");
            map.put("code",201);
        }else{
            List<Map<String, Object>> resultList = new ArrayList<>();
            for (Object[] row : pictures) {
                // 创建一个新的Map来存储当前行的数据
                Map<String, Object> rowMap = new HashMap<>();

                // 在这里，你可以根据你的查询选择的字段的顺序，为每个字段添加一个键值对到Map中
                // 假设你的查询中选择了id、name、age这三个字段
                rowMap.put("id", row[0]);
                rowMap.put("title", row[1]);
                rowMap.put("usename", row[2]);
                rowMap.put("like_number", row[3]);
                rowMap.put("collect_number", row[4]);
                rowMap.put("download_number", row[5]);
                rowMap.put("pic_url", row[6]);
                rowMap.put("is_like",false);
                rowMap.put("is_collect",false);
                if(pic_id != null){
                    for(Pic_user p : pic_id){
                        if(p.getPicId().equals(row[0])){
                            if(p.isLikeCheck()){
                                rowMap.put("is_like",true);
                            }else{
                                rowMap.put("is_like",false);
                            }
                            if(p.isCollectCheck()){
                                rowMap.put("is_collect",true);
                            }else{
                                rowMap.put("is_collect",false);
                            }
                            break;
                        }
                    }
                }
                // 将当前行的Map对象添加到结果列表中
                resultList.add(rowMap);
            }
            map.put("msg","success");
            map.put("code",200);
            map.put("result",resultList);
        }
        return map;
    }

    //点赞更新picture、pic_user、user_info表
    @RequestMapping("/pic_like")
    public Map update_pic_user_like(@RequestParam("pic_id") int pic_id, @RequestParam("username") String name,
                                    @RequestParam("likeNum") int likeNumber, @RequestParam("flag") boolean flag){
        Map map = new HashMap();
        boolean re_flag = true;
        //更新picture
        boolean update_pic = pictureService.updateLikeNumber(Integer.valueOf(pic_id),likeNumber);
        if(update_pic){
            map.put("update_picture","success");
        }else{
            re_flag = false;
            map.put("update_picture","failture");
        }

        //更新pic_user
        boolean update_pic_user = picUserService.updateLikeCheck(Integer.valueOf(pic_id),name,flag);
        if(update_pic_user){
            map.put("update_pic_user","success");
        }else{
            re_flag = false;
            map.put("update_pic_user","failture");
        }

        //更新user_info
        boolean update_user_info = userInformationService.update_userinfo_like(name,flag);
        if(update_user_info){
            map.put("update_user_info","success");
        }else{
            re_flag = false;
            map.put("update_user_info","failture");
        }

        if(re_flag){
            map.put("code",200);
            map.put("msg","success");
        }else{
            map.put("code",260);
            map.put("msg","failture");
        }
        return map;
    }

    //收藏更新picture、pic_user、user_info表
    @RequestMapping("/pic_collect")
    public Map update_pic_user_collect(@RequestParam("pic_id") int pic_id, @RequestParam("username") String name,
                                       @RequestParam("collectNumber") int collectNumber, @RequestParam("flag") boolean flag){
        Map map = new HashMap();
        boolean re_flag = true;
        //更新picture
        boolean update_pic = pictureService.updateCollectNumber(Integer.valueOf(pic_id), collectNumber);
        if(update_pic){
            map.put("update_picture","success");
        }else{
            re_flag = false;
            map.put("update_picture","failture");
        }

        //更新pic_user
        boolean update_pic_user = picUserService.updateCollectCheck(Integer.valueOf(pic_id),name,flag);
        if(update_pic_user){
            map.put("update_pic_user","success");
        }else{
            re_flag = false;
            map.put("update_pic_user","failture");
        }

        //更新user_info
        boolean update_user_info = userInformationService.update_userinfo_collect(name,flag);
        if(update_user_info){
            map.put("update_user_info","success");
        }else{
            re_flag = false;
            map.put("update_user_info","failture");
        }

        if(re_flag){
            map.put("code",200);
            map.put("msg","success");
        }else{
            map.put("code",270);
            map.put("msg","failture");
        }
        return map;
    }
    //更新下载
    @RequestMapping("/updatedownload")
    public Map update_downPic(@RequestParam("pic_id") int pic_id,@RequestParam("downNum") int downNumber){
        Map map = new HashMap<>();
        boolean re = pictureService.updateDownNumber(pic_id,downNumber);
        if(re){
            map.put("code",200);
            map.put("msg","success");
        }else{
            map.put("code",280);
            map.put("msg","failture");
        }
        return map;
    }

    //获取收藏
    @RequestMapping("/get_info")
    public Map get_info(@RequestParam("number") int num,@RequestParam("username") String name){
        Map map = new HashMap<>();
        User_information u = userInformationService.getinfo(name);
        if(u == null){
            map.put("code",290);
            map.put("msg","error");
        }else{
            map.put("code",200);
            map.put("msg","success");
            map.put("like_number",u.getLike_total());
            map.put("collect_number",u.getCollect_total());
        }
        List<Pic_user> picUsers = picUserService.findPicId(name,true);
        int number = picUsers.size();
        if(number <= num){
            map.put("code",290);
            map.put("msg","error");
            return map;
        }
        if(picUsers.isEmpty()){
            map.put("picnumber","0");
        }else{
            Pic_user p = picUsers.get(num);
            String url = pictureService.findurl(p.getPicId());
            map.put("url" ,url);
        }
        return map;
    }

    //返回文件
    @RequestMapping("/web")
    public ResponseEntity<Resource> web(String path){
        String contentDisposition = ContentDisposition
                .builder("attachment")
                .filename("F:\\系统默认\\桌面\\web\\" + path)
                .build().toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(new FileSystemResource("F:\\系统默认\\桌面\\web\\" + path));
    }

    //返回svg图形
    @RequestMapping("/img")
    public ResponseEntity<Resource> img(String path){
        String contentDisposition = ContentDisposition
                .builder("attachment")
                .filename("F:\\系统默认\\桌面\\web\\" + path)
                .build().toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.parseMediaType("image/svg+xml"))
                .body(new FileSystemResource("F:\\系统默认\\桌面\\web\\" + path));
    }
}
