# 文件上传

* #### 前提

  > "Content-Type": "multipart/form-data"
  >
  > method -> post
  >

 * #### MultipartFile

   > yaml 配置: 
   >
   > ```yaml
   > spring:  
   >   servlet:
   >     multipart:
   >       max-file-size: 5MB
   >       max-request-size: 20MB
   > ```

 * #### Controller

   > ```java
   > String path = session.getServletContext().getRealPath("/upload");
   > File upload = new File(path);
   > if (upload.exists())
   > 	upload.mkdirs();
   > String imageName = image.getOriginalFilename();
   > String uuid = UUID.randomUUID().toString().replaceAll("-", "");
   > try {
   > 	image.transferTo(new File(path + uuid + imageName));
   > 	} catch (IOException e) {
   > 		e.printStackTrace();
   > }
   > ```
   >
   > 